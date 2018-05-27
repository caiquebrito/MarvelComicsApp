package com.marvelcomics.brito.data.mapper;

import com.marvelcomics.brito.data.datasource.remote.response.SeriesResponse;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer;
import com.marvelcomics.brito.entity.SeriesEntity;
import com.marvelcomics.brito.infrastructure.exception.MarvelApiException;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class SeriesEntityMapper {

    @Inject
    protected ThumbnailEntityMapper thumbnailEntityMapper;

    @Inject
    public SeriesEntityMapper() {
    }

    public List<SeriesEntity> transform(RemoteMarvelContainer<SeriesResponse> remoteMarvelData) throws MarvelApiException {
        try {
            List<SeriesEntity> seriesEntityList = new ArrayList<>();
            List<SeriesResponse> results = remoteMarvelData.getRemoteMarvelData().getResults();
            for (SeriesResponse seriesResponse : results) {
                SeriesEntity seriesEntity = new SeriesEntity(
                        seriesResponse.getId(),
                        seriesResponse.getTitle(),
                        seriesResponse.getDescription(),
                        thumbnailEntityMapper.transform(seriesResponse.getThumbnailResponse())
                );
                seriesEntityList.add(seriesEntity);
            }
            return seriesEntityList;
        } catch (NullPointerException e) {
            throw new MarvelApiException("Result from server return nulls", e);
        }
    }
}
