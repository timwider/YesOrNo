package ru.coralcode.yrn.data

import android.content.Context
import android.content.SharedPreferences

private const val PREFERENCES_NAME = "general_app_preferences"
private const val FAVORITES_KEY = "favorite_questions"

class QuestionPreferencesRepository(context: Context): QuestionPreferences {

    private val sp: SharedPreferences by lazy { initialize(context) }

    private fun initialize(context: Context): SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)


    override fun checkFavorite(questionId: Int): Boolean {
        val favorites = sp.getStringSet(FAVORITES_KEY, null) ?: mutableSetOf()
        return favorites.contains(questionId.toString())
    }

    override fun setFavorite(questionId: Int) {
        val newObject = getDataForEditing()
        newObject.add(questionId.toString())
        sp.edit().putStringSet(FAVORITES_KEY, newObject).apply()
    }

    override fun removeFavorite(questionId: Int) {
        val newObject = getDataForEditing()
        newObject.remove(questionId.toString())
        sp.edit().putStringSet(FAVORITES_KEY, newObject).apply()
    }

    override fun getDataForEditing(): MutableSet<String> {
        val newObject = mutableSetOf<String>()
        val oldObject = sp.getStringSet(FAVORITES_KEY, null) ?: mutableSetOf()
        if (oldObject.isNotEmpty()) newObject.addAll(oldObject)
        return newObject
    }

    override fun getFavorites(): Set<Int> {
        val favoritesAsInts = mutableSetOf<Int>()
        getDataForEditing().forEach { favoritesAsInts.add(it.toInt()) }
        return favoritesAsInts.toSet()
    }
}

interface QuestionPreferences {

    fun checkFavorite(questionId: Int): Boolean

    fun setFavorite(questionId: Int)

    fun removeFavorite(questionId: Int)

    fun getDataForEditing(): MutableSet<String>

    fun getFavorites(): Set<Int>

}