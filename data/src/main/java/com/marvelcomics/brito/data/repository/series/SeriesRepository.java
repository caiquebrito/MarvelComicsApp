package com.marvelcomics.brito.data.repository.series;

import com.marvelcomics.brito.entity.SeriesEntity;

import java.util.List;

import io.reactivex.Observable;

public interface SeriesRepository {

    Observable<List<SeriesEntity>> series(int characterId);
}
