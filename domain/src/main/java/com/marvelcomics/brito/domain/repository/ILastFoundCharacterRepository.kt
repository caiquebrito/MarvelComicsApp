package com.marvelcomics.brito.domain.repository

interface ILastFoundCharacterRepository {
    suspend fun getLastCharacterName(): String
    suspend fun setLastCharacterName(name: String)
}