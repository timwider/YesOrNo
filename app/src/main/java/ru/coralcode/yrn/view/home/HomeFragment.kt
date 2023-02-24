package ru.coralcode.yrn.view.home
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ru.coralcode.yrn.R
import ru.coralcode.yrn.databinding.HomeFragmentBinding
import ru.coralcode.yrn.view.BaseFragment

class HomeFragment: BaseFragment<HomeFragmentBinding, HomeViewModel>() {

    override val vm: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvQuestionsNumber.text = getString(
            R.string.all_questions_count,
            vm.getRelativeQuestionsCountAsString()
        )

        binding.tvHelp.setOnClickListener {

        }
    }

    override fun initBinding() = HomeFragmentBinding.inflate(layoutInflater)
}