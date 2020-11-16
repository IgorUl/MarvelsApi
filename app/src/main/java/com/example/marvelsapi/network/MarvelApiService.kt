package com.example.marvelsapi.network

import com.example.marvelsapi.ApiConstants
import com.example.marvelsapi.data.MarvelCharacters
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class MarvelApiService {

    private val timeStamp: String = System.currentTimeMillis().toString()

    private fun createHttpClient(): OkHttpClient {
        //todo cache maybe?
        val httpClient = OkHttpClient.Builder()
        httpClient.addInterceptor { chain ->
            val original: Request = chain.request()
            val originalHttpUrl: HttpUrl = original.url()
            val url = originalHttpUrl.newBuilder()
                .addQueryParameter("ts", timeStamp)
                .addQueryParameter("apikey", ApiConstants.PUBLIC_KEY)
                .addQueryParameter(
                    "hash",
                    createHash(
                        timeStamp + ApiConstants.PRIVATE_KEY + ApiConstants.PUBLIC_KEY
                    )
                )
//                .addQueryParameter("offset", "$offset")
                .addQueryParameter("limit", "${ApiConstants.limit}")
                .build()
            val requestBuilder: Request.Builder = original.newBuilder()
                .url(url)
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
        return httpClient.build()
    }

    private fun createHash(stringToHash: String): String {
        val md5 = "MD5"

        try {
            val digest: MessageDigest = MessageDigest.getInstance(md5)
            digest.update(stringToHash.toByteArray())
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


    private fun init(): ApiInterface {

        val gson: Gson = GsonBuilder().setLenient().create()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(createHttpClient())
            .build()

        return retrofit.create(ApiInterface::class.java)
    }

    fun getAllHeroes(offset: Int): Call<MarvelCharacters> {
        return init().getCharacter(offset)
    }
}