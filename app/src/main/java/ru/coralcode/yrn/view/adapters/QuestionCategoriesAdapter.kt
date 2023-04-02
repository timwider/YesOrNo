package ru.coralcode.yrn.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.coralcode.yrn.R
import ru.coralcode.yrn.data.models.QuestionCategory
import ru.coralcode.yrn.view.helpers.Binder

class QuestionCategoriesAdapter(
    private val onCategoryClick: (QuestionCategory) -> Unit
): ListAdapter<QuestionCategory, QuestionCategoriesAdapter.ViewHolder>(QuestionCategoriesDiffUtilCallback()) {

    private val categoryBinder = Binder.Category()

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvCategory: TextView = itemView.findViewById(R.id.tv_category_name)
        val ivCategory: ImageView = itemView.findViewById(R.id.iv_category)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.questions_recyclerview_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = bindData(holder, position)

    private fun bindData(holder: ViewHolder, pos: Int) {
        val currentItem = getItem(pos)
        holder.tvCategory.text = currentItem.displayName
        val currentDrawableId = categoryBinder.bindCategoryNameToDrawableId(currentItem.name)
        val drawable = ResourcesCompat.getDrawable(holder.ivCategory.context.resources, currentDrawableId, null)
        holder.ivCategory.setImageDrawable(drawable)
        holder.itemView.setOnClickListener { onCategoryClick.invoke(currentItem) }
    }

    class QuestionCategoriesDiffUtilCallback: DiffUtil.ItemCallback<QuestionCategory>() {

        override fun areItemsTheSame(oldItem: QuestionCategory, newItem: QuestionCategory): Boolean =
            oldItem.name == newItem.name
        override fun areContentsTheSame(oldItem: QuestionCategory, newItem: QuestionCategory): Boolean =
            oldItem.name == newItem.name
    }
}