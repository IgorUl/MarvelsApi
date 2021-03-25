package ru.ulyakin.marvelapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import ru.ulyakin.marvelapi.data.model.Hero
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.ulyakin.marvelapi.data.repository.MarvelRepository

class HeroesListViewModel(private val repo: MarvelRepository) : ViewModel() {

    lateinit var heroesList: PagingData<Hero>

    init {
        fetchHeroes()
    }

    private fun fetchHeroes() {
        viewModelScope.launch {
            repo.fetchHero().cachedIn(viewModelScope).collectLatest { heroesList = it }
        }
    }
}