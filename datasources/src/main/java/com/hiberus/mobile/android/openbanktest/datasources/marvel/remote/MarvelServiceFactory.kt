package com.hiberus.mobile.android.openbanktest.datasources.marvel.remote

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hiberus.mobile.android.openbanktest.datasources.remote.API_PRIVATE_KEY
import com.hiberus.mobile.android.openbanktest.datasources.remote.API_PUBLIC_KEY
import com.hiberus.mobile.android.openbanktest.datasources.remote.BASE_URL
import com.hiberus.mobile.android.openbanktest.datasources.remote.authentication.AccessTokenAuthenticator
import com.hiberus.mobile.android.openbanktest.datasources.remote.authentication.AccessTokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.util.concurrent.TimeUnit

/**
 * Provide "make" methods to create instances of [MarvelService]
 * and its related dependencies, such as OkHttpClient, Gson, etc.
 */
object MarvelServiceFactory {

    fun makeService(): MarvelService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(makeGson()))
            .client(makeOkHttpClient()).build()

        return retrofit.create(MarvelService::class.java)
    }

    private fun makeOkHttpClient(): OkHttpClient {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    private fun makeGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
    }

    fun getHash(ts: String): String {
        return md5(ts + API_PRIVATE_KEY + API_PUBLIC_KEY)
    }

    private fun md5(input: String): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

}