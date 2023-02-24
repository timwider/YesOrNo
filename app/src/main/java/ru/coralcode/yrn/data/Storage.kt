package ru.coralcode.yrn.data

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val QUESTIONS_FILENAME = "questions.json"

object Storage {

    private var _assets: AssetManager? = null
    private val assets get() = _assets!!

    suspend fun loadQuestions(): List<Question> {
        if (_assets == null) throw StorageNotInitializedException()
        return withContext(Dispatchers.IO) {
            val questionsJsonContent = assets.open(QUESTIONS_FILENAME).reader().readText()
            Gson().fromJson(questionsJsonContent, Array<Question>::class.java).toList()
        }
    }

    fun getRelativeQuestionsCount() = 200

    fun start(assetManager: AssetManager) {
        _assets = assetManager
    }
}

data class Question(
    val id: Int,
    val title: String,
    val content: String,
    val answer: String,
    val difficulty: String
    )


class StorageNotInitializedException: Exception() {
    override val message: String = "loadQuestions() in Storage class was called, but start(context: Context) was not."
}