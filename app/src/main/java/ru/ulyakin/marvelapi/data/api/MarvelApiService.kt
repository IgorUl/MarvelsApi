package ru.ulyakin.marvelapi.data.api

import com.squareup.moshi.Moshi
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import kotlin.jvm.Throws

object MarvelApiService {

    private val timeStamp: String = System.currentTimeMillis().toString()

    private val interceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private fun createDefaultParameters(chain: Interceptor.Chain): Response {
        val stringToHash: String = timeStamp + PROP_PRIVATE_KEY + PROP_PUBLIC_KEY
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url
        val url: HttpUrl = originalHttpUrl.newBuilder()
            .addQueryParameter(PROP_TIME_STAMP, timeStamp)
            .addQueryParameter(PROP_API_KEY, PROP_PUBLIC_KEY)
            .addQueryParameter(PROP_HASH, convertStringToMd5(stringToHash))
            .addQueryParameter(PROP_LIMIT, "$limit")
            .build()
        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }

    fun create(baseUrl: String): ApiInterface {
        val responseMoshi: Moshi = Moshi.Builder().build()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(responseMoshi))
            .client(unsafeClient)
            .build()
            .create(ApiInterface::class.java)
    }

    private val unsafeClient: OkHttpClient = getUnsafeOkHttpClient().apply {
        this.addInterceptor(interceptor)
        this.addInterceptor(this@MarvelApiService::createDefaultParameters)
    }.build()


    private fun getUnsafeOkHttpClient(): OkHttpClient.Builder {
        try {
            val trustAllCerts: Array<TrustManager> = arrayOf(object : X509TrustManager {
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ): Unit =
                    Unit

                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                    chain: Array<java.security.cert.X509Certificate>,
                    authType: String
                ): Unit =
                    Unit

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> =
                    arrayOf()
            })

            val sslContext: SSLContext = SSLContext.getInstance(PROP_SSL)
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())
            val sslSocketFactory: SSLSocketFactory = sslContext.socketFactory

            val builder = OkHttpClient.Builder()
            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }

            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    private fun convertStringToMd5(stringToConvert: String): String {
        try {
            val digest: MessageDigest = MessageDigest.getInstance(PROP_MD5)
            digest.update(stringToConvert.toByteArray())
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

    private const val limit = 10
    private const val PROP_PUBLIC_KEY = "5d583fdfbb234ce822fc0b5a4075c6a1"
    private const val PROP_PRIVATE_KEY = "37ebccd0994a76faf8984373d8fecc5bd32ba1b3"
    private const val PROP_SSL = "SSL"
    private const val PROP_TIME_STAMP = "ts"
    private const val PROP_API_KEY = "apikey"
    private const val PROP_HASH = "hash"
    private const val PROP_LIMIT = "limit"
    private const val PROP_MD5 = "MD5"
}