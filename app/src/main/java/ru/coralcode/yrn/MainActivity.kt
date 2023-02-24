package ru.coralcode.yrn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.coralcode.yrn.view.home.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fl, HomeFragment())
                .commit()
        }
    }
}