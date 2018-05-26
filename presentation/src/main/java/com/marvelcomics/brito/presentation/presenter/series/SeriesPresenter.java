package com.marvelcomics.brito.presentation.presenter.series;

import com.marvelcomics.brito.domain.series.SeriesUseCase;
import com.marvelcomics.brito.entity.SeriesEntity;
import com.marvelcomics.brito.presentation.presenter.BasePresenter;
import com.marvelcomics.brito.presentation.presenter.PresenterObserver;

import java.util.List;

public class SeriesPresenter extends BasePresenter implements SeriesContract.Presenter {

    private SeriesContract.View view;
    private SeriesUseCase seriesUseCase;

    @Override
    public void loadSeries(int characterId) {
        seriesUseCase.execute(characterId, new PresenterObserver<List<SeriesEntity>>() {
            @Override
            public void onNextObserver(List<SeriesEntity> seriesEntities) {
                if (!seriesEntities.isEmpty()) {
                    view.showSeries(seriesEntities);
                } else {
                    onErrorObserver(new Exception("Series not loaded."));
                }
            }

            @Override
            public void onErrorObserver(Throwable throwable) {
                view.showError(throwable.getMessage());
            }
        });
    }

    public void setSeriesUseCase(SeriesUseCase seriesUseCase) {
        this.seriesUseCase = seriesUseCase;
    }

    public void setView(SeriesContract.View view) {
        this.view = view;
    }
}
