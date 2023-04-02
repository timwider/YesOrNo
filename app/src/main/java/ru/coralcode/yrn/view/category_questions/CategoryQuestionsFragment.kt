package ru.coralcode.yrn.view.category_questions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.coralcode.yrn.R
import ru.coralcode.yrn.data.models.Question
import ru.coralcode.yrn.data.models.QuestionCategory
import ru.coralcode.yrn.databinding.CategoryQuestionsFragmentBinding
import ru.coralcode.yrn.view.BaseFragment
import ru.coralcode.yrn.view.adapters.QuestionsAdapter
import ru.coralcode.yrn.view.question.QuestionFragment

const val QUESTIONS_CATEGORY_ARGS_KEY = "questions_category_argument"
const val QUESTION_ARGS_KEY = "question_argument"

class CategoryQuestionsFragment: BaseFragment<CategoryQuestionsFragmentBinding, CategoryQuestionsViewModel>() {

    override val vm: CategoryQuestionsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!vm.areQuestionsLoaded()) startLoadingQuestions()

        setupObservers()
    }

    private fun startLoadingQuestions() {
        val category = requireArguments().getSerializable(QUESTIONS_CATEGORY_ARGS_KEY) as QuestionCategory
        vm.loadQuestionsForCategory(category)
    }

    private fun setupRecyclerView(rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = QuestionsAdapter( onQuestionClick = { question -> onQuestionClick(question) } )
    }

    private fun onQuestionClick(question: Question) {
        val questionFragment = QuestionFragment()
        val args = Bundle()
        args.putSerializable(QUESTIONS_CATEGORY_ARGS_KEY, question)

        parentFragmentManager.beginTransaction()
            .replace(R.id.fl, questionFragment)
            .addToBackStack(questionFragment::class.java.simpleName)
            .commit()
    }

    private fun setupObservers() {
        vm.questions.observe(viewLifecycleOwner) {
            (binding.rvCategoryQuestions.adapter as QuestionsAdapter).submitList(it)
        }
    }

    override fun initBinding() = CategoryQuestionsFragmentBinding.inflate(layoutInflater)

}