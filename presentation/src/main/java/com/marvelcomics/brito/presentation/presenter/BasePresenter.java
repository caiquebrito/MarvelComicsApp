package com.marvelcomics.brito.presentation.presenter;

import com.marvelcomics.brito.infrastructure.di.SchedulersProvider;

public abstract class BasePresenter<View> {

    protected final View view;
    protected SchedulersProvider schedulersProvider;

    protected BasePresenter(View view, SchedulersProvider schedulersProvider) {
        this.view = view;
        this.schedulersProvider = schedulersProvider;
    }

    /**
     * Contains common setup actions needed for all presenters, if any.
     * Subclasses may override this.
     */
    public void start() {
    }

    /**
     * Contains common cleanup actions needed for all presenters, if any.
     * Subclasses may override this.
     */
    public void stop() {
    }
}
