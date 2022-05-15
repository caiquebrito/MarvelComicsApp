package com.marvelcomics.brito.view.mapper

import com.marvelcomics.brito.domain.models.ThumbnailDomain
import com.marvelcomics.brito.view.models.ThumbnailEntity

class ThumbnailViewMapper : Mapper<ThumbnailEntity, ThumbnailDomain> {

    override fun toDomain(entity: ThumbnailEntity): ThumbnailDomain {
        return ThumbnailDomain(path = entity.path, extension = entity.extension)
    }

    override fun fromDomain(domain: ThumbnailDomain): ThumbnailEntity {
        return ThumbnailEntity(path = domain.path, extension = domain.extension)
    }
}
