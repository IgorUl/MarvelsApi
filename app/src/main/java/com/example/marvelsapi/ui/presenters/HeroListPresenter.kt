package com.example.marvelsapi.ui.presenters

import com.example.marvelsapi.ui.contracts.MainContract
import com.example.marvelsapi.data.model.Hero
import com.example.marvelsapi.data.model.Model

class HeroListPresenter(private val model: Model, private val view: MainContract.MainView) {

    fun getHeroesList(): List<Hero?> =
        model.getHeroesList

    fun loadHeroes() = model.loadHeroList(object : MainContract.Network {
        //длинную строку по переменным
        override fun onSuccess(list: List<Hero?>) {
            if (model.getHeroesList.isNotEmpty() && model.getHeroesList[model.progressBarPosition] == model.getProgressBar) {
                model.removeProgressBar()
            }
            model.addList(list)
            view.updateHeroesView()
            view.setLoaded()
        }

        override fun onError() {
            view.showRefreshSnackbar()
        }
    })

    fun onCreate() {
        val lastItemID = model.getHeroesList.last()?.id
        if (lastItemID == -1) {
            model.removeProgressBar()
        }
        view.updateHeroesView()
    }

    fun addProgressBar() {
        model.addProgressBarItem()
        view.notifyItemInsert(model.progressBarPosition)
    }
}