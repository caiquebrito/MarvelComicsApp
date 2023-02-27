package com.marvelcomics.brito.datalocal.room.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "character")
data class CharacterRoomEntity(
    @PrimaryKey val id: Int,
    val name: String?,
    val description: String?,
    @Embedded
    var thumbnail: ThumbnailRoomEntity?
)
