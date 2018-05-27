package com.marvelcomics.brito.data.repository.series;

import com.marvelcomics.brito.data.datasource.remote.MarvelWebService;
import com.marvelcomics.brito.data.datasource.remote.response.SeriesResponse;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer;

import io.reactivex.Observable;

public class RemoteSeriesRepository implements SeriesRepository {

    private MarvelWebService marvelWebService;

    public RemoteSeriesRepository(MarvelWebService marvelWebService) {
        this.marvelWebService = marvelWebService;
    }

    @Override
    public Observable<RemoteMarvelContainer<SeriesResponse>> series(int characterId) {
        return marvelWebService.series(characterId).toObservable();
    }
}
