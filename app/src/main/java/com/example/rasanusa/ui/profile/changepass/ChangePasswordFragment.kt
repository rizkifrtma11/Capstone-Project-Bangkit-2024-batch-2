package com.example.rasanusa.ui.profile.changepass

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.rasanusa.R
import com.example.rasanusa.databinding.FragmentChangeEmailBinding
import com.example.rasanusa.databinding.FragmentChangePasswordBinding
import com.example.rasanusa.ui.mainactivity.MainActivity
import com.google.firebase.auth.FirebaseAuth

class ChangePasswordFragment : Fragment() {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    private val currentUser = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack(R.id.navigation_profile, false)
        }

        binding.apply {
            lastpasswordEditText.addTextChangedListener {
                setMyButtonEnable()
            }

            newpasswordEditText.addTextChangedListener {
                setMyButtonEnable()
            }

            confirmNewPasswordEditText.addTextChangedListener {
                setMyButtonEnable()
            }

            btnUpdatePass.addTextChangedListener {
                showDialog()
            }

            bntBack.setOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    private fun showDialog(){
        AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.feature_not_available))
            .setMessage(getString(R.string.txt_feature_on_development))
            .setPositiveButton("OK") { _, _ ->
                val intent = Intent(requireContext(), MainActivity::class.java)
                intent.putExtra("navigate_to", "ProfileFragment")
                startActivity(intent)
                requireActivity().finish()
            }
            .show()
    }

    private fun setMyButtonEnable() {
        val passwordValid = binding.btnUpdatePass.text
        binding.btnUpdatePass.isEnabled =
            (passwordValid != null) && passwordValid.toString().isNotEmpty()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}