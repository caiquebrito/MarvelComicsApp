package com.marvelcomics.brito.data_local.room.entity

import androidx.room.Entity

@Entity(tableName = "thumbnail")
data class ThumbnailRoomEntity(
    var path: String? = null,
    var extension: String? = null
)
