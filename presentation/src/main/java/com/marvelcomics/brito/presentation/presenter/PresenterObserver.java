package com.marvelcomics.brito.presentation.presenter;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class PresenterObserver<ReturnType> implements Observer<ReturnType> {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public abstract void onNextObserver(ReturnType returnType);
    public abstract void onErrorObserver(Throwable throwable);

    @Override
    public void onSubscribe(Disposable disposable) {
        this.compositeDisposable.add(disposable);
    }

    @Override
    public void onNext(ReturnType returnType) {
        onNextObserver(returnType);
    }

    @Override
    public void onError(Throwable throwable) {
        onErrorObserver(throwable);
    }

    @Override
    public void onComplete() {
        compositeDisposable.clear();
    }
}
