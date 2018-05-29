package com.marvelcomics.brito.marvelcomics.application.injection.module.fragment;

import com.marvelcomics.brito.marvelcomics.ui.fragment.comics.ComicsFragment;
import com.marvelcomics.brito.presentation.presenter.comics.ComicsContract;

import dagger.Binds;
import dagger.Module;

@Module
public abstract class ComicViewModule {

    @Binds
    abstract ComicsContract.View providesComicsView(ComicsFragment comicsFragment);
}
