package com.marvelcomics.brito.marvelcomics.application.injection.module.fragment;

import com.marvelcomics.brito.marvelcomics.ui.fragment.series.SeriesFragment;
import com.marvelcomics.brito.presentation.presenter.series.SeriesContract;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class SeriesViewModule {

    @Binds
    abstract SeriesContract.View provideSeriesView(SeriesFragment seriesFragment);
}
