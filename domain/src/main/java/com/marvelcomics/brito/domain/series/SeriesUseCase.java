package com.marvelcomics.brito.domain.series;

import com.marvelcomics.brito.data.mapper.SeriesEntityMapper;
import com.marvelcomics.brito.data.repository.series.SeriesRepository;
import com.marvelcomics.brito.domain.UseCase;
import com.marvelcomics.brito.entity.SeriesEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SeriesUseCase extends UseCase<Integer, List<SeriesEntity>> {

    private SeriesRepository seriesRepository;

    @Inject
    protected SeriesEntityMapper seriesEntityMapper;

    @Inject
    public SeriesUseCase(SeriesRepository seriesRepository) {
        this.seriesRepository = seriesRepository;
    }

    @Override
    protected Observable<List<SeriesEntity>> createObservable(Integer data) {
        return seriesRepository.series(data).map(seriesEntityMapper::transform);
    }
}
