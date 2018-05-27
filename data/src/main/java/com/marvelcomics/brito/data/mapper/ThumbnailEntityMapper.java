package com.marvelcomics.brito.data.mapper;

import com.marvelcomics.brito.data.datasource.remote.response.ThumbnailResponse;
import com.marvelcomics.brito.entity.ThumbnailEntity;

import javax.inject.Inject;

public class ThumbnailEntityMapper {

    @Inject
    public ThumbnailEntityMapper() {
    }

    public ThumbnailEntity transform(ThumbnailResponse thumbnailResponse) {
        return new ThumbnailEntity(thumbnailResponse.getPath(), thumbnailResponse.getExtension());
    }
}
