package com.example.marvelsapi.data.model

import com.example.marvelsapi.ui.contracts.MainContract
import com.example.marvelsapi.data.Loader

class Model {

    var isItemsLoading: Boolean = false
    var progressBarPosition: Int = 0
    val getHeroesList: List<Hero?>
        get() = heroesList

    private val heroesList: MutableList<Hero?> = mutableListOf()

    private val loader = Loader()

    //заглушка для progress bar
    val getProgressBar: Hero
        get() = progressBarHero

    private val progressBarHero = Hero(-1, "progressBar", "", null)


    fun addProgressBarItem() {
        progressBarPosition = heroesList.size
        heroesList.add(progressBarHero)
    }

    fun removeProgressBar() {
        heroesList.removeAt(progressBarPosition)
    }

    fun loadHeroList(network: MainContract.Network) {
        loader.loadHeroesList(network)
        addList(loader.heroesList)
    }

    fun addList(list: List<Hero?>) {
        heroesList.addAll(list)
    }
}