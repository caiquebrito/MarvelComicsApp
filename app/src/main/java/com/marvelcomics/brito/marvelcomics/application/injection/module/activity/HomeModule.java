package com.marvelcomics.brito.marvelcomics.application.injection.module.activity;

import com.marvelcomics.brito.domain.character.CharactersUseCase;
import com.marvelcomics.brito.infrastructure.di.ResourceProvider;
import com.marvelcomics.brito.infrastructure.di.SchedulersProvider;
import com.marvelcomics.brito.presentation.presenter.home.HomeContract;
import com.marvelcomics.brito.presentation.presenter.home.HomePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    @Provides
    static HomePresenter providesHomePresenter(HomeContract.View view,
                                               CharactersUseCase charactersUseCase,
                                               SchedulersProvider schedulersProvider,
                                               ResourceProvider resourceProvider) {
        return new HomePresenter(view, schedulersProvider, charactersUseCase, resourceProvider);
    }
}
