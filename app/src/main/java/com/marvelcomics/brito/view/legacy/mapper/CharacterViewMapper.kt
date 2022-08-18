package com.marvelcomics.brito.view.legacy.mapper

import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.view.legacy.models.CharacterEntity

class CharacterViewMapper : Mapper<CharacterEntity, CharacterDomain> {

    override fun toDomain(entity: CharacterEntity): CharacterDomain {
        return CharacterDomain(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            thumbnailDomain = entity.thumbnailEntity?.let {
                ThumbnailViewMapper().toDomain(entity = it)
            }
        )
    }

    override fun fromDomain(domain: CharacterDomain): CharacterEntity {
        return CharacterEntity(
            id = domain.id,
            name = domain.name,
            description = domain.description,
            domain.thumbnailDomain?.let {
                ThumbnailViewMapper().fromDomain(domain = it)
            }
        )
    }
}
