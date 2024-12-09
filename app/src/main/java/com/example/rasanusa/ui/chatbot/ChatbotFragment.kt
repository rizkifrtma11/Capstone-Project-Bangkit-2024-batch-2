package com.example.rasanusa.ui.chatbot

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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

        binding.apply {
            btnExit.setOnClickListener {
                findNavController().navigateUp()
            }
            btnAsk.setOnClickListener {
                botBubbleChat.visibility = View.VISIBLE
                userBubbleChat.visibility = View.VISIBLE
                titleChatbotWelcome.visibility = View.GONE

                Toast.makeText(requireContext(), getString(R.string.feature_not_available), Toast.LENGTH_SHORT).show()
            }
        }
    }
}