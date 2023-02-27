package com.marvelcomics.brito.datalocal.room.entity

import androidx.room.Entity

@Entity(tableName = "thumbnail")
data class ThumbnailRoomEntity(
    var path: String? = null,
    var extension: String? = null
)
