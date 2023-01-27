package org.akozlenko.gitstars.di

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.akozlenko.gitstars.api.services.GithubApi
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

private const val BASE_URL = "https://api.github.com/"

private const val CONNECT_TIMEOUT_IN_SEC = 20L
private const val READ_TIMEOUT_IN_SEC = 30L
private const val WRITE_TIMEOUT_IN_SEC = 30L

val apiModule = module {

    single {
        initRetrofit()
    }

    single<GithubApi> {
        val retrofit: Retrofit = get()
        retrofit.create(GithubApi::class.java)
    }
}

private fun initRetrofit(): Retrofit {
    //Prepare client
    val httpClient = initHttpClient()
    // Prepare Gson
    val gsonConverterFactory = initGsonConverterFactory()
    // Prepare builder
    val builder = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        client(httpClient)
        addConverterFactory(gsonConverterFactory)
    }
    return builder.build()
}

private fun initGsonConverterFactory(): GsonConverterFactory {
    val gson = GsonBuilder().create()
    return GsonConverterFactory.create(gson)
}

private fun initHttpClient(): OkHttpClient {
    val okHttpBuilder = OkHttpClient.Builder()
    // Timeouts
    okHttpBuilder.connectTimeout(CONNECT_TIMEOUT_IN_SEC, TimeUnit.SECONDS)
        .readTimeout(READ_TIMEOUT_IN_SEC, TimeUnit.SECONDS)
        .writeTimeout(WRITE_TIMEOUT_IN_SEC, TimeUnit.SECONDS)

    return okHttpBuilder.build()
}
