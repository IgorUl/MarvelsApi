package ru.ulyakin.marvelapi

import android.app.Application
import ru.ulyakin.marvelapi.data.api.MarvelApiService
import ru.ulyakin.marvelapi.data.mapper.HeroesMapper
import ru.ulyakin.marvelapi.data.repository.MarvelRepository
import ru.ulyakin.marvelapi.ui.viewmodel.ViewModelFactory
import ru.ulyakin.marvelsapi.R

class MarvelApp : Application() {

    // TODO Navigation Component; Dagger Hilt;
    lateinit var factory: ViewModelFactory
        private set

    override fun onCreate() {
        super.onCreate()
        val baseUrl = getString(R.string.base_url)
        val api = MarvelApiService.create(baseUrl)
        val repo = MarvelRepository(api, HeroesMapper())
        factory = ViewModelFactory(repo)
    }
}