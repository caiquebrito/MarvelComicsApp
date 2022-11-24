package com.marvelcomics.brito.data_local

import com.marvelcomics.brito.data.local.MarvelLocalDataSource
import com.marvelcomics.brito.data_local.room.AppDatabase
import com.marvelcomics.brito.data_local.room.extension.toCharacterDomain
import com.marvelcomics.brito.data_local.room.extension.toCharacterRoomEntity
import com.marvelcomics.brito.domain.models.CharacterDomain

class MarvelLocalRepository(private val database: AppDatabase) : MarvelLocalDataSource {

    override suspend fun getCharacterById(id: Int): CharacterDomain {
        return database.characterDao().findCharacterById(id).toCharacterDomain()
    }

    override suspend fun saveCharacter(character: CharacterDomain) {
        database.characterDao().insertCharacter(character.toCharacterRoomEntity())
    }

    override suspend fun getAllCharactersIds(): List<Int> {
        return database.characterDao().getAllCharacters().map {
            it.id
        }
    }
}
