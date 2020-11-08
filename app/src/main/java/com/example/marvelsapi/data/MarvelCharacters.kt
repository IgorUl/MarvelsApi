package com.example.marvelsapi.data

import com.google.gson.annotations.SerializedName

data class MarvelCharacters(@SerializedName("code") val code: Int,
                            @SerializedName("data") val data: MarvelCharactersData)