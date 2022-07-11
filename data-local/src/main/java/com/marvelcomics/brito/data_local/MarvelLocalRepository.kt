package com.marvelcomics.brito.data_local

import android.content.Context
import com.marvelcomics.brito.data.local.MarvelLocalDataSource
import com.marvelcomics.brito.domain.models.CharacterDomain
import com.marvelcomics.brito.domain.models.ThumbnailDomain

class MarvelLocalRepository(context: Context) : MarvelLocalDataSource {

    private val sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    override suspend fun getLastCharacter(): CharacterDomain {
        return CharacterDomain(
            id = sharedPreferences.getInt(CHARACTER_ID, 0),
            name = sharedPreferences.getString(CHARACTER_NAME, ""),
            description = sharedPreferences.getString(CHARACTER_DESCRIPTION, ""),
            ThumbnailDomain(
                path = sharedPreferences.getString(CHARACTER_THUMBNAIL_PATH, ""),
                extension = sharedPreferences.getString(CHARACTER_THUMBNAIL_EXTENSION, "")
            )
        )
    }

    override suspend fun setLastCharacter(character: CharacterDomain) {
        with(sharedPreferences.edit()) {
            putInt(CHARACTER_ID, character.id)
            putString(CHARACTER_NAME, character.name)
            putString(CHARACTER_DESCRIPTION, character.description)
            putString(CHARACTER_THUMBNAIL_PATH, character.thumbnailDomain?.path)
            putString(CHARACTER_THUMBNAIL_EXTENSION, character.thumbnailDomain?.extension)
        }.apply()
    }

    companion object {
        const val CHARACTER_ID = "com.marvelcomics.brito.extras.character_id"
        const val CHARACTER_NAME = "com.marvelcomics.brito.extras.character_name"
        const val CHARACTER_DESCRIPTION = "com.marvelcomics.brito.extras.character_description"
        const val CHARACTER_THUMBNAIL_PATH =
            "com.marvelcomics.brito.extras.character_thumbnail_path"
        const val CHARACTER_THUMBNAIL_EXTENSION =
            "com.marvelcomics.brito.extras.character_thumbnail_extensions"
        const val PREF_NAME = "com.marvelcomics.brito"
    }
}
