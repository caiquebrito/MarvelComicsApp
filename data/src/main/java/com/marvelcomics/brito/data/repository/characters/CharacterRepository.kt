package com.marvelcomics.brito.data.repository.characters

import com.marvelcomics.brito.data.datasource.remote.response.CharacterResponse
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelData
import com.marvelcomics.brito.data.handler.ResourceModel

interface CharacterRepository {
    suspend fun characters(name: String): ResourceModel<RemoteMarvelData<CharacterResponse>>
}