package com.marvelcomics.brito.datalocal

import com.marvelcomics.brito.data.local.MarvelLocalDataSource
import com.marvelcomics.brito.datalocal.room.AppDatabase
import com.marvelcomics.brito.datalocal.room.extension.toCharacterDomain
import com.marvelcomics.brito.datalocal.room.extension.toCharacterRoomEntity
import com.marvelcomics.brito.entity.CharacterEntity

class MarvelLocalRepository(private val database: AppDatabase) : MarvelLocalDataSource {

    override suspend fun loadCharacterById(id: Int): CharacterEntity {
        return database.characterDao().findCharacterById(id).toCharacterDomain()
    }

    override suspend fun saveCharacter(character: CharacterEntity) {
        database.characterDao().insertCharacter(character.toCharacterRoomEntity())
    }

    override suspend fun loadAllCharactersIds(): List<Int> {
        return database.characterDao().getAllCharactersIds()
    }

    override suspend fun loadAllCharacters(): List<CharacterEntity> {
        return database.characterDao().getAllCharacters().map {
            it.toCharacterDomain()
        }
    }
}
