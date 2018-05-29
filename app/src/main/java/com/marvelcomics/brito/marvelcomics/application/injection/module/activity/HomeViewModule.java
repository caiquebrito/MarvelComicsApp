package com.marvelcomics.brito.marvelcomics.application.injection.module.activity;

import com.marvelcomics.brito.marvelcomics.ui.activity.home.HomeActivity;
import com.marvelcomics.brito.presentation.presenter.home.HomeContract;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class HomeViewModule {

    @Binds
    abstract HomeContract.View provideHomeView(HomeActivity homeActivity);
}
