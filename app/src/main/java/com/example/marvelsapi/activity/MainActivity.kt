package com.example.marvelsapi.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.marvelsapi.R
import com.example.marvelsapi.fragments.MainFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MainFragment())
            .commit()
    }

}
