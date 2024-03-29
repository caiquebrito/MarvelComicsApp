package com.marvelcomics.brito.datalocal.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.marvelcomics.brito.datalocal.room.entity.CharacterRoomEntity

@Dao
interface CharacterRoomDao {

    @Query("SELECT * FROM character")
    fun getAllCharacters(): List<CharacterRoomEntity>

    @Query("SELECT id FROM character")
    fun getAllCharactersIds(): List<Int>

    @Query("SELECT * FROM character WHERE id = :id")
    fun findCharacterById(id: Int): CharacterRoomEntity

    @Insert
    fun insertCharacter(characters: CharacterRoomEntity)

    @Delete
    fun deleteCharacter(character: CharacterRoomEntity)
}
