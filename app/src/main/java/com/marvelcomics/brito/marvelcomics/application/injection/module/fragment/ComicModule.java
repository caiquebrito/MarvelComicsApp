package com.marvelcomics.brito.marvelcomics.application.injection.module.fragment;

import com.marvelcomics.brito.domain.comic.ComicsUseCase;
import com.marvelcomics.brito.infrastructure.di.SchedulersProvider;
import com.marvelcomics.brito.presentation.presenter.comics.ComicsPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ComicModule {

    @Provides
    static ComicsPresenter providesComicsPresenter(
            ComicsUseCase comicsUseCase,
            SchedulersProvider provider) {
        ComicsPresenter presenter = new ComicsPresenter();
        presenter.setComicsUseCase(comicsUseCase);
        presenter.setSchedulersProvider(provider);
        return presenter;
    }
}
