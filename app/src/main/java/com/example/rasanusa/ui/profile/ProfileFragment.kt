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
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.rasanusa.R
import com.example.rasanusa.databinding.FragmentProfileBinding
import com.example.rasanusa.ui.login.LoginActivity
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

        binding.apply {
            layoutProfile.setOnClickListener{ navigateToProfileSettings() }
            layoutBerlangganan.setOnClickListener{ navigateToMembership() }
            layoutHistory.setOnClickListener{ navigateToHistory() }
            layoutLogout.setOnClickListener { setupLogout() }
        }
    }

    private fun fetchUsername() {
        val sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
        val username = sharedPreferences.getString("USERNAME", "User")

        binding.txtUsernameProfile.text = getString(R.string.username_home, username)
    }

    private fun navigateToProfileSettings(){
        Toast.makeText(requireContext(), "Fitur ini belum tersedia", Toast.LENGTH_SHORT).show()
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
            .setMessage("Apakah anda yaking ingin Logout?")
            .setPositiveButton("Logout"){_, _, ->
                Firebase.auth.signOut()
                val intent = Intent(requireContext(), LoginActivity::class.java)
                startActivity(intent)

                Toast.makeText(requireContext(), "Anda berhasil keluar.", Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            }
            .setNegativeButton("Batal"){dialog, _, ->
                dialog.dismiss()
            }
            .show()
    }

}