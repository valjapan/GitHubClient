package com.valkyrie.nabeshimamac.githubclient

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by NabeshimaMAC on 2017/01/08.
 */
object GithubClient {

    private val _endPoint = "https://api.github.com"
    var service: GithubAPI

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val okClient = OkHttpClient.Builder().addInterceptor(interceptor).build()
        val builder = Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(_endPoint)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okClient)
                .build()
        service = builder.create(GithubAPI::class.java)
    }
}