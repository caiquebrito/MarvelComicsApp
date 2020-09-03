package com.marvelcomics.brito.view.fragment.character

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.marvelcomics.brito.R
import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.infrastructure.utils.MarvelThumbnailAspectRatio
import kotlinx.android.synthetic.main.fragment_character.view.*

class CharacterFragment : Fragment() {

    private var characterEntity: CharacterEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        characterEntity = args?.getSerializable(ARGUMENT_CHARACTER) as CharacterEntity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val inflatedView = inflater.inflate(R.layout.fragment_character, null)
        inflatedView.textview_fragment_character_name.text = characterEntity?.name
        inflatedView.textview_fragment_character_description.text = characterEntity?.description

        Glide.with(this)
            .load(characterEntity?.getFullUrlThumbnailWithAspect(MarvelThumbnailAspectRatio.Portrait.MEDIUM))
//            .placeholder(context.circularProgressBar())
            .apply(RequestOptions().centerCrop())
            .into(inflatedView.imageview_fragment_character)

        return inflatedView
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