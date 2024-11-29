package com.example.rasanusa.ui.result

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rasanusa.data.response.DocumentData

class BottomSheetDialogResultViewModel : ViewModel() {
    private val _documentData = MutableLiveData<DocumentData?>()
    val documentData: LiveData<DocumentData?> get() = _documentData

    fun setDocumentData(data: DocumentData?) {
        _documentData.value = data
    }
}