package com.example.rasanusa.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rasanusa.data.api.ApiConfig
import com.example.rasanusa.data.response.FoodResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {
    private val _listFood = MutableLiveData<FoodResponse?>()
    val listFood: LiveData<FoodResponse?> = _listFood

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        getData()
    }

    fun getData() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllFood()

        client.enqueue(object : Callback<FoodResponse> {
            override fun onResponse(call: Call<FoodResponse>, response: Response<FoodResponse>) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _listFood.value = response.body()

                } else {
                    _isLoading.value = false
                    Log.e("HomeViewModel", "Cannot get data!")
                }
            }

            override fun onFailure(call: Call<FoodResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("HomeViewModel", "onFailure: ${t.message}")
            }
        })
    }

}