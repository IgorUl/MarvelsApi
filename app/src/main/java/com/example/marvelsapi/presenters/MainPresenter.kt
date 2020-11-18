package com.example.marvelsapi.presenters

import android.util.Log
import com.example.marvelsapi.ApiConstants
import com.example.marvelsapi.contracts.MainContract
import com.example.marvelsapi.data.Hero
import com.example.marvelsapi.data.Model

class MainPresenter(private val model: Model, private val view: MainContract.MainView) {

    fun getHeroesList(): List<Hero?> =
        model.getHeroesList

    fun loadHeroes() = model.loadHeroList(object : MainContract.Network {
        override fun onSuccess(list: List<Hero?>) {
            if (model.getHeroesList.isNotEmpty() && model.getHeroesList[model.progressBarPosition] == model.getProgressBar) {
                model.removeProgressBar()
            }
            model.addList(list)
            view.updateHeroesView()
            view.setLoaded()
        }

        override fun onError() {
            //todo add snackbar for refresh
            Log.i("ONERROR", "CALL")
        }
    })

    fun onCreate() {
        view.updateHeroesView()
    }

    fun addProgressBar() {
        model.addProgressBar()
        view.notifyItemInsert(model.progressBarPosition)
    }
}