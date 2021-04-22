package ru.ulyakin.marvelapi.data.model


class Thumbnail(
    private val path: String,
    private val extension: String
) {
    fun getImageUrl(): String = path + PROP_IMAGE_RATIO + extension

    companion object {
        const val PROP_IMAGE_RATIO = "/landscape_xlarge."
    }
}