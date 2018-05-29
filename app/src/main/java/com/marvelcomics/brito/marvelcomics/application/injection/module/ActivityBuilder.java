package com.marvelcomics.brito.marvelcomics.application.injection.module;

import com.marvelcomics.brito.marvelcomics.application.injection.module.activity.HomeModule;
import com.marvelcomics.brito.marvelcomics.application.injection.module.activity.HomeViewModule;
import com.marvelcomics.brito.marvelcomics.ui.activity.home.HomeActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = {HomeViewModule.class, HomeModule.class})
    abstract HomeActivity provideHomeActivity();
}
