package com.marvelcomics.brito.data_local.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterRoomEntity(
    @PrimaryKey val id: Int,
    val name: String?,
    val description: String?,
    @Embedded
    val thumbnail: ThumbnailRoomEntity?
)
