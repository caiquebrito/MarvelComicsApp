package com.marvelcomics.brito.data_local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marvelcomics.brito.data_local.room.dao.CharacterRoomDao
import com.marvelcomics.brito.data_local.room.entity.CharacterRoomEntity

@Database(entities = [CharacterRoomEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterRoomDao
}
