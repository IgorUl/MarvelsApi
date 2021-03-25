package ru.ulyakin.marvelapi.data.model

import ru.ulyakin.marvelapi.common.ApiConstants.Companion.PROP_IMAGE_RATIO

class Thumbnail(
    private val path: String,
    private val extension: String
) {
    fun getImageUrl(): String = path + PROP_IMAGE_RATIO + extension
}