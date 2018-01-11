package com.marvelcomics.brito.marvelcomics.application;

import android.app.Activity;
import android.app.Application;
import android.arch.persistence.room.Room;

import com.marvelcomics.brito.data.datasource.database.MarvelComicsDatabase;
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

    public static MarvelComicsDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        buildTopLevelDependenciesGraph();
        instantiateRoom();
    }

    private void instantiateRoom() {
        database = Room.databaseBuilder(
                this,
                MarvelComicsDatabase.class,
                "marvel.db")
                .build();
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
