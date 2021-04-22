package ru.ulyakin.marvelapi.data.model

data class Comics(
    val items: List<Comic>
)

class Comic(
    val id: Int,
    val title: String,
    val thumbnail: Thumbnail,

    )