package com.example.rasanusa.ui.profile

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
import com.example.rasanusa.databinding.FragmentScanBinding
import com.example.rasanusa.ui.history.HistoryActivity
import com.example.rasanusa.ui.history.HistoryFragment
import com.example.rasanusa.ui.login.LoginActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            layoutProfile.setOnClickListener{}
            layoutBerlangganan.setOnClickListener{}
            layoutHistory.setOnClickListener{ setupHistory() }
            layoutLogout.setOnClickListener { setupLogout() }
        }

    }

    private fun setupProfileSetting(){
//        val intent = Intent(requireContext(), ChangeProfileActivity::class.java)
//        startActivity(intent)
        requireActivity().finish()
    }

    private fun setupMembership(){
//        val intent = Intent(requireContext(), ChangeProfileActivity::class.java)
//        startActivity(intent)
        requireActivity().finish()
    }

    private fun setupHistory(){

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