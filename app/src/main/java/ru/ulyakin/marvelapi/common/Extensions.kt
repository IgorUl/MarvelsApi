package ru.ulyakin.marvelapi.common

import android.widget.ImageView
import com.bumptech.glide.Glide
import ru.ulyakin.marvelapi.common.ApiConstants.Companion.PROP_MD5
import ru.ulyakin.marvelapi.data.mapper.HeroesMapper
import ru.ulyakin.marvelapi.data.model.Hero
import ru.ulyakin.marvelapi.data.model.MarvelCharacters
import ru.ulyakin.marvelsapi.R
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

fun String.md5(): String {

    try {
        val digest: MessageDigest = MessageDigest.getInstance(PROP_MD5)
        digest.update(this.toByteArray())
        val messageDigest: ByteArray = digest.digest()
        val hexString = StringBuilder()

        for (aMessageDigest: Byte in messageDigest) {
            var h = Integer.toHexString(0xFF and aMessageDigest.toInt())
            while (h.length < 2) {
                h = "0$h"
            }
            hexString.append(h)
        }
        return hexString.toString()
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    }
    return ""
}

fun ImageView.load(url: String?) {
    Glide.with(context)
        .load(url)
        .centerCrop()
        .placeholder(R.drawable.marvel)
        .into(this)
}

fun MarvelCharacters.asDomainModel(mapper: HeroesMapper): List<Hero> {
    return data.results.map(mapper::map)
}