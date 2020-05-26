package com.marvelcomics.brito.data.repository.characters

import com.marvelcomics.brito.data.entity.CharacterEntity
import com.marvelcomics.brito.data.handler.ResourceModel

interface CharacterRepository {
    suspend fun getCharacters(name: String): ResourceModel<CharacterEntity>
}