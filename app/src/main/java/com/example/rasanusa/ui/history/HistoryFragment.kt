package com.example.rasanusa.ui.history

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rasanusa.R
import com.example.rasanusa.data.localdatabase.roomdatabase.FoodHistory
import com.example.rasanusa.data.response.DataItem
import com.example.rasanusa.databinding.FragmentHistoryBinding
import com.example.rasanusa.databinding.FragmentScanBinding
import com.example.rasanusa.ui.adapter.HistoryAdapter
import com.example.rasanusa.ui.detail.DetailActivity

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var historyAdapter: HistoryAdapter

    private val historyViewModel : HistoryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyViewModel.listHistoryFood.observe(viewLifecycleOwner) { historyResponse ->
            val histories = historyResponse ?: emptyList()
            setupRecyclerViewHistory(histories)
        }
        historyViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            showLoading(isLoading)
        }

        historyViewModel.getPredictionHistory(requireContext())

        binding.apply {
            bntBack.setOnClickListener {
                findNavController().navigateUp()
            }
            deleteHistory.setOnClickListener { showDialogDeleteHistory() }
        }

    }

    private fun setupRecyclerViewHistory(foodHistory: List<FoodHistory>) {
        historyAdapter = HistoryAdapter(foodHistory) { selectedFood ->
            val dataItem = DataItem(
                image = selectedFood.predictionResult.documentData?.image,
                name = selectedFood.predictionResult.documentData?.name,
                bahanDasar = selectedFood.predictionResult.documentData?.bahanDasar,
                asal = selectedFood.predictionResult.documentData?.asal,
                history = selectedFood.predictionResult.documentData?.history,
                desc = selectedFood.predictionResult.documentData?.desc,
                funfact = selectedFood.predictionResult.documentData?.funfact,
                gizi = selectedFood.predictionResult.documentData?.gizi
            )
            navigateToDetail(dataItem)
        }

        binding.rvHistory.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvHistory.adapter = historyAdapter
    }

    private fun showDialogDeleteHistory() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_history)
            .setMessage(R.string.delete_all_history)
            .setPositiveButton(R.string.setuju) { _, _ ->
                historyViewModel.deleteAllHistory()
                Toast.makeText(
                    requireContext(),
                    R.string.history_deleted,
                    Toast.LENGTH_SHORT
                ).show()
            }
            .setNegativeButton(R.string.tidak, null).show()
    }

    private fun navigateToDetail(itemFood: DataItem) {
        val intentDetail = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(DetailActivity.FOOD_ITEM, itemFood)
        }
        startActivity(intentDetail)
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}