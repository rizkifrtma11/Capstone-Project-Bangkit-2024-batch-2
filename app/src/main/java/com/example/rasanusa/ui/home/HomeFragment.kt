package com.example.rasanusa.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rasanusa.Food
import com.example.rasanusa.FoodAdapter
import com.example.rasanusa.R
import com.example.rasanusa.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val list = ArrayList<Food>()
    private lateinit var rvFood: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val homeViewModel =
//            ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvFood = binding.rvFood

        list.addAll(getListFood())
        showRecyclerList()
    }

    private fun getListFood(): ArrayList<Food> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataAsalDaerah = resources.getStringArray(R.array.data_asal_daerah)
        val dataPhoto = resources.obtainTypedArray(R.array.food_photo)
        val listFood = ArrayList<Food>()
        for (i in dataName.indices) {
            val food = Food(dataName[i], dataAsalDaerah[i], dataPhoto.getResourceId(i, -1))
            listFood.add(food)
        }
        return listFood
    }

    private fun showRecyclerList() {
        rvFood.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        val foodAdapter = FoodAdapter(list)
        rvFood.adapter = foodAdapter
//        rvFood.scrollToPosition(0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}