package com.marvelcomics.brito.marvelcomics.application.injection.module.fragment;

import com.marvelcomics.brito.domain.series.SeriesUseCase;
import com.marvelcomics.brito.infrastructure.di.ResourceProvider;
import com.marvelcomics.brito.infrastructure.di.SchedulersProvider;
import com.marvelcomics.brito.presentation.presenter.series.SeriesContract;
import com.marvelcomics.brito.presentation.presenter.series.SeriesPresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class SeriesModule {

    @Provides
    static SeriesPresenter providesSeriesPresenter(SeriesContract.View view,
                                                   SchedulersProvider schedulersProvider,
                                                   SeriesUseCase seriesUseCase,
                                                   ResourceProvider resourceProvider) {
        return new SeriesPresenter(view, schedulersProvider, seriesUseCase, resourceProvider);
    }
}
