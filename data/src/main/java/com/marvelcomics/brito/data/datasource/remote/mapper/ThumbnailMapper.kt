package com.marvelcomics.brito.data.datasource.remote.mapper

import com.marvelcomics.brito.data.datasource.remote.response.ThumbnailResponse
import com.marvelcomics.brito.domain.models.ThumbnailDomain

class ThumbnailMapper {
    fun transform(thumbnailResponse: ThumbnailResponse?): ThumbnailDomain? {
        thumbnailResponse?.let {
            return ThumbnailDomain(thumbnailResponse.path, thumbnailResponse.extension)
        }
        return null
    }
}
