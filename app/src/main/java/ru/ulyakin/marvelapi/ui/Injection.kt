package ru.ulyakin.marvelapi.ui

import androidx.lifecycle.ViewModelProvider
import ru.ulyakin.marvelapi.api.MarvelApiService
import ru.ulyakin.marvelapi.data.HeroesMapper
import ru.ulyakin.marvelapi.data.MarvelRepository
import ru.ulyakin.marvelapi.ui.viewmodel.ViewModelFactory

object Injection {

    private val api = MarvelApiService().init()

    private fun provideMarvelRepository(): MarvelRepository {
        return MarvelRepository(api, HeroesMapper())
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return ViewModelFactory(provideMarvelRepository())
    }
}