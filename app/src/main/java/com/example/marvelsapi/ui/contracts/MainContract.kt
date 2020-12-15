package com.example.marvelsapi.ui.contracts

import com.example.marvelsapi.data.model.Hero

interface MainContract {

    interface MainView {
        fun updateHeroesView()
        fun notifyItemInsert(position: Int)
        fun setLoaded()
        fun showRefreshSnackbar()
    }

    interface ILoadMore {
        fun onLoadMore()
    }

    interface Network {
        fun onError()
        fun onSuccess(list: List<Hero?>)
    }
}