package ru.coralcode.yrn.view.category_questions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.coralcode.yrn.data.QuestionsRepository
import ru.coralcode.yrn.data.models.Question
import ru.coralcode.yrn.data.models.QuestionCategory
import ru.coralcode.yrn.data.models.QuestionPresentation

class CategoryQuestionsViewModel: ViewModel() {

    private val _questions = MutableLiveData<List<QuestionPresentation>>()
    val questions = _questions as LiveData<List<QuestionPresentation>>

    private var areQuestionsLoaded = false

    fun loadQuestionsForCategory(category: QuestionCategory) {
        _questions.value = QuestionsRepository.getQuestionsByCategory(category.name)
        areQuestionsLoaded = true
    }

    fun areQuestionsLoaded(): Boolean = areQuestionsLoaded
}