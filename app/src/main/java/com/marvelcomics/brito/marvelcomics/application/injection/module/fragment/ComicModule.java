package com.marvelcomics.brito.marvelcomics.application.injection.module.fragment;

import com.marvelcomics.brito.domain.comic.ComicsUseCase;
import com.marvelcomics.brito.infrastructure.di.ResourceProvider;
import com.marvelcomics.brito.infrastructure.di.SchedulersProvider;
import com.marvelcomics.brito.presentation.presenter.comics.ComicsContract;
import com.marvelcomics.brito.presentation.presenter.comics.ComicsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ComicModule {

    @Provides
    static ComicsPresenter providesComicsPresenter(ComicsContract.View view,
                                                   SchedulersProvider schedulersProvider,
                                                   ComicsUseCase comicsUseCase,
                                                   ResourceProvider resourceProvider) {
        return new ComicsPresenter(view, schedulersProvider, comicsUseCase, resourceProvider);
    }
}
