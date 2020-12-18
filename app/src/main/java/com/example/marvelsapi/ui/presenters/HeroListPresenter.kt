package com.example.marvelsapi.ui.presenters

import com.example.marvelsapi.ui.contracts.MainContract
import com.example.marvelsapi.data.model.Hero
import com.example.marvelsapi.data.model.Model

class HeroListPresenter(private val model: Model, private val view: MainContract.MainView) {

    fun getHeroesList(): List<Hero?> =
        model.getHeroesList

    fun loadHeroes() = model.loadHeroList(object : MainContract.Network {
        override fun onSuccess(list: List<Hero?>) {
            if (model.getHeroesList.isNotEmpty() &&
                isProgressBar(model.progressBarPosition)
            ) {
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

    fun isProgressBar(position: Int) =
        model.getHeroesList[position] == model.getProgressBar

    fun setLoaded() {
        model.isItemsLoading = false
    }

    fun getLoadState() = model.isItemsLoading

    fun onCreate() {
        if(model.getHeroesList.isNotEmpty()) {
            val lastItemID = model.getHeroesList.last()?.id
            if (lastItemID == -1) {
                model.removeProgressBar()
            }
            view.updateHeroesView()
        } else {
            loadHeroes()
        }
    }

    private fun addProgressBar() {
        model.addProgressBarItem()
        view.notifyItemInsert(model.progressBarPosition)
    }

    fun onListEndReached() {
        addProgressBar()
        loadHeroes()
//        setLoaded()
        model.isItemsLoading = true
    }
}