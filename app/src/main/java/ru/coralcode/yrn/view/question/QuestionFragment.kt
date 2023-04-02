package ru.coralcode.yrn.view.question

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ru.coralcode.yrn.R
import ru.coralcode.yrn.data.models.QuestionPresentation
import ru.coralcode.yrn.databinding.QuestionFragmentBinding
import ru.coralcode.yrn.view.BaseFragment
import ru.coralcode.yrn.view.category_questions.QUESTIONS_CATEGORY_ARGS_KEY

const val TEST_QUESTION_ARGS_KEY = "test_question_args_key"
private const val CARD_ROTATION_VALUE = 180F
private const val BASE_CAMERA_ROTATION_DISTANCE = 8000
private const val CARD_FLIP_ANIMATION_DURATION = 500L

class QuestionFragment: BaseFragment<QuestionFragmentBinding, QuestionViewModel>() {

    override val vm: QuestionViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()

        val question = requireArguments().getInt(TEST_QUESTION_ARGS_KEY)
    }

    private fun bindQuestionData(question: QuestionPresentation) {
        binding.questionDetailsTvQuestionTitle.text = question.title
        binding.questionDetailsTvQuestionText.text = question.content
        binding.questionDetailsTvTimeToSolve.text = question.timeToSolve
        val isFavoriteDrawableId = if (question.isFavorite) R.drawable.ic_favorite_filled else R.drawable.ic_favorite
        binding.questionDetailsIvFavorite.setImageResource(isFavoriteDrawableId)

        setClickListeners(question)
    }

    private fun setClickListeners(question: QuestionPresentation) {
        binding.questionDetailsBtnShowAnswer.setOnClickListener {
            flipCard(question)
        }

        binding.questionDetailsIvLikeQuestion.setOnClickListener {
            vm.onQuestionRate(QuestionRateState.LIKE)
        }

        binding.questionDetailsIvDislikeQuestion.setOnClickListener {
            vm.onQuestionRate(QuestionRateState.DISLIKE)
        }

        binding.questionDetailsTvBackToList.setOnClickListener {
            requireActivity().onBackPressed()
        }

        binding.questionDetailsTvNextQuestion.setOnClickListener {
            vm.loadNextQuestion()
        }
    }

    private fun setupObservers() {
        vm.questionRateState.observe(viewLifecycleOwner) {
            handleNewQuestionRateState(it)
        }

        vm.isFavorite.observe(viewLifecycleOwner) {
            val isFavoriteDrawableId = if (it) R.drawable.ic_favorite_filled else R.drawable.ic_favorite
            binding.questionDetailsIvFavorite.setImageResource(isFavoriteDrawableId)
        }

        vm.currentQuestion.observe(viewLifecycleOwner) {
            bindQuestionData(it)
        }
    }

    private fun handleNewQuestionRateState(@RateState newState: Int) {
        resetQuestionRateState()
        when (newState) {
            QuestionRateState.LIKE -> {
                binding.questionDetailsIvLikeQuestion.setImageResource(R.drawable.ic_like_question_filled)
            }
            QuestionRateState.DISLIKE -> {
                binding.questionDetailsIvDislikeQuestion.setImageResource(R.drawable.ic_dont_like_question_filled)
            }
            QuestionRateState.NOT_RATED -> return
        }
    }

    private fun resetQuestionRateState() {
        binding.questionDetailsIvLikeQuestion.setImageResource(R.drawable.ic_like_question)
        binding.questionDetailsIvDislikeQuestion.setImageResource(R.drawable.ic_dont_like_question)
    }

    private fun flipCard(question: QuestionPresentation) {

        val currentState = vm.getContentCardFlipState()
        val cameraDistance = BASE_CAMERA_ROTATION_DISTANCE * requireContext().resources.displayMetrics.density
        binding.questionDetailsCvContent.cameraDistance = cameraDistance

        val textToShow: String
        val showAnswerButtonText: String
        if (currentState == ContentCardState.SHOWING_CONTENT) {
            textToShow = question.answer
            showAnswerButtonText = getString(R.string.hide_answer)
        } else {
            textToShow = question.content
            showAnswerButtonText = getString(R.string.show_answer)
        }

        val shouldRotateTextView = currentState == ContentCardState.SHOWING_CONTENT
        var haveRotated = false
        binding.questionDetailsCvContent
            .animate()
            .rotationYBy(CARD_ROTATION_VALUE)
            .withStartAction {
                binding.questionDetailsBtnShowAnswer.isClickable = false
                binding.questionDetailsBtnShowAnswer.text = showAnswerButtonText
            }
            .withEndAction { binding.questionDetailsBtnShowAnswer.isClickable = true }
            .setUpdateListener {
                if ((it.animatedValue as Float) >= 0.5f && !haveRotated) {
                    haveRotated = true
                    binding.questionDetailsTvQuestionText.text = textToShow
                    if (shouldRotateTextView) binding.questionDetailsTvQuestionText.rotationY = 180f else binding.questionDetailsTvQuestionText.rotationY = 0f
                }
            }
            .duration = CARD_FLIP_ANIMATION_DURATION

        vm.onCardFlip()
    }

    override fun initBinding() = QuestionFragmentBinding.inflate(layoutInflater)
}
