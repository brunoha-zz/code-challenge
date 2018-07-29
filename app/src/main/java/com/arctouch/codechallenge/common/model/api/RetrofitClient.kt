package com.arctouch.codechallenge.common.model.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class RetrofitClient private constructor() {



    companion object {
        val apiInstance : TmdbApi = Retrofit.Builder()
                .baseUrl(TmdbApi.URL)
                .client(OkHttpClient
                        .Builder()
                        .addInterceptor(HttpLoggingInterceptor()
                                .getSimpleLogging())
                        .build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(TmdbApi::class.java)
    }


}


fun HttpLoggingInterceptor.getSimpleLogging(): HttpLoggingInterceptor {
    val logging = this
    logging.level = HttpLoggingInterceptor.Level.BODY
    return logging

}
