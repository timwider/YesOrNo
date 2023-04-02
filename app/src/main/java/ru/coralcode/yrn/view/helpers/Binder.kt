package ru.coralcode.yrn.view.helpers

import ru.coralcode.yrn.R

class Binder {

    class Category {
        fun bindCategoryNameToDrawableId(categoryName: String): Int {
            return when (categoryName) {
                "detective" -> R.drawable.category_detective
                "scary" -> R.drawable.category_scary
                "fairytale" -> R.drawable.category_fairytale
                "brief" -> R.drawable.category_brief
                "history" -> R.drawable.category_history
                else -> throw CategoryNotFoundException()
            }
        }
    }

    class CategoryNotFoundException: Exception() {
        override var message: String = "Could not find category by supplied name."
    }
}