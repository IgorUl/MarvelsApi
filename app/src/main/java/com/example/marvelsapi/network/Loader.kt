package com.example.marvelsapi.network

import android.util.Log
import com.example.marvelsapi.ApiConstants
import com.example.marvelsapi.contracts.MainContract
import com.example.marvelsapi.data.Hero
import com.example.marvelsapi.data.MarvelCharacters
import com.example.marvelsapi.data.Model
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Loader {

    var heroesList: MutableList<Hero?> = mutableListOf()

    fun loadHeroesList(network: MainContract.Network) {
        heroesList.clear()
        val call: Call<MarvelCharacters> = MarvelApiService().getAllHeroes(ApiConstants.offset)
        call.enqueue(object : Callback<MarvelCharacters> {
            override fun onFailure(call: Call<MarvelCharacters>?, t: Throwable?) {
                Log.i("FAIL", t.toString())
            }
            override fun onResponse(
                call: Call<MarvelCharacters>?,
                response: Response<MarvelCharacters>?
            ) {

                response?.let {
                    val heroes = it.body()

                    if (heroes != null && heroes.data.result.isNotEmpty()) {
                        heroesList.addAll(heroes.data.result)
                        ApiConstants.offset += ApiConstants.limit
                        network.onSuccess(heroesList)
                        Log.i("RESPONSE", "$heroesList")
                    }
                }
            }

        })
    }
}