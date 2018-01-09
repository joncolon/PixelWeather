package com.tronography.pixelweather.dagger

import com.tronography.pixelweather.http.OpenWeatherApi
import com.tronography.pixelweather.http.OpenWeatherClient

import javax.inject.Singleton

import dagger.Module
import dagger.Provides

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


@Module
class NetworkModule {

    @Provides
    @Singleton
    internal fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideOpenWeatherClient(client: OkHttpClient): OpenWeatherClient {
        return Retrofit.Builder()
                .baseUrl(OpenWeatherApi.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(OpenWeatherClient::class.java)
    }
}
