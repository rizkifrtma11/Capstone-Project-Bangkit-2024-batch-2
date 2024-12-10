package com.example.rasanusa.ui.subscription

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.rasanusa.R
import com.example.rasanusa.databinding.FragmentSubscriptionBinding
import com.example.rasanusa.helper.ScanPreferences
import com.example.rasanusa.setFormattedPrice
import com.example.rasanusa.setStrikeThrough
import com.example.rasanusa.ui.mainactivity.MainActivity

class SubscriptionFragment : Fragment() {

    private var _binding: FragmentSubscriptionBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSubscriptionBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().popBackStack(R.id.navigation_profile, false)
        }

        setupTextView()

        _binding?.apply {
            btnExit.setOnClickListener {
                findNavController().navigateUp()
            }
            btnBuy.setOnClickListener{
                resetLimit()
            }
        }
    }

    private fun setupTextView() {
        setFormattedPrice(binding.txtPriceDiscountAnnual, "29999")
        setFormattedPrice(binding.txtPriceOri, "149999")
        setFormattedPrice(binding.txtPriceDiscountMonthly, "19999")
        setFormattedPrice(binding.txtPriceOriMonthly, "89999")
        setFormattedPrice(binding.txtPriceOriWeekly, "9999")

        binding.apply {
            setStrikeThrough(txtPriceOri)
            setStrikeThrough(txtPerYearOri)

            setStrikeThrough(txtPriceOriMonthly)
            setStrikeThrough(txtPerYearOriMonthly)

            setStrikeThrough(txtPriceOriWeekly)
            setStrikeThrough(txtPerYearOriWeekly)
        }
    }

    private fun resetLimit(){
        if (ScanPreferences.isScanLimitReached(requireContext())) {
            ScanPreferences.resetScanCount(requireContext())

            AlertDialog.Builder(requireContext())
                .setTitle(getString(R.string.success_subscribe))
                .setMessage(getString(R.string.message_success_subscribe))
                .setPositiveButton("OK") { _, _ ->
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.putExtra("navigate_to", "HomeFragment")
                    startActivity(intent)
                    requireActivity().finish()
                }
                .show()
//            Toast.makeText(requireContext(), getString(R.string.success_subscribe), Toast.LENGTH_SHORT).show()
        }
    }


}