package com.example.rasanusa.ui.result

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.rasanusa.data.response.DocumentData
import com.example.rasanusa.databinding.FragmentBottomSheetDialogResultBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

@Suppress("DEPRECATION")
class BottomSheetDialogResultFragment : BottomSheetDialogFragment() {

    private var _binding : FragmentBottomSheetDialogResultBinding? = null
    private val binding get() = _binding!!

    private val viewModel: BottomSheetDialogResultViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetDialogResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val documentData = arguments?.getParcelable<DocumentData>("DOCUMENT_DATA")
        viewModel.setDocumentData(documentData)
        viewModel.documentData.observe(viewLifecycleOwner) { data ->
            if (data != null) {
                updateUI(data)
            }
        }
    }

    private fun updateUI(data: DocumentData) {
        Glide.with(requireContext())
            .load(data.image)
            .into(binding.ivFood)

        binding.txtTitleFoodName.text = data.name
        binding.txtAsalDaerah.text = data.asal
        binding.txtBahanDasar.text = data.bahanDasar
        binding.txtFoodDesc.text = data.desc
        binding.txtFoodHistory.text = data.history
        binding.txtFunfact.text = data.funfact

        data.gizi?.let {
            binding.txtCalPerServing.text = it.servingSize
            binding.txtJmlKalori.text = it.calories
            binding.txtJmlKarbohidrat.text = it.carbohidrate
            binding.txtJmlLemak.text = it.fat
            binding.txtJmlProtein.text = it.protein
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(documentData: DocumentData): BottomSheetDialogResultFragment {
            val fragment = BottomSheetDialogResultFragment()
            val args = Bundle()
            args.putParcelable("DOCUMENT_DATA", documentData)
            fragment.arguments = args
            return fragment
        }
    }
}