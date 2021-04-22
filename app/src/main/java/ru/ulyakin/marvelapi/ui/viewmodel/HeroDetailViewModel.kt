package ru.ulyakin.marvelapi.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.ulyakin.marvelapi.data.model.Hero
import ru.ulyakin.marvelapi.data.repository.MarvelRepository

class HeroDetailViewModel(private val repo: MarvelRepository): ViewModel() {

    private var hero: MutableLiveData<Hero> = MutableLiveData()
    var getHero: LiveData<Hero> = hero


    suspend fun loadDetailedMovie(id: Int) {
        repo.fetchHeroDetail(id)
//            repo.fetchDetailedMovieFromResponseTask(movieId, object : LoadDetailedMovieCallback {
//            override fun onSuccess(detailedMovie: DetailedMovie) {
//                hero.value = detailedMovie
//            }
//
//            override fun onError() {
//                TODO("Not yet implemented")
//            }
//
//        })
    }

}