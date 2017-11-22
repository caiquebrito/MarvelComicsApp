package com.marvelcomics.brito.marvelcomics.application.injection.module.fragment;

import android.arch.lifecycle.ViewModelProviders;

import com.marvelcomics.brito.domain.series.SeriesUseCase;
import com.marvelcomics.brito.infrastructure.di.SchedulersProvider;
import com.marvelcomics.brito.marvelcomics.ui.fragment.series.SeriesFragment;
import com.marvelcomics.brito.presentation.viewmodel.series.SeriesViewModel;

import dagger.Module;
import dagger.Provides;

@Module
public class SeriesModule {

    @Provides
    static SeriesViewModel providesSeriesViewModel(
            SeriesFragment seriesFragment,
            SeriesUseCase seriesUseCase,
            SchedulersProvider provider) {
        SeriesViewModel viewModel = ViewModelProviders.of(seriesFragment).get(SeriesViewModel.class);
        viewModel.setSeriesUseCase(seriesUseCase);
        viewModel.setSchedulersProvider(provider);
        return viewModel;
    }
}
