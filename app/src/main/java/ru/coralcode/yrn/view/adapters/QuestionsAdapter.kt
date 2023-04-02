package ru.coralcode.yrn.view.adapters

import android.media.Image
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.coralcode.yrn.R
import ru.coralcode.yrn.data.models.Question
import ru.coralcode.yrn.data.models.QuestionPresentation

class QuestionsAdapter(
    private var onQuestionClick: (Question) -> Unit
): ListAdapter<QuestionPresentation, QuestionsAdapter.ViewHolder>(QuestionsDiffUtilCallback()) {


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvQuestionTitle: TextView = getView(R.id.list_tv_question_title)
        val tvTimeToSolve: TextView = getView(R.id.list_tv_time_to_solve)
        val ivDifficulty: ImageView = getView(R.id.iv_difficulty)
        val ivFavorite: ImageView = getView(R.id.iv_favorite)

        private fun <T> getView(id: Int): T = itemView.findViewById(id) as T
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.questions_recyclerview_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentQuestion = getItem(position)
        holder.tvQuestionTitle.text = currentQuestion.title
        holder.tvTimeToSolve.text = currentQuestion.timeToSolve
        val difficultyDrawableId = when (currentQuestion.difficulty) {
            "easy" -> R.drawable.ic_difficulty_easy
            "medium" -> R.drawable.ic_difficulty_medium
            "hard" -> R.drawable.ic_difficulty_hard
            else -> R.drawable.ic_difficulty_medium
        }
        val isFavoriteDrawableId = if (currentQuestion.isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_filled
        holder.ivFavorite.setImageResource(isFavoriteDrawableId)
        holder.ivDifficulty.setImageResource(difficultyDrawableId)
    }

    class QuestionsDiffUtilCallback: DiffUtil.ItemCallback<QuestionPresentation>() {

        override fun areItemsTheSame(oldItem: QuestionPresentation, newItem: QuestionPresentation): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: QuestionPresentation, newItem: QuestionPresentation): Boolean =
            oldItem.id == newItem.id

    }
}