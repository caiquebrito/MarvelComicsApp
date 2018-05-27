package com.marvelcomics.brito.marvelcomics.application.injection.module;

import android.content.Context;
import android.net.ConnectivityManager;

import dagger.Module;
import dagger.Provides;

@Module
public class DataSourceModule {

    @Provides
    public ConnectivityManager providesConnectivityManager(Context context) {
        return (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
