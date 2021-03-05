package ru.ulyakin.marvelapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import ru.ulyakin.marvelapi.model.Hero
import kotlinx.coroutines.flow.Flow
import ru.ulyakin.marvelapi.data.MarvelRepository

class HeroesListViewModel(private val repo: MarvelRepository) : ViewModel() {

    private var currentHeroesList: Flow<PagingData<Hero>>? = null

    fun getHero(): Flow<PagingData<Hero>> {
        val lastResult = currentHeroesList
        if (lastResult != null) {
            return lastResult
        }
        val newResult: Flow<PagingData<Hero>> = repo.getHeroesList()
            .cachedIn(viewModelScope)
        currentHeroesList = newResult
        return newResult
    }
}