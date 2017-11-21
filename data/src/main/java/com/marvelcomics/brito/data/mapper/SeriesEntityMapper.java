package com.marvelcomics.brito.data.mapper;

import com.marvelcomics.brito.data.datasource.remote.response.SeriesResponse;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer;
import com.marvelcomics.brito.entity.SeriesEntity;
import com.marvelcomics.brito.infrastructure.exception.MarvelApiException;

import java.util.ArrayList;
import java.util.List;

public class SeriesEntityMapper {

    public static List<SeriesEntity> transform(RemoteMarvelContainer<SeriesResponse> remoteMarvelData)
            throws MarvelApiException {

        try {
            List<SeriesEntity> seriesEntityList = new ArrayList<>();
            for (SeriesResponse seriesResponse : remoteMarvelData.getRemoteMarvelData().getResults()) {
                SeriesEntity seriesEntity = new SeriesEntity(
                        seriesResponse.getId(),
                        seriesResponse.getTitle(),
                        seriesResponse.getDescription(),
                        ThumbnailEntityMapper.transform(seriesResponse.getThumbnailResponse())
                );
                seriesEntityList.add(seriesEntity);
            }
            return seriesEntityList;
        } catch (NullPointerException e) {
            throw new MarvelApiException("Result from server return nulls", e);
        }
    }
}
