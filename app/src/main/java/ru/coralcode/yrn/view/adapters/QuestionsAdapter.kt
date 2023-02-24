package ru.coralcode.yrn.view.adapters

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.coralcode.yrn.data.Question

class QuestionsAdapter(
    private var onQuestionClick: (Question) -> Unit
): ListAdapter<Question, QuestionsAdapter.ViewHolder>(QuestionsDiffUtilCallback()) {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    class QuestionsDiffUtilCallback: DiffUtil.ItemCallback<Question>() {

        override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean =
            oldItem.id == newItem.id


    }
}