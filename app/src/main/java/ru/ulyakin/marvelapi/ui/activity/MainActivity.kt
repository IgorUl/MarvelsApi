package ru.ulyakin.marvelapi.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.ulyakin.marvelapi.ui.fragments.HeroListFragment
import ru.ulyakin.marvelsapi.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, HeroListFragment())
                .commit()
        }
    }
}