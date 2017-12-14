package com.marvelcomics.brito.domain;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public abstract class UseCase<Parameter, Repository> {

    protected abstract Observable<Repository> createObservable(Parameter data);

    public void execute(Parameter data, Observer<Repository> observer) {
        createObservable(data)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
