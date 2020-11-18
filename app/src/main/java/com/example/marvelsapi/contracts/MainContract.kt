package com.example.marvelsapi.contracts

import com.example.marvelsapi.data.Hero

interface MainContract {

    interface MainView {
        fun updateHeroesView()
        fun notifyItemInsert(position: Int)
        fun setLoaded()
    }

    interface ILoadMore {
        fun onLoadMore()
    }

    interface Network {
        fun onError()
        fun onSuccess(list: List<Hero?>)
    }
}