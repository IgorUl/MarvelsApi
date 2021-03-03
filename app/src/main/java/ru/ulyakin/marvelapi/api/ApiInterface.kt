package ru.ulyakin.marvelapi.api

import ru.ulyakin.marvelapi.model.Hero
import ru.ulyakin.marvelapi.model.MarvelCharacters
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @GET("/v1/public/characters")
    suspend fun getCharacter(@Query("offset") offset: Int? = 0): MarvelCharacters

    @GET("/v1/public/characters/{id}")
    suspend fun getCharacterInfo(@Path("id") heroId: Int): Hero
}