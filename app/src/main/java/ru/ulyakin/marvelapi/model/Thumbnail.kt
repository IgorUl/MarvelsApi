package ru.ulyakin.marvelapi.model

import ru.ulyakin.marvelapi.common.ApiConstants.Companion.IMAGE_RATIO

data class Thumbnail(
    val path: String,
    val extension: String
) {
    fun getImageUrl(): String = path + IMAGE_RATIO + extension
}