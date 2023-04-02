package ru.coralcode.yrn.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.coralcode.yrn.data.QuestionsRepository
import ru.coralcode.yrn.data.models.QuestionCategory


class HomeViewModel: ViewModel() {

    private val repository = QuestionsRepository

    private var _questionCategories = MutableLiveData<List<QuestionCategory>>()
    val questionCategories = _questionCategories as LiveData<List<QuestionCategory>>

    private var _relativeQuestionsCount = MutableLiveData<String>()
    val relativeQuestionsCount = _relativeQuestionsCount as LiveData<String>

    private fun getDataForHomeScreen() {
        getRelativeQuestionsCountAsString()
        getCategories()
    }

    private fun getRelativeQuestionsCountAsString(): String {
        return repository.getRelativeQuestionCount().toString()
    }

    private fun getCategories() {
        _questionCategories.value = repository.getCategories()
    }

    init {
        getDataForHomeScreen()
    }

}