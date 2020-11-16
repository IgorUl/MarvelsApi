package com.example.marvelsapi.data

import android.util.Log
import com.example.marvelsapi.contracts.MainContract
import com.example.marvelsapi.network.Loader

class Model {

    var progressBarPosition: Int = 0
    val getHeroesList: List<Hero?>
        get() = heroesList

    private val heroesList: MutableList<Hero?> = mutableListOf()

    private val loader = Loader()

    fun addProgressBar() {
        progressBarPosition = heroesList.size
        heroesList.add(null)
    }

    fun removeProgressBar() {
        Log.i("REMOVEBAR", "$progressBarPosition ++++++++  ${heroesList[progressBarPosition]}")
//        if (progressBarPosition == 0) {
            heroesList.removeAt(progressBarPosition)
//        } else {
//            heroesList.removeAt(progressBarPosition - 1)
//        }
        Log.i("LISTAFTERREMOVE", "$heroesList")
    }

    fun loadHeroList(network: MainContract.Network) {
        addProgressBar()
        loader.loadHeroesList(network)
        addList(loader.heroesList)
    }

    fun addList(list: List<Hero?>) {
        heroesList.addAll(list)
    }
}