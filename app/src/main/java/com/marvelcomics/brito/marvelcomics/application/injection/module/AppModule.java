package com.marvelcomics.brito.marvelcomics.application.injection.module;

import android.app.Application;
import android.content.Context;

import com.marvelcomics.brito.infrastructure.di.ResourceProvider;
import com.marvelcomics.brito.marvelcomics.resources.ResourceProviderImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    @Provides
    @Singleton
    static Context provideContext(Application application) {
        return application;
    }

    @Provides
    ResourceProvider providesResourceProvider(ResourceProviderImpl resourceProvider) {
        return resourceProvider;
    }
}
