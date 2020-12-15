package com.example.marvelsapi

import android.app.Application
import com.example.marvelsapi.data.model.Model

class MarvelApplication : Application() {

    lateinit var model: Model
        private set

    override fun onCreate() {
        super.onCreate()

        model = Model()
    }
}