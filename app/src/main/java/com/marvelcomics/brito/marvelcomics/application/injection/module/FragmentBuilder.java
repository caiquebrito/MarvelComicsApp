package com.marvelcomics.brito.marvelcomics.application.injection.module;

import com.marvelcomics.brito.marvelcomics.application.injection.module.fragment.ComicModule;
import com.marvelcomics.brito.marvelcomics.application.injection.module.fragment.SeriesModule;
import com.marvelcomics.brito.marvelcomics.ui.fragment.series.SeriesFragment;
import com.marvelcomics.brito.marvelcomics.ui.fragment.comics.ComicsFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilder {

    @ContributesAndroidInjector(modules = {ComicModule.class})
    abstract ComicsFragment provideComicFragment();

    @ContributesAndroidInjector(modules = {SeriesModule.class})
    abstract SeriesFragment provideSeriesFragment();
}
