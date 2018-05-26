package com.marvelcomics.brito.presentation.presenter.series;

import com.marvelcomics.brito.entity.SeriesEntity;

import java.util.List;

public interface SeriesContract {

    interface View {
        void showSeries(List<SeriesEntity> seriesEntities);
        void showError(String message);
    }

    interface Presenter {
        void loadSeries(int characterId);
    }
}
