package com.marvelcomics.brito.marvelcomics.application.injection.module;

import com.marvelcomics.brito.infrastructure.di.DefaultSchedulersProvider;
import com.marvelcomics.brito.infrastructure.di.SchedulersProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class InfrastructureModule {

    @Provides
    @Singleton
    static SchedulersProvider providesSchedulerProvider() {
        return new DefaultSchedulersProvider();
    }
}
