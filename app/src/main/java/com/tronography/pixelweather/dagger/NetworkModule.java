package com.tronography.pixelweather.dagger;

import com.tronography.pixelweather.http.OpenWeatherApi;
import com.tronography.pixelweather.http.OpenWeatherClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class NetworkModule {

    @Provides
    @Singleton
    OkHttpClient provideOkHttpeClient() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @Singleton
    public OpenWeatherClient provideOpenWeatherClient(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(OpenWeatherApi.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
                .create(OpenWeatherClient.class);
    }
}
