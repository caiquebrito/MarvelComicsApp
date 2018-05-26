package com.marvelcomics.brito.marvelcomics.application.injection.module.fragment;

import com.marvelcomics.brito.domain.series.SeriesUseCase;
import com.marvelcomics.brito.infrastructure.di.SchedulersProvider;
import com.marvelcomics.brito.presentation.presenter.series.SeriesPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SeriesModule {

    @Provides
    static SeriesPresenter providesSeriesPresenter(
            SeriesUseCase seriesUseCase,
            SchedulersProvider provider) {
        SeriesPresenter presenter = new SeriesPresenter();
        presenter.setSeriesUseCase(seriesUseCase);
        presenter.setSchedulersProvider(provider);
        return presenter;
    }
}
