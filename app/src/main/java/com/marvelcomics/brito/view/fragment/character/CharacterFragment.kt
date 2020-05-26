package com.marvelcomics.brito.view.fragment.character

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.marvelcomics.brito.R
import com.marvelcomics.brito.data.entity.CharacterEntity
import com.marvelcomics.brito.infrastructure.utils.MarvelThumbnailAspectRatio
import kotlinx.android.synthetic.main.fragment_character.view.*

class CharacterFragment : Fragment() {

    private var characterEntity: CharacterEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        characterEntity = args?.getSerializable(ARGUMENT_CHARACTER) as CharacterEntity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val inflater = layoutInflater.inflate(R.layout.fragment_character, null)
        inflater.textview_fragment_character_name.text = characterEntity?.name
        inflater.textview_fragment_character_description.text = characterEntity?.description
        Glide.with(this)
            .load(characterEntity?.getFullUrlThumbnailWithAspect(MarvelThumbnailAspectRatio.Portrait.MEDIUM))
            .placeholder(context?.getDrawable(R.drawable.progress))
            .into(inflater.imageview_fragment_character)
    }

    companion object {
        const val ARGUMENT_CHARACTER = "character_args"

        fun newInstance(characterEntity: CharacterEntity): CharacterFragment {
            val fragment = CharacterFragment()
            val args = Bundle()
            args.putSerializable(ARGUMENT_CHARACTER, characterEntity)
            fragment.arguments = args
            return fragment
        }
    }
}