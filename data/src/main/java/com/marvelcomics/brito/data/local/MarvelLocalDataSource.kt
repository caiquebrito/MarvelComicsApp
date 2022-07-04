package com.marvelcomics.brito.data.local

interface MarvelLocalDataSource {
    suspend fun getLastCharacterName(): String
    suspend fun setLastCharacterName(name: String)
}
