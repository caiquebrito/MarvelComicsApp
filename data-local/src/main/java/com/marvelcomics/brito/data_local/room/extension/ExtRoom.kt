package com.marvelcomics.brito.data_local.room.extension

import com.marvelcomics.brito.data_local.room.entity.CharacterRoomEntity
import com.marvelcomics.brito.data_local.room.entity.ThumbnailRoomEntity
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.entity.ThumbnailEntity

fun CharacterEntity.toCharacterRoomEntity(): CharacterRoomEntity {
    return CharacterRoomEntity(
        id = id,
        name = name,
        description = description,
        thumbnail = thumbnailEntity?.toThumbnailRoomEntity()
    )
}

fun CharacterRoomEntity.toCharacterDomain(): CharacterEntity {
    return CharacterEntity(
        id = id,
        name = name,
        description = description,
        thumbnailEntity = thumbnail?.toThumbnailDomain()
    )
}

fun ThumbnailEntity.toThumbnailRoomEntity(): ThumbnailRoomEntity {
    return ThumbnailRoomEntity(
        path = path,
        extension = extension
    )
}

fun ThumbnailRoomEntity.toThumbnailDomain(): ThumbnailEntity {
    return ThumbnailEntity(
        path = path,
        extension = extension
    )
}
