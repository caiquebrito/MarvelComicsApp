package com.marvelcomics.brito.data.repository.series;

import com.marvelcomics.brito.data.datasource.remote.MarvelWebService;
import com.marvelcomics.brito.data.mapper.SeriesEntityMapper;
import com.marvelcomics.brito.entity.SeriesEntity;

import java.util.List;

import io.reactivex.Observable;

public class RemoteSeriesRepository implements SeriesRepository {

    private MarvelWebService marvelWebService;

    public RemoteSeriesRepository(MarvelWebService marvelWebService) {
        this.marvelWebService = marvelWebService;
    }

    @Override
    public Observable<List<SeriesEntity>> series(int characterId) {
        return marvelWebService.series(characterId)
                .map(SeriesEntityMapper::transform)
                .toObservable();
    }
}
