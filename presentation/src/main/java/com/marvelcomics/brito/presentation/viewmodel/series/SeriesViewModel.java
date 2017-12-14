package com.marvelcomics.brito.presentation.viewmodel.series;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.marvelcomics.brito.domain.series.SeriesUseCase;
import com.marvelcomics.brito.entity.SeriesEntity;
import com.marvelcomics.brito.presentation.ResourceModel;
import com.marvelcomics.brito.presentation.viewmodel.BaseObserver;
import com.marvelcomics.brito.presentation.viewmodel.BaseViewModel;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class SeriesViewModel extends BaseViewModel {

    private MutableLiveData<ResourceModel<List<SeriesEntity>>> series;

    private SeriesUseCase seriesUseCase;

    public void setSeriesUseCase(SeriesUseCase seriesUseCase) {
        this.seriesUseCase = seriesUseCase;
    }

    public LiveData<ResourceModel<List<SeriesEntity>>> loadSeries(int characterId) {
        if (series == null) {
            series = new MutableLiveData<>();
            series.postValue(new ResourceModel<>(ResourceModel.State.LOADING, null, null));
            seriesUseCase.execute(
                    characterId,
                    new BaseObserver<>(series));
        }
        return series;
    }
}
