package com.marvelcomics.brito.marvelcomics.application.injection.module.fragment;

import android.arch.lifecycle.ViewModelProviders;

import com.marvelcomics.brito.domain.comic.ComicsUseCase;
import com.marvelcomics.brito.infrastructure.di.SchedulersProvider;
import com.marvelcomics.brito.marvelcomics.ui.fragment.comics.ComicsFragment;
import com.marvelcomics.brito.presentation.viewmodel.comics.ComicsViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class ComicModule {

    @Provides
    static ComicsViewModel providesComicsViewModel(
            ComicsFragment comicsFragment,
            ComicsUseCase comicsUseCase,
            SchedulersProvider provider) {
        ComicsViewModel viewModel = ViewModelProviders.of(comicsFragment).get(ComicsViewModel.class);
        viewModel.setComicsUseCase(comicsUseCase);
        viewModel.setSchedulersProvider(provider);
        return viewModel;
    }
}
