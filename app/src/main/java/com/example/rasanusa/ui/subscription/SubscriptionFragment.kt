package com.example.rasanusa.ui.subscription

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.rasanusa.R
import com.example.rasanusa.databinding.FragmentSubscriptionBinding
import com.example.rasanusa.setFormattedPrice
import com.example.rasanusa.setStrikeThrough

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

        setupTextView()

        _binding?.apply {
            btnExit.setOnClickListener {
                findNavController().navigateUp()
            }
            btnBuy.setOnClickListener{
                Toast.makeText(requireContext(),
                    getString(R.string.feature_not_available), Toast.LENGTH_SHORT).show()
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


}