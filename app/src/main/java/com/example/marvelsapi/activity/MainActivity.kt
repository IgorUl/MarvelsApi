package com.example.marvelsapi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marvelsapi.R
import com.example.marvelsapi.fragments.HeroListFragment

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
