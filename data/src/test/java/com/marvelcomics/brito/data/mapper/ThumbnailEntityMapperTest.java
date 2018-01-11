package com.marvelcomics.brito.data.mapper;

import com.marvelcomics.brito.data.datasource.remote.response.ThumbnailResponse;
import com.marvelcomics.brito.entity.ThumbnailEntity;

public class ThumbnailEntityMapperTest {

    public static ThumbnailEntity transform(ThumbnailResponse thumbnailResponse) {
        return new ThumbnailEntity(thumbnailResponse.getPath(), thumbnailResponse.getExtension());
    }
}
