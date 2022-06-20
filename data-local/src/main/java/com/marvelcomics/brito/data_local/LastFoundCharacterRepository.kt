package com.marvelcomics.brito.data_local

import android.content.Context
import com.marvelcomics.brito.domain.repository.ILastFoundCharacterRepository

class LastFoundCharacterRepository(context: Context) : ILastFoundCharacterRepository {

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override suspend fun getLastCharacterName(): String {
        return sharedPreferences.getString(CHARACTER_NAME, "") ?: ""
    }

    override suspend fun setLastCharacterName(name: String) {
        sharedPreferences.edit().putString(CHARACTER_NAME, name).apply()
    }

    companion object {
        const val CHARACTER_NAME = "character_name"
        const val PREF_NAME = "com.marvelcomics.brito"
    }
}
