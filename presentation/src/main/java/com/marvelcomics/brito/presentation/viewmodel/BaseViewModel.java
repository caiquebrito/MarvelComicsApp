package com.marvelcomics.brito.presentation.viewmodel;

import android.arch.lifecycle.ViewModel;

import com.marvelcomics.brito.infrastructure.di.SchedulersProvider;

import javax.inject.Inject;

public abstract class BaseViewModel extends ViewModel {

    @Inject
    protected SchedulersProvider schedulersProvider;

    public void setSchedulersProvider(SchedulersProvider schedulersProvider) {
        this.schedulersProvider = schedulersProvider;
    }
}
