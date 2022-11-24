package com.marvelcomics.brito.data_local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity
data class ThumbnailRoomEntity(
    @ColumnInfo(name = "path") val path: String? = null,
    @ColumnInfo(name = "extension") val extension: String? = null
)
