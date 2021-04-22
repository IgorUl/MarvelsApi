package ru.ulyakin.marvelapi.data.model

data class Hero(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail,
    val comics: Comics
) {
    // TODO Thumbnail
}