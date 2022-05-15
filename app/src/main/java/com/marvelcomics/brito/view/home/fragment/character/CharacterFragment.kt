package com.marvelcomics.brito.view.home.fragment.character

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.marvelcomics.brito.R
import com.marvelcomics.brito.databinding.FragmentCharacterBinding
import com.marvelcomics.brito.infrastructure.utils.MarvelThumbnailAspectRatio
import com.marvelcomics.brito.view.extensions.viewBinding
import com.marvelcomics.brito.view.models.CharacterEntity

class CharacterFragment : Fragment(R.layout.fragment_character) {

    private val binding by viewBinding(FragmentCharacterBinding::bind)
    private var characterEntity: CharacterEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        characterEntity = args?.getParcelable(ARGUMENT_CHARACTER)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textviewFragmentCharacterName.text = characterEntity?.name
        binding.textviewFragmentCharacterDescription.text = characterEntity?.description
        Glide.with(this)
            .load(
                characterEntity?.thumbnailEntity?.getFullUrlThumbnailWithAspect(
                    MarvelThumbnailAspectRatio.Portrait.MEDIUM
                )
            )
            .apply(RequestOptions().centerCrop())
            .into(binding.imageviewFragmentCharacter)
    }

    companion object {
        const val ARGUMENT_CHARACTER = "character_args"

        fun newInstance(character: CharacterEntity): CharacterFragment {
            val fragment = CharacterFragment()
            val args = Bundle()
            args.putParcelable(ARGUMENT_CHARACTER, character)
            fragment.arguments = args
            return fragment
        }
    }
}
