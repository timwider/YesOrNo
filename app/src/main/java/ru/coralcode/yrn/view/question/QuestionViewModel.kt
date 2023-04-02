package ru.coralcode.yrn.view.question

import androidx.annotation.IntDef
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.coralcode.yrn.data.QuestionsRepository
import ru.coralcode.yrn.data.models.QuestionPresentation

class QuestionViewModel: ViewModel() {

    private var cardState = ContentCardState.SHOWING_CONTENT

    private var questionId = 0

    private var _questionRateState = MutableLiveData(QuestionRateState.NOT_RATED)
    val questionRateState = _questionRateState as LiveData<Int>

    private var _isFavorite = MutableLiveData<Boolean>()
    val isFavorite = _isFavorite as LiveData<Boolean>

    private var _currentQuestion = MutableLiveData<QuestionPresentation>()
    val currentQuestion = _currentQuestion as LiveData<QuestionPresentation>

    private val questionsQueue = mutableListOf<Int>()
    private var currentQuestionIndex = 0

    fun loadNextQuestion() {
        currentQuestionIndex++
//        _currentQuestion.value = questionsQueue[currentQuestionIndex]
    }

    fun setQuestionsQueue(value: List<Int>) {
        questionsQueue.addAll(value)
    }

    fun onCardFlip() {
        cardState = if (cardState == ContentCardState.SHOWING_CONTENT) ContentCardState.SHOWING_ANSWER else ContentCardState.SHOWING_CONTENT
    }

    fun getContentCardFlipState(): ContentCardState = cardState

    fun onQuestionRate(@RateState newState: Int) {
        _questionRateState.value = if (newState == _questionRateState.value) {
            QuestionRateState.NOT_RATED
        } else newState
    }

    fun toggleFavorite() {
        _isFavorite.flipBooleanValue(
            onFalse = { QuestionsRepository.removeQuestionFromFavorites(questionId) },
            onTrue = { QuestionsRepository.addQuestionToFavorites(questionId) }
        )
    }

    fun getQuestionRate() = _questionRateState

    fun setQuestionId(id: Int) {
        questionId = id
        val isFavorite = QuestionsRepository.checkIfQuestionIsFavorite(id)
        _isFavorite.value = isFavorite
    }
}

enum class ContentCardState {
    SHOWING_CONTENT,
    SHOWING_ANSWER
}

class QuestionRateState {

    companion object States {
        const val NOT_RATED = 0
        const val LIKE = 1
        const val DISLIKE = 2
    }
}

@IntDef(QuestionRateState.NOT_RATED, QuestionRateState.LIKE, QuestionRateState.DISLIKE)
annotation class RateState

/**
 * This is dope.
 */
fun MutableLiveData<Boolean>.flipBooleanValue(onFalse: () -> Unit, onTrue: () -> Unit) {
    if (this.value == true) {
        this.value = false
        onFalse.invoke()
    } else {
        this.value = true
        onTrue.invoke()
    }
}