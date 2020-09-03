package com.marvelcomics.brito.data.datasource.remote.mapper

import com.marvelcomics.brito.data.datasource.remote.response.ThumbnailResponse
import com.marvelcomics.brito.domain.entity.ThumbnailEntity

class ThumbnailMapper {
    fun transform(thumbnailResponse: ThumbnailResponse?): ThumbnailEntity? {
        thumbnailResponse?.let {
            return ThumbnailEntity(thumbnailResponse.path, thumbnailResponse.extension)
        }
        return null
    }
}