package com.example.marvelsapi.data.network

import com.example.marvelsapi.data.model.Hero
import com.example.marvelsapi.data.model.MarvelCharacters
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("/v1/public/characters")
    fun getCharacter(@Query("offset") offset: Int): Call<MarvelCharacters>

    @GET("/v1/public/characters/{id}")
    fun getCharacterInfo(@Path("id") heroId: Int): Call<List<Hero>>
}