package ru.ulyakin.marvelapi.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import ru.ulyakin.marvelapi.model.Hero
import kotlinx.coroutines.flow.Flow
import ru.ulyakin.marvelapi.data.MarvelRepository

class HeroesListViewModel(private val repo: MarvelRepository): ViewModel() {

    fun getHero(): Flow<PagingData<Hero>> {
        return repo.getHeroesList()
            .cachedIn(viewModelScope)
    }
}