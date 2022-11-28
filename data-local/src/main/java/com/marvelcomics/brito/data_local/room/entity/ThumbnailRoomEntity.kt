package com.marvelcomics.brito.data_local.room.entity

import androidx.room.Entity

@Entity(tableName = "thumbnail")
data class ThumbnailRoomEntity(
    val path: String? = null,
    val extension: String? = null
)
