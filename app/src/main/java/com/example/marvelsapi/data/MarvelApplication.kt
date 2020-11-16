package com.example.marvelsapi.data

import android.app.Application
import com.example.marvelsapi.network.Loader

class MarvelApplication : Application() {

    lateinit var model: Model
        private set

    override fun onCreate() {
        super.onCreate()

        model = Model()
    }
}