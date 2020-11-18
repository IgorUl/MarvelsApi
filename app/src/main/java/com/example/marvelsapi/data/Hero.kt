package com.example.marvelsapi.data

import com.google.gson.annotations.SerializedName

data class Hero(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("thumbnail")
    val thumbnail: Thumbnail?
)