package com.example.rasanusa.ui.history

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rasanusa.data.localdatabase.roomdatabase.FoodHistory
import com.example.rasanusa.data.localdatabase.roomdatabase.FoodHistoryDao
import com.example.rasanusa.data.localdatabase.roomdatabase.FoodHistoryRoomDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryViewModel (application: Application) : AndroidViewModel(application) {

    private val _listHistoryFood = MutableLiveData<List<FoodHistory>?>()
    val listHistoryFood: LiveData<List<FoodHistory>?> = _listHistoryFood

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val historyDao: FoodHistoryDao =
        FoodHistoryRoomDatabase.getDatabase(application).foodHistoryDao()

    val allHistoryLiveData: LiveData<List<FoodHistory>> = historyDao.getAllHistory()

    init {

        allHistoryLiveData.observeForever { historyList ->
            _listHistoryFood.value = historyList
        }
    }

    fun getPredictionHistory(context: Context) {
        viewModelScope.launch {
            _isLoading.value = true
            _isLoading.value = false
        }
    }

    fun deleteAllHistory() = viewModelScope.launch {
        _isLoading.value = true
        withContext(Dispatchers.IO){
            historyDao.deleteAllHistory()
        }
        _listHistoryFood.value = emptyList()
        _isLoading.value = false
    }
}