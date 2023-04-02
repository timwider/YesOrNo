package ru.coralcode.yrn.view.home
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.coralcode.yrn.R
import ru.coralcode.yrn.data.models.QuestionCategory
import ru.coralcode.yrn.databinding.HomeFragmentBinding
import ru.coralcode.yrn.view.BaseFragment
import ru.coralcode.yrn.view.adapters.QuestionCategoriesAdapter
import ru.coralcode.yrn.view.help.HelpBottomSheetDialogFragment
import ru.coralcode.yrn.view.question.QuestionFragment


private const val QUESTION_CATEGORY_ARGS_KEY = "question_category_argument"

class HomeFragment: BaseFragment<HomeFragmentBinding, HomeViewModel>() {

    override val vm: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupRecyclerView(binding.rvCategories)

        binding.tvHelp.setOnClickListener { showHelpDialog() }
    }

    private fun showHelpDialog() {
        HelpBottomSheetDialogFragment().show(parentFragmentManager, null)
    }

    private fun setupRecyclerView(rv: RecyclerView) {
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = QuestionCategoriesAdapter(onCategoryClick = {category -> onCategoryClick(category)})
        rv.setHasFixedSize(true)
    }

    private fun onCategoryClick(category: QuestionCategory) {
        val questionFragment = QuestionFragment()
        val arguments = Bundle()
        arguments.putSerializable(QUESTION_CATEGORY_ARGS_KEY, category)
        questionFragment.arguments = arguments

        parentFragmentManager.beginTransaction()
            .replace(R.id.fl, QuestionFragment())
            .addToBackStack(QuestionFragment::class.java.simpleName)
            .commit()
    }

    private fun setupObservers() {
        vm.relativeQuestionsCount.observe(viewLifecycleOwner) { questionCount ->
            binding.tvQuestionsNumber.text = getString(R.string.all_questions_count, questionCount)
        }

        vm.questionCategories.observe(viewLifecycleOwner) { categoriesList ->
            (binding.rvCategories.adapter as QuestionCategoriesAdapter).submitList(categoriesList)
        }
    }

    override fun initBinding() = HomeFragmentBinding.inflate(layoutInflater)
}