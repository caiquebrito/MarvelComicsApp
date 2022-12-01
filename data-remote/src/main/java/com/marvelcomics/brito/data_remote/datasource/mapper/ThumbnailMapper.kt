package com.marvelcomics.brito.data_remote.datasource.mapper

import com.marvelcomics.brito.data_remote.datasource.response.ThumbnailResponse
import com.marvelcomics.brito.entity.ThumbnailEntity

class ThumbnailMapper {
    fun transform(thumbnailResponse: ThumbnailResponse?): ThumbnailEntity? {
        thumbnailResponse?.let {
            return ThumbnailEntity(thumbnailResponse.path, thumbnailResponse.extension)
        }
        return null
    }
}
