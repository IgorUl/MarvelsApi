package ru.ulyakin.marvelapi.data.api

import ru.ulyakin.marvelapi.common.ApiConstants.Companion.limit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.ulyakin.marvelapi.common.ApiConstants.Companion.PROP_PRIVATE_KEY
import ru.ulyakin.marvelapi.common.ApiConstants.Companion.PROP_API_KEY
import ru.ulyakin.marvelapi.common.ApiConstants.Companion.PROP_HASH
import ru.ulyakin.marvelapi.common.ApiConstants.Companion.PROP_LIMIT
import ru.ulyakin.marvelapi.common.ApiConstants.Companion.PROP_SSL
import ru.ulyakin.marvelapi.common.ApiConstants.Companion.PROP_TIME_STAMP
import ru.ulyakin.marvelapi.common.ApiConstants.Companion.PROP_PUBLIC_KEY
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateException
import ru.ulyakin.marvelapi.common.md5
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
            .addQueryParameter(PROP_HASH, stringToHash.md5())
            .addQueryParameter(PROP_LIMIT, "$limit")
            .build()
        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)
        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }

    fun create(baseUrl: String): ApiInterface {
        val responseGson: Gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(responseGson))
            .client(unsafeClient)
            .build().create(ApiInterface::class.java)
    }

    private val unsafeClient: OkHttpClient = getUnsafeOkHttpClient().apply {
        this.addInterceptor(interceptor)
        this.addInterceptor { chain -> createDefaultParameters(chain) }
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
}