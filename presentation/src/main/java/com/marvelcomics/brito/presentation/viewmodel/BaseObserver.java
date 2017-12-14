package com.marvelcomics.brito.presentation.viewmodel;

import android.arch.lifecycle.MutableLiveData;

import com.marvelcomics.brito.presentation.ResourceModel;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseObserver<ReturnType> implements Observer<ReturnType> {

    protected CompositeDisposable disposables = new CompositeDisposable();
    private MutableLiveData<ResourceModel<ReturnType>> mutableLiveData;

    public BaseObserver(MutableLiveData<ResourceModel<ReturnType>> mutableLiveData) {
        this.mutableLiveData = mutableLiveData;
    }

    @Override
    public void onSubscribe(Disposable d) {
        disposables.add(d);
    }

    @Override
    public void onNext(ReturnType model) {
        mutableLiveData.postValue(new ResourceModel<>(
                ResourceModel.State.SUCCESS,
                model,
                null
        ));
    }

    @Override
    public void onError(Throwable e) {
        mutableLiveData.postValue(new ResourceModel<>(
                ResourceModel.State.ERROR,
                null,
                e.getMessage()
        ));
    }

    @Override
    public void onComplete() {
        disposables.clear();
    }
}