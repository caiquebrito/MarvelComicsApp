package com.marvelcomics.brito.domain;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;

public abstract class UseCase<P,R> {

    protected abstract Observable<R> createObservable(P data);

    public void execute(P data, Scheduler subscriberScheduler, Scheduler observerScheduler, Observer<R> observer) {
        createObservable(data)
                .subscribeOn(subscriberScheduler)
                .observeOn(observerScheduler)
                .subscribe(observer);
    }
}
