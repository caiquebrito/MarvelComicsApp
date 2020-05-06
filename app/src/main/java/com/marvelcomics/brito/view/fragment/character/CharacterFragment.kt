package com.marvelcomics.brito.marvelcomics.view.fragment.character

import androidx.fragment.app.Fragment

class CharacterFragment : Fragment() {
//    public static final String ARGUMENT_CHARACTER = "character_args";
//
//    private CharacterEntity characterEntity;
//    private FragmentCharacterBinding binding;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        Bundle args = getArguments();
//        characterEntity = args.getParcelable(ARGUMENT_CHARACTER);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character, container, false);
//        binding.setCharacter(characterEntity);
//        return binding.getRoot();
//    }
//
//    @BindingAdapter("android:src")
//    public static void setCharacterImage(ImageView imageView, CharacterEntity character) {
//        Context context = imageView.getContext();
//        Picasso.with(imageView.getContext())
//                .load(character.getFullUrlThumbnailWithAspect(MarvelThumbnailAspectRatio.Portrait.XLARGE))
//                .placeholder(context.getDrawable(R.drawable.progress))
//                .into(imageView);
//    }
//
//    public static CharacterFragment newInstance(CharacterEntity characterEntity) {
//        CharacterFragment fragment = new CharacterFragment();
//
//        Bundle args = new Bundle();
//        args.putParcelable(ARGUMENT_CHARACTER, characterEntity);
//
//        fragment.setArguments(args);
//        return fragment;
//    }
}