package ru.ulyakin.marvelapi.model

data class Hero(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)