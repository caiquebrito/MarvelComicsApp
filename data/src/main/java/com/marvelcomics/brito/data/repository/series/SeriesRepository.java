package com.marvelcomics.brito.data.repository.series;

import com.marvelcomics.brito.data.datasource.remote.response.SeriesResponse;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer;

import io.reactivex.Observable;

public interface SeriesRepository {

    Observable<RemoteMarvelContainer<SeriesResponse>> series(int characterId);
}
