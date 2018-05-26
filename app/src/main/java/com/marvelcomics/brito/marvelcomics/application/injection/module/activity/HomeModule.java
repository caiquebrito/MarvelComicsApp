package com.marvelcomics.brito.marvelcomics.application.injection.module.activity;

import android.arch.lifecycle.ViewModelProviders;

import com.marvelcomics.brito.domain.character.CharactersUseCase;
import com.marvelcomics.brito.infrastructure.di.SchedulersProvider;
import com.marvelcomics.brito.presentation.presenter.home.HomePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    @Provides
    static HomePresenter providesHomePresenter(CharactersUseCase charactersUseCase,
                                               SchedulersProvider provider) {
        HomePresenter presenter = new HomePresenter(charactersUseCase);
        presenter.setSchedulersProvider(provider);
        return presenter;
    }
}
