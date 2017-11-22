package com.marvelcomics.brito.marvelcomics.application;

import android.app.Activity;
import android.app.Application;

import com.marvelcomics.brito.marvelcomics.application.injection.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MarvelApplication extends Application implements HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> injectorActivity;
    @Inject
    DispatchingAndroidInjector<android.support.v4.app.Fragment> injectorFragment;

    @Override
    public void onCreate() {
        super.onCreate();
        buildTopLevelDependenciesGraph();
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return injectorActivity;
    }

    @Override
    public AndroidInjector<android.support.v4.app.Fragment> supportFragmentInjector() {
        return injectorFragment;
    }

    protected void buildTopLevelDependenciesGraph() {
        DaggerAppComponent.builder().application(this).build().inject(this);
    }
}
