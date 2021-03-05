package ru.ulyakin.marvelapi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.ulyakin.marvelapi.data.MarvelRepository

class ViewModelFactory(private val repository: MarvelRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HeroesListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HeroesListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}