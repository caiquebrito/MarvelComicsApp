package com.marvelcomics.brito.view.fragment.comics

import androidx.fragment.app.Fragment

class ComicsFragment : Fragment() {
//    private static final String ARGUMENT_CHARACTER_ID = "character_id_args";
//    private int characterId;
//
//    @Inject
//    protected ComicsPresenter comicsPresenter;
//
//    private FragmentComicsBinding binding;
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Bundle args = getArguments();
//        characterId = args.getInt(ARGUMENT_CHARACTER_ID);
//    }
//
//    @Nullable
//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        super.onCreateView(inflater, container, savedInstanceState);
//        AndroidSupportInjection.inject(this);
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comics, container, false);
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        loadComics();
//    }
//
//    @Override
//    public void showComics(List<ComicEntity> comics) {
//        binding.progressbarLoadingComics.setVisibility(View.GONE);
//        binding.recyclerviewFragmentComic.setVisibility(View.VISIBLE);
//        createdAdapter(comics);
//    }
//
//    @Override
//    public void showError(String message) {
//        binding.progressbarLoadingComics.setVisibility(View.GONE);
//        binding.recyclerviewFragmentComic.setVisibility(View.VISIBLE);
//        //TODO getActivity.getTheme could return nullPointer
//        //AlertDialogUtils.showSimpleDialog("Erro", message, getActivity());
//    }
//
//    private void loadComics() {
//        comicsPresenter.loadComics(characterId);
//    }
//
//    private void createdAdapter(List<ComicEntity> comicEntities) {
//        ComicsAdapter adapter = new ComicsAdapter(comicEntities);
//        binding.recyclerviewFragmentComic.setLayoutManager(
//                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        binding.recyclerviewFragmentComic.setAdapter(adapter);
//        binding.recyclerviewFragmentComic.addItemDecoration(new ItemOffSetDecorationHorizontal(8));
//    }
//
//    public static ComicsFragment newInstance(int characterId) {
//        ComicsFragment fragment = new ComicsFragment();
//
//        Bundle args = new Bundle();
//        args.putInt(ARGUMENT_CHARACTER_ID, characterId);
//
//        fragment.setArguments(args);
//        return fragment;
//    }
}