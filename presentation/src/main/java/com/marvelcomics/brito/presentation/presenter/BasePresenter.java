package com.marvelcomics.brito.presentation.presenter;

import com.marvelcomics.brito.infrastructure.di.SchedulersProvider;

import javax.inject.Inject;

public class BasePresenter {

    @Inject
    protected SchedulersProvider schedulersProvider;

    public void setSchedulersProvider(SchedulersProvider schedulersProvider) {
        this.schedulersProvider = schedulersProvider;
    }
}
