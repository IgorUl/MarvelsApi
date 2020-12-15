package com.example.marvelsapi.data.model

import com.google.gson.annotations.SerializedName

data class MarvelCharactersData(@SerializedName("results") val result: List<Hero> = listOf())