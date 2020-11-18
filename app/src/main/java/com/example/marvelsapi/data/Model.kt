package com.example.marvelsapi.data

import com.example.marvelsapi.contracts.MainContract
import com.example.marvelsapi.network.Loader

class Model {

    var progressBarPosition: Int = 0
    val getHeroesList: List<Hero?>
        get() = heroesList

    private val heroesList: MutableList<Hero?> = mutableListOf()

    private val loader = Loader()

    //заглушка для progress bara
    val getProgressBar: Hero
        get() = progressBarHero

    private val progressBarHero = Hero(-1, "progressBar", "", null)


    fun addProgressBar() {
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