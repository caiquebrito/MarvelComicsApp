package com.marvelcomics.brito.marvelcomics.ui.fragment.character;


import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.marvelcomics.brito.entity.CharacterEntity;
import com.marvelcomics.brito.infrastructure.utils.MarvelThumbnailAspectRatio;
import com.marvelcomics.brito.marvelcomics.R;
import com.marvelcomics.brito.marvelcomics.databinding.FragmentCharacterBinding;
import com.squareup.picasso.Picasso;

public class CharacterFragment extends Fragment {

    public static final String ARGUMENT_CHARACTER = "character_args";

    private CharacterEntity characterEntity;
    private FragmentCharacterBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        characterEntity = args.getParcelable(ARGUMENT_CHARACTER);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character, container, false);
        binding.setCharacter(characterEntity);
        return binding.getRoot();
    }

    @BindingAdapter("android:src")
    public static void setCharacterImage(ImageView imageView, CharacterEntity character) {
        Context context = imageView.getContext();
        Picasso.with(imageView.getContext())
                .load(character.getFullUrlThumbnailWithAspect(MarvelThumbnailAspectRatio.Portrait.XLARGE))
                .placeholder(context.getDrawable(R.drawable.progress))
                .into(imageView);
    }

    public static CharacterFragment newInstance(CharacterEntity characterEntity) {
        CharacterFragment fragment = new CharacterFragment();

        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_CHARACTER, characterEntity);

        fragment.setArguments(args);
        return fragment;
    }
}
