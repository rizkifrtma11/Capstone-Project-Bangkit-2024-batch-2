package com.example.rasanusa.ui.profile

import android.content.Context
import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.rasanusa.R
import com.example.rasanusa.databinding.FragmentProfileBinding
import com.example.rasanusa.helper.AuthenticationHelper
import com.example.rasanusa.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fetchUsername()

        onBackPressed()

        binding.apply {
            layoutEmailPass.setOnClickListener{ navigateToProfileSettings() }
            layoutBerlangganan.setOnClickListener{ navigateToMembership() }
            layoutHistory.setOnClickListener{ navigateToHistory() }
            layoutLogout.setOnClickListener { setupLogout() }
        }
    }

    private fun fetchUsername() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        if (currentUser != null) {
            val email = currentUser.email
            val uid = currentUser.uid

            if (email != null) {
                val username = AuthenticationHelper.getUsername(requireContext(), email)
                binding.txtUsernameProfile.text = getString(R.string.username_profile, username)
            } else {
                binding.txtUsernameProfile.text = getString(R.string.username_profile, "User")
            }
        }
    }

    private fun navigateToProfileSettings(){
        Toast.makeText(requireContext(), getString(R.string.feature_not_available), Toast.LENGTH_SHORT).show()
    }

    private fun navigateToMembership(){
        findNavController().navigate(R.id.action_profile_to_subscription)
    }

    private fun navigateToHistory(){
        findNavController().navigate(R.id.action_profile_to_history)
    }

    private fun setupLogout(){
        AlertDialog.Builder(requireContext())
            .setTitle("Logout")
            .setMessage(getString(R.string.confirm_logout))
            .setPositiveButton("Logout"){_, _, ->
                val sharedPreferences =
                    requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                with(sharedPreferences.edit()) {
                    remove("CURRENT_USERNAME")
                    apply()
                }

                Firebase.auth.signOut()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)

                Toast.makeText(requireContext(), "Anda berhasil keluar.", Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            }
            .setNegativeButton(getString(R.string.tidak)){dialog, _, ->
                dialog.dismiss()
            }
            .show()
    }

    private fun onBackPressed(){
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val navController = findNavController()
                if (navController.currentDestination?.id != R.id.navigation_home) {
                    navController.popBackStack(R.id.navigation_home, false)
                } else {
                    requireActivity().finish()
                }
            }
        })
    }

}