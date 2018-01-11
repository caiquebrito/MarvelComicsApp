package com.marvelcomics.brito.data.mapper;

import com.marvelcomics.brito.data.datasource.remote.response.ComicResponse;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer;
import com.marvelcomics.brito.entity.ComicEntity;
import com.marvelcomics.brito.infrastructure.exception.MarvelApiException;

import java.util.ArrayList;
import java.util.List;

public class ComicEntityMapperTest {

    public static List<ComicEntity> transform(RemoteMarvelContainer<ComicResponse> remoteMarvelData)
            throws MarvelApiException {

        try {
            List<ComicEntity> comicEntityList = new ArrayList<>();
            for (ComicResponse comicResponse : remoteMarvelData.getRemoteMarvelData().getResults()) {
                ComicEntity comicEntity = new ComicEntity(
                        comicResponse.getId(),
                        comicResponse.getTitle(),
                        comicResponse.getDescription(),
                        ThumbnailEntityMapperTest.transform(comicResponse.getThumbnailResponse())
                );
                comicEntityList.add(comicEntity);
            }
            return comicEntityList;
        } catch (NullPointerException e) {
            throw new MarvelApiException("Result from server return nulls", e);
        }
    }
}
