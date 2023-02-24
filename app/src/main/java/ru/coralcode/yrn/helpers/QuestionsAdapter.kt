package ru.coralcode.yrn.helpers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.coralcode.yrn.R
import ru.coralcode.yrn.data.Question

class QuestionsAdapter: ListAdapter<Question, QuestionsAdapter.ViewHolder>(DiffUtilCallback()) {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private var tvQuestionTitle: TextView = itemView.findViewById(R.id.tv_question_title)

    }

    class DiffUtilCallback: DiffUtil.ItemCallback<Question>() {
        override fun areItemsTheSame(oldItem: Question, newItem: Question): Boolean =
            oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Question, newItem: Question): Boolean =
            oldItem.id == newItem.id
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.question_item_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }
}