package ru.coralcode.yrn.data.models


data class Question(
    val id: Int,
    val title: String,
    val content: String,
    val answer: String,
    val difficulty: String,
    val timeToSolve: String,
    val categories: List<String>
): java.io.Serializable

data class QuestionPresentation(
    val id: Int,
    val title: String,
    val content: String,
    val answer: String,
    val difficulty: String,
    val timeToSolve: String,
    val isFavorite: Boolean,
    val categories: List<String>
): java.io.Serializable