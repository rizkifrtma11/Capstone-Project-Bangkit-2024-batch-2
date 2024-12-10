package com.example.rasanusa.ui.chatbot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.rasanusa.R
import com.example.rasanusa.databinding.FragmentChatbotBinding


class ChatbotFragment : Fragment() {

    private var _binding : FragmentChatbotBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChatbotBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        onBackPressed()

        binding.apply {
            btnExit.setOnClickListener {
                findNavController().popBackStack(R.id.navigation_home, false)
            }
            btnAsk.setOnClickListener {
                botBubbleChat.visibility = View.VISIBLE
                userBubbleChat.visibility = View.VISIBLE
                titleChatbotWelcome.visibility = View.GONE

                Toast.makeText(requireContext(), getString(R.string.feature_not_available), Toast.LENGTH_SHORT).show()
            }
        }
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