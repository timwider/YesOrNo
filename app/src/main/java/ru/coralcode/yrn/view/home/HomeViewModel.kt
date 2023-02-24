package ru.coralcode.yrn.view.home

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.coralcode.yrn.data.Question
import ru.coralcode.yrn.data.Storage

class HomeViewModel: ViewModel() {

    fun getRelativeQuestionsCountAsString(): String {
        val num = Storage.getRelativeQuestionsCount()
        return "$num+"
    }

}