package ru.coralcode.yrn.data

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson
import ru.coralcode.yrn.BuildConfig
import ru.coralcode.yrn.data.models.Question
import ru.coralcode.yrn.data.models.QuestionCategory
import ru.coralcode.yrn.data.models.QuestionPresentation
import ru.coralcode.yrn.extensions.log

/**
 * In the first version of the app, this repository acts as DataSource.
 * If we'll need to extend it's functionality in the future, we'll implement DataSource interface
 * in another class, and this repository will interact with it.
 */

private const val QUESTIONS_FILENAME = "main/questions.json"
private const val NUM_FOR_RELATIVE_QUESTIONS_COUNT = 100
private const val NUM_FOR_RELATIVE_Q_IN_CATEGORY_COUNT = 10

object QuestionsRepository: DataSource  {

    private var questionsCache = mutableListOf<Question>()

    private var assetManagerContainer: AssetManager? = null
    private val assetManager get() = assetManagerContainer!!

    private var questionPreferencesRepositoryContainer: QuestionPreferencesRepository? = null
    private val questionPreferencesRepository get() = questionPreferencesRepositoryContainer!!

    private fun isInitialized(): Boolean = assetManagerContainer != null

    fun initialize(appContext: Context) {
        if (isInitialized()) return
        assetManagerContainer = appContext.assets
        initializeQuestionPreferencesRepository(appContext)
    }

    private fun initializeQuestionPreferencesRepository(context: Context) {
        questionPreferencesRepositoryContainer = QuestionPreferencesRepository(context)
    }

    fun checkIfQuestionIsFavorite(questionId: Int): Boolean =
        questionPreferencesRepository.checkFavorite(questionId)

    fun addQuestionToFavorites(questionId: Int) =
        questionPreferencesRepository.setFavorite(questionId)

    fun removeQuestionFromFavorites(questionId: Int) =
        questionPreferencesRepository.removeFavorite(questionId)

    fun getFavoriteQuestions(): List<QuestionPresentation> {
        val ids = questionPreferencesRepository.getFavorites()
        val questionsPresentation = mutableListOf<QuestionPresentation>()
        val favoriteQuestions = mutableListOf<Question>()
        if (!areQuestionsCached()) cacheAllQuestions()
        for (question in questionsCache) {
            if (ids.contains(question.id)) favoriteQuestions.add(question)
        }

        for (favoriteQuestion in favoriteQuestions) {
            questionsPresentation.add(mapQuestionToPresentation(favoriteQuestion, true))
        }

        return questionsPresentation
    }

    override fun getQuestionsByCategory(category: String): List<QuestionPresentation> {
        if (!areQuestionsCached()) cacheAllQuestions()
        val byCategory = mutableListOf<Question>()
        for (question in questionsCache) {
            if (question.categories.contains(category)) byCategory.add(question)
        }
        return getQuestionsPresentation(byCategory)
    }

    override fun getQuestionById(id: Int): QuestionPresentation {
        if (questionsCache.isEmpty()) cacheAllQuestions()
        val question =  questionsCache.find { it.id == id } ?: throw InvalidIdException()
        return mapQuestionToPresentation(question)
    }

    override fun getRelativeQuestionCount(): Int {
        if (!areQuestionsCached()) cacheAllQuestions()
        return (questionsCache.size / NUM_FOR_RELATIVE_QUESTIONS_COUNT) * NUM_FOR_RELATIVE_QUESTIONS_COUNT
    }

    override fun getRelativeQuestionCountByCategory(category: String): Int {
        if (!areQuestionsCached()) cacheAllQuestions()
        var count = 0
        questionsCache.forEach { if (it.categories.contains(category)) count++ }
        return (count * NUM_FOR_RELATIVE_Q_IN_CATEGORY_COUNT) / NUM_FOR_RELATIVE_Q_IN_CATEGORY_COUNT
    }

    private fun cacheAllQuestions() {
        if (questionsCache.isNotEmpty()) return
        val jsonString = assetManager.open(QUESTIONS_FILENAME).reader().readText()
        val questionsFromJson = Gson().fromJson(jsonString, Array<Question>::class.java).toList()
        questionsCache.addAll(questionsFromJson)
    }

    private fun getQuestionsPresentation(questions: List<Question>): List<QuestionPresentation> {
        val questionsPresentation = mutableListOf<QuestionPresentation>()

        for (question in questions) {
            questionsPresentation.add(mapQuestionToPresentation(question))
        }
        return questionsPresentation.toList()
    }

    private fun mapQuestionToPresentation(question: Question): QuestionPresentation {
        val isFavorite = checkIfQuestionIsFavorite(question.id)
        return QuestionPresentation(
            id = question.id,
            title = question.title,
            content = question.content,
            answer = question.answer,
            timeToSolve = question.timeToSolve,
            isFavorite = isFavorite,
            categories = question.categories,
            difficulty = question.difficulty
        )
    }

    private fun mapQuestionToPresentation(question: Question, isFavorite: Boolean): QuestionPresentation {
        return QuestionPresentation(
            id = question.id,
            title = question.title,
            content = question.content,
            answer = question.answer,
            timeToSolve = question.timeToSolve,
            isFavorite = isFavorite,
            categories = question.categories,
            difficulty = question.difficulty
        )
    }

    fun getCategories(): List<QuestionCategory> {
        return listOf(
            QuestionCategory(name = "scary", displayName = "Страшные"),
            QuestionCategory(name = "brief", displayName = "Короткие"),
            QuestionCategory(name = "detective", displayName = "Детектив"),
            QuestionCategory(name = "history", displayName = "Исторические"),
            QuestionCategory(name = "fairytale", displayName = "Сказочные")
        )
    }

    private fun areQuestionsCached(): Boolean = questionsCache.size > 0
}

class DataSourceNotInitializedException: Exception() {
    override var message: String = "initialize(appContext: Context) wasn't called before fetching data"
}

class InvalidIdException: Exception() {
    override var message: String = "There is no question with supplied ID."
}

interface DataSource {

    fun getQuestionsByCategory(category: String): List<QuestionPresentation>

    fun getQuestionById(id: Int): QuestionPresentation

    fun getRelativeQuestionCount(): Int

    fun getRelativeQuestionCountByCategory(category: String): Int
}