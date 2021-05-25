package com.marvelcomics.brito.view.fragment.character

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.marvelcomics.brito.R
import com.marvelcomics.brito.databinding.FragmentCharacterBinding
import com.marvelcomics.brito.domain.entity.CharacterEntity
import com.marvelcomics.brito.infrastructure.utils.MarvelThumbnailAspectRatio
import com.marvelcomics.brito.view.extensions.viewBinding

class CharacterFragment : Fragment(R.layout.fragment_character) {

    private val binding by viewBinding(FragmentCharacterBinding::bind)
    private var characterEntity: CharacterEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        characterEntity = args?.getSerializable(ARGUMENT_CHARACTER) as CharacterEntity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.textviewFragmentCharacterName.text = characterEntity?.name
        binding.textviewFragmentCharacterDescription.text = characterEntity?.description
        Glide.with(this)
            .load(
                characterEntity?.getFullUrlThumbnailWithAspect(
                    MarvelThumbnailAspectRatio.Portrait.MEDIUM
                )
            )
            .apply(RequestOptions().centerCrop())
            .into(binding.imageviewFragmentCharacter)
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
