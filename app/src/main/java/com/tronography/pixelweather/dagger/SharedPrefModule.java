package com.tronography.pixelweather.dagger;

import android.content.Context;
import android.support.annotation.NonNull;

import com.tronography.pixelweather.utils.SharedPrefsUtils;

import dagger.Module;
import dagger.Provides;


@Module
public class SharedPrefModule {

    @Provides
    SharedPrefsUtils provideSharedPrefUtils(@NonNull Context context) {
        return new SharedPrefsUtils(context);
    }
}
