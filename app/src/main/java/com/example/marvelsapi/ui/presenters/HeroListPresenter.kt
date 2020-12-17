package com.example.marvelsapi.ui.presenters

import com.example.marvelsapi.ui.contracts.MainContract
import com.example.marvelsapi.data.model.Hero
import com.example.marvelsapi.data.model.Model

class HeroListPresenter(private val model: Model, private val view: MainContract.MainView) {

    fun getHeroesList(): List<Hero?> =
        model.getHeroesList

    var isLoading: Boolean = false
    private var loadMore: MainContract.ILoadMore? = null

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


    fun setLoadMore(loadMore: MainContract.ILoadMore) {
        this.loadMore = loadMore
    }

    fun setLoaded() {
        isLoading = false
    }

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

    fun loadMore() {
        loadMore?.onLoadMore()
        isLoading = true
    }
}