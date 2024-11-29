package com.example.rasanusa.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rasanusa.data.response.DataItem
import com.example.rasanusa.ui.adapter.FoodAdapter
import com.example.rasanusa.databinding.FragmentHomeBinding
import com.example.rasanusa.ui.detail.DetailActivity

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var foodAdapter: FoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoading(true)
        homeViewModel.listFood.observe(viewLifecycleOwner) { eventResponse ->
            val foods = eventResponse?.data ?: emptyList()
            setupRecyclerView(foods)
        }
        homeViewModel.listFood.observe(viewLifecycleOwner) { _ ->
            showLoading(false)
        }
        homeViewModel.getData()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView(foods: List<DataItem>) {
        foodAdapter = FoodAdapter(foods){ selectedFood ->
            navuigateToDetail(selectedFood)
        }
        binding.rvFood.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvFood.adapter = foodAdapter
    }

    private fun navuigateToDetail(itemFood: DataItem){
        val intentDetail = Intent(requireContext(), DetailActivity::class.java).apply {
            putExtra(DetailActivity.FOOD_ITEM, itemFood)
        }
        startActivity(intentDetail)
    }

    private fun showLoading(isLoading: Boolean){
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}