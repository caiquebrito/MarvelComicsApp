package com.marvelcomics.brito.presentation.presenter.series;

import com.marvelcomics.brito.domain.series.SeriesUseCase;
import com.marvelcomics.brito.entity.SeriesEntity;
import com.marvelcomics.brito.infrastructure.di.ResourceProvider;
import com.marvelcomics.brito.infrastructure.di.SchedulersProvider;
import com.marvelcomics.brito.presentation.R;
import com.marvelcomics.brito.presentation.presenter.BasePresenter;
import com.marvelcomics.brito.presentation.presenter.PresenterObserver;

import java.util.List;

public class SeriesPresenter extends BasePresenter<SeriesContract.View> implements SeriesContract.Presenter {

    private SeriesUseCase seriesUseCase;
    private ResourceProvider resourceProvider;

    public SeriesPresenter(SeriesContract.View view,
                              SchedulersProvider schedulersProvider,
                              SeriesUseCase seriesUseCase,
                              ResourceProvider resourceProvider) {
        super(view, schedulersProvider);
        this.seriesUseCase = seriesUseCase;
        this.resourceProvider = resourceProvider;
    }


    @Override
    public void loadSeries(int characterId) {
        seriesUseCase.execute(characterId, new PresenterObserver<List<SeriesEntity>>() {
            @Override
            public void onNextObserver(List<SeriesEntity> seriesEntities) {
                if (!seriesEntities.isEmpty()) {
                    view.showSeries(seriesEntities);
                } else {
                    onErrorObserver(new Exception(resourceProvider.getString(R.string.series_not_found)));
                }
            }

            @Override
            public void onErrorObserver(Throwable throwable) {
                view.showError(throwable.getMessage());
            }
        });
    }
}
