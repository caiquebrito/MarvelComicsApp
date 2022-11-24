package com.marvelcomics.brito.data_local.room.extension

import com.marvelcomics.brito.data_local.room.entity.CharacterRoomEntity
import com.marvelcomics.brito.data_local.room.entity.ThumbnailRoomEntity
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.models.ThumbnailDomain

fun CharacterDomain.toCharacterRoomEntity(): CharacterRoomEntity {
    return CharacterRoomEntity(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnailDomain?.toThumbnailRoomEntity()
    )
}

fun CharacterRoomEntity.toCharacterDomain(): CharacterDomain {
    return CharacterDomain(
        id = id,
        name = name,
        description = description,
        thumbnailDomain = thumbnail?.toThumbnailDomain()
    )
}

fun ThumbnailDomain.toThumbnailRoomEntity(): ThumbnailRoomEntity {
    return ThumbnailRoomEntity(
        path = path,
        extension = extension
    )
}

fun ThumbnailRoomEntity.toThumbnailDomain(): ThumbnailDomain {
    return ThumbnailDomain(
        path = path,
        extension = extension
    )
}
