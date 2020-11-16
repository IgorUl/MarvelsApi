package com.example.marvelsapi.presenters

import android.util.Log
import com.example.marvelsapi.ApiConstants
import com.example.marvelsapi.contracts.MainContract
import com.example.marvelsapi.data.Hero
import com.example.marvelsapi.data.Model

class MainPresenter(private val model: Model, private val view: MainContract.MainView) {

    fun getHeroesList(): List<Hero?> =
        model.getHeroesList

    fun loadHeroes() = model.loadHeroList(object: MainContract.Network {
        override fun onSuccess(list: List<Hero?>) {
            Log.i("ONSUCCESS+++", "$list")
            model.removeProgressBar()
            model.addList(list)
            view.updateHeroesView()
            view.setLoaded()
            Log.i("LOADED", "${view.getLoaded()}")


        }
        override fun onError() {
            Log.i("ONERROR", "CALL")
            TODO("Not yet implemented")
        }
    })
}