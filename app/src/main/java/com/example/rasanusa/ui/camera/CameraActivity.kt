package com.example.rasanusa.ui.camera

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import android.view.OrientationEventListener
import android.view.Surface
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.AspectRatio
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.rasanusa.R
import com.example.rasanusa.createCustomTempFile
import com.example.rasanusa.databinding.ActivityCameraBinding

class CameraActivity : AppCompatActivity() {
//    private lateinit var binding: ActivityCameraBinding
//    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
//    private var imageCapture: ImageCapture? = null
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityCameraBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setupView()
//        startCamera()
//        binding.btnCapture.setOnClickListener { takePhoto() }
//
//    }
//
//    private fun hideSystemUI() {
//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
//        supportActionBar?.hide()
//    }
//
//    @Suppress("DEPRECATION")
//    private fun setupView(){
//        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
//    }
//
//    private fun startCamera() {
//        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)
//
//        cameraProviderFuture.addListener({
//            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
//            val preview = Preview.Builder()
////                .setTargetAspectRatio(AspectRatio.RATIO_1_1)
//                .build()
//                .also {
//                    it.setSurfaceProvider(binding.camera.surfaceProvider)
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
//                Toast.makeText(
//                    this@CameraActivity,
//                    "Gagal memunculkan kamera.",
//                    Toast.LENGTH_SHORT
//                ).show()
//                Log.e(TAG, "startCamera: ${exc.message}")
//            }
//        }, ContextCompat.getMainExecutor(this))
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
//            ContextCompat.getMainExecutor(this),
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
//                        this@CameraActivity,
//                        "Gagal mengambil gambar.",
//                        Toast.LENGTH_SHORT
//                    ).show()
//                    Log.e(TAG, "onError: ${exc.message}")
//                }
//            }
//        )
//    }
//
//    private val orientationEventListener by lazy {
//        object : OrientationEventListener(this) {
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
//
//    override fun onResume() {
//        super.onResume()
//        hideSystemUI()
//        startCamera()
//    }
//
//    override fun onStart() {
//        super.onStart()
//        orientationEventListener.enable()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        orientationEventListener.disable()
//    }
//
//    companion object {
//        private const val TAG = "CameraActivity"
//        const val EXTRA_CAMERAX_IMAGE = "CameraX Image"
//        const val CAMERAX_RESULT = 200
//    }
}