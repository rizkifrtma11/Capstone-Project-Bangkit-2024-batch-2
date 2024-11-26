package com.example.rasanusa.ui.scan

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.util.Rational
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Surface
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.core.UseCaseGroup
import androidx.camera.core.ViewPort
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import com.example.rasanusa.createCustomTempFile
import com.example.rasanusa.databinding.FragmentScanBinding
import com.example.rasanusa.ui.imageresult.ImageResultActivity

class ScanFragment : Fragment() {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    private var _currentImageUri: Uri? = null
    private var currentImagerUri: Uri?
        get() = _currentImageUri
        set(value) {_currentImageUri = value}

//    private lateinit var binding: FragmentScanBinding
//    private var currentImageUri: Uri? = null
    private lateinit var cameraProvider: ProcessCameraProvider
    private lateinit var preview: Preview
    private lateinit var imageCapture: ImageCapture
    private lateinit var cameraSelector: CameraSelector
    private lateinit var previewView: PreviewView
    private lateinit var viewPort: ViewPort

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Toast.makeText(requireContext(), "Permission request granted", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(requireContext(), "Permission request denied", Toast.LENGTH_LONG)
                    .show()
            }
        }

    private fun allPermissionsGranted() =
        ContextCompat.checkSelfPermission(
            requireContext(),
            REQUIRED_PERMISSION
        ) == PackageManager.PERMISSION_GRANTED

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         if (!allPermissionsGranted()) {
            requestPermissionLauncher.launch(REQUIRED_PERMISSION)
        }

//        binding.btnToCam.setOnClickListener{ toCamera() }

        previewView = binding.preview

        startCamera()

        binding.apply {
            btnCapture.setOnClickListener{
                captureImage()
            }
            btnPhotoPicker.setOnClickListener{
                startGallery()
            }
        }
    }


    private fun startCamera() {
        // Mendapatkan CameraProvider
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            cameraProvider = cameraProviderFuture.get()

            // Menyiapkan Preview untuk CameraX
            preview = Preview.Builder().build()
            preview.surfaceProvider = previewView.surfaceProvider

            // Menyiapkan ImageCapture untuk menangkap foto
            imageCapture = ImageCapture.Builder().build()

            viewPort = ViewPort.Builder(
                Rational(1, 1),
                Surface.ROTATION_0
            ).build()

            val useCaseGroup = UseCaseGroup.Builder()
                .setViewPort(viewPort)
                .addUseCase(preview)
                .addUseCase(imageCapture)
                .build()

            // Menyiapkan CameraSelector untuk memilih kamera belakang
            cameraSelector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            // Menyambungkan kamera dengan lifecycle fragment
            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    viewLifecycleOwner, cameraSelector, useCaseGroup
                )
            } catch (e: Exception) {
                Log.e("CameraX", "Use case binding failed", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun captureImage() {
        val outputOptions = ImageCapture.OutputFileOptions.Builder(createCustomTempFile(requireContext())).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = outputFileResults.savedUri
                    Log.d("CameraFragment", "Image saved to: $savedUri")

                    val intent = Intent(requireContext(), ImageResultActivity::class.java)
                    intent.putExtra("imageUri", savedUri.toString())
                    startActivity(intent)
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("CameraFragment", "Image capture failed", exception)
                }
            })
    }

    private fun startGallery() {
        launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImagerUri = uri

            val intent = Intent(activity, ImageResultActivity::class.java)
            intent.putExtra("imageUri", uri.toString())
            startActivity(intent)
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }



//    private val launcherGallery = registerForActivityResult(
//        ActivityResultContracts.PickVisualMedia() // Hanya memilih gambar
//    ) { uri: Uri? ->
//        if (uri != null) {
//            // Gambar berhasil dipilih, simpan URI gambar
//            currentImageUri = uri
//            showImage(uri) // Menampilkan gambar yang dipilih
//        } else {
//            Log.d("Gallery", "No media selected")
//        }
//    }



    companion object {
        private const val REQUIRED_PERMISSION = Manifest.permission.CAMERA
//        private const val TAG = "ScanFragment"
//        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
//        const val CAMERAX_RESULT = 200
    }
}

//    private fun startCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
//
//        cameraProviderFuture.addListener({
//            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
//            val preview = Preview.Builder()
////                .setTargetAspectRatio(AspectRatio.RATIO_1_1)
//                .build()
//                .also {
//                    it.setSurfaceProvider(binding.preview.surfaceProvider)
//                }
//
//            imageCapture = ImageCapture.Builder().build()
//
//            try {
//                cameraProvider.unbindAll()
//                cameraProvider.bindToLifecycle(
//                    this,
//                    cameraSelector,
//                    preview,
//                    imageCapture
//                )
//
//            } catch (exc: Exception) {
//                Toast.makeText(requireContext(), "Gagal memunculkan kamera.", Toast.LENGTH_SHORT).show()
//                Log.e(TAG, "startCamera: ${exc.message}")
//            }
//        }, ContextCompat.getMainExecutor(requireContext()))
//    }
//
//    private fun takePhoto() {
//        val imageCapture = imageCapture ?: return
//
//        val photoFile = createCustomTempFile(application)
//
//        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
//
//        imageCapture.takePicture(
//            outputOptions,
//            ContextCompat.getMainExecutor(requireContext()),
//            object : ImageCapture.OnImageSavedCallback {
//                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
//                    val intent = Intent()
//                    intent.putExtra(EXTRA_CAMERAX_IMAGE, output.savedUri.toString())
//                    setResult(CAMERAX_RESULT, intent)
//                    finish()
//                }
//
//                override fun onError(exc: ImageCaptureException) {
//                    Toast.makeText(
//                        requireContext(),
//                        "Gagal mengambil gambar.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    Log.e(ScanFragment.TAG, "onError: ${exc.message}")
//                }
//            }
//        )
//    }
//
//    private val orientationEventListener by lazy {
//        object : OrientationEventListener(requireContext()) {
//            override fun onOrientationChanged(orientation: Int) {
//                if (orientation == ORIENTATION_UNKNOWN) {
//                    return
//                }
//
//                val rotation = when (orientation) {
//                    in 45 until 135 -> Surface.ROTATION_270
//                    in 135 until 225 -> Surface.ROTATION_180
//                    in 225 until 315 -> Surface.ROTATION_90
//                    else -> Surface.ROTATION_0
//                }
//
//                imageCapture?.targetRotation = rotation
//            }
//        }
//    }

//    //TODO: Make it smooth transition when exit from CameraActivity
//
//    private fun toCamera(){
//        val intent = Intent(requireActivity(), CameraActivity::class.java)
//        launcherIntentCameraX.launch(intent)
//    }
//
//    private val launcherIntentCameraX = registerForActivityResult(
//        ActivityResultContracts.StartActivityForResult()
//    ){
//        if (it.resultCode == CAMERAX_RESULT){
//            _currentImageUri = it.data?.getStringExtra(CameraActivity.EXTRA_CAMERAX_IMAGE)?.toUri()
//            showImage()
//        }
//    }
//
//    private fun showImage() {
//        currentImagerUri?.let {
//            Log.d("Image URI", "showImage: $it")
//            binding.preview.setImageURI(it)
//        }
//    }