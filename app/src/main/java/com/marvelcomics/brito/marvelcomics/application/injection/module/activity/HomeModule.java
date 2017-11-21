package com.marvelcomics.brito.marvelcomics.application.injection.module.activity;

import android.arch.lifecycle.ViewModelProviders;

import com.marvelcomics.brito.domain.character.CharactersUseCase;
import com.marvelcomics.brito.infrastructure.di.SchedulersProvider;
import com.marvelcomics.brito.marvelcomics.ui.activity.HomeActivity;
import com.marvelcomics.brito.presentation.viewmodel.home.HomeViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class HomeModule {

    @Provides
    static HomeViewModel providesLoginViewModel(
            HomeActivity homeActivity,
            CharactersUseCase charactersUseCase,
            SchedulersProvider provider) {
        HomeViewModel viewModel = ViewModelProviders.of(homeActivity).get(HomeViewModel.class);
        viewModel.setCharactersUseCase(charactersUseCase);
        viewModel.setSchedulersProvider(provider);
        return viewModel;
    }
}
