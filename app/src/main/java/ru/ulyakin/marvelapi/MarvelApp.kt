package ru.ulyakin.marvelapi

import android.app.Application
import ru.ulyakin.marvelapi.data.api.MarvelApiService
import ru.ulyakin.marvelapi.data.mapper.HeroesMapper
import ru.ulyakin.marvelapi.data.repository.MarvelRepository
import ru.ulyakin.marvelapi.ui.viewmodel.ViewModelFactory
import ru.ulyakin.marvelsapi.R

class MarvelApp : Application() {

    lateinit var factory: ViewModelFactory
        private set

    override fun onCreate() {
        super.onCreate()
        val api = MarvelApiService.create(getString(R.string.base_url))
        val repo = MarvelRepository(api, HeroesMapper())
        factory = ViewModelFactory(repo)
    }
}