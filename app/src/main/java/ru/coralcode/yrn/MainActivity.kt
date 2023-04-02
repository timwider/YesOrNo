package ru.coralcode.yrn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.coralcode.yrn.data.models.QuestionPresentation
import ru.coralcode.yrn.view.home.HomeFragment
import ru.coralcode.yrn.view.question.QuestionFragment
import ru.coralcode.yrn.view.question.TEST_QUESTION_ARGS_KEY

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        if (savedInstanceState == null) {
//            supportFragmentManager.beginTransaction()
//                .replace(R.id.fl, HomeFragment())
//                .commit()
//        }

        goToTestFragment(savedInstanceState)
    }

    fun goToTestFragment(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            val fragment = QuestionFragment()
            val args = Bundle()
            val testQuestion = QuestionPresentation(
                id = 2000,
                title = "Человек со спичкой",
                content = "Голый человек был найден мертвым посреди пустыни. В его руке была сгоревшая спичка. Что произошло?\n" +
                        "\n",
                answer = "Человек летел с другими людьми на воздушном шаре. Воздушный шар начал падать и чтобы сделать его легче, они выкинули все вещи, в том числе всю свою одежду. Этого было недостаточно и они решили, что один из них должен прыгнуть для спасения остальных. Они договорились тянуть спичку: кому выпадет сгоревшая спичка, тому придется прыгать. Этому человеку попалась сгоревшая спичка и он прыгнул, как они и договаривались.\n" +
                        "\n",
                timeToSolve = "18 минут",
                difficulty = "hard",
                categories = listOf("detective"),
                isFavorite = false
            )
            args.putSerializable(TEST_QUESTION_ARGS_KEY, testQuestion)
            fragment.arguments = args

            supportFragmentManager.beginTransaction()
                .replace(R.id.fl, fragment)
                .commit()
        }
    }
}