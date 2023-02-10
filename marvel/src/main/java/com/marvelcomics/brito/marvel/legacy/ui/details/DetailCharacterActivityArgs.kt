package com.marvelcomics.brito.marvel.legacy.ui.details

import android.content.Context
import android.content.Intent
import com.marvelcomics.brito.entity.CharacterEntity
import com.marvelcomics.brito.marvel.legacy.ui.ActivityArgs
import com.marvelcomics.brito.marvel.legacy.ui.models.CharacterDataBundle
import com.marvelcomics.brito.marvel.legacy.ui.models.fromDataBundle
import com.marvelcomics.brito.marvel.legacy.ui.models.toDataBundle

class DetailCharacterActivityArgs(val characterEntity: CharacterEntity?) : ActivityArgs {
    override fun build(context: Context): Intent {
        return Intent(context, DetailCharacterFragment::class.java).apply {
            putExtra(EXTRA_CHARACTER, characterEntity?.toDataBundle())
        }
    }

    companion object {
        const val EXTRA_CHARACTER = "com.marvelcomics.brito.view.search.EXTRA_CHARACTER"

        fun deserializeFrom(intent: Intent): DetailCharacterActivityArgs {
            return DetailCharacterActivityArgs(
                characterEntity = intent.getParcelableExtra<CharacterDataBundle>(
                    EXTRA_CHARACTER
                )?.fromDataBundle()
            )
        }
    }
}
