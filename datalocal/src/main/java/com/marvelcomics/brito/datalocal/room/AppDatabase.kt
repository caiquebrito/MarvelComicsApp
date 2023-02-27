package com.marvelcomics.brito.datalocal.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.marvelcomics.brito.datalocal.room.dao.CharacterRoomDao
import com.marvelcomics.brito.datalocal.room.entity.CharacterRoomEntity

@Database(entities = [CharacterRoomEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun characterDao(): CharacterRoomDao
}
