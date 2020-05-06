package com.marvelcomics.brito.marvelcomics.view.fragment.series

import androidx.fragment.app.Fragment

class SeriesFragment : Fragment() {
//    private static final String ARGUMENT_CHARACTER_ID = "character_id_args";
//    private static final String ARGUMENT_HOME_VIEW = "home_view_args";
//    private int characterId;
//
//    @Inject
//    protected SeriesPresenter seriesPresenter;
//
//    private FragmentSeriesBinding binding;
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
//        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_series, container, false);
//        return binding.getRoot();
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        loadSeries();
//    }
//
//    @Override
//    public void showSeries(List<SeriesEntity> seriesEntities) {
//        binding.progressbarLoadingSeries.setVisibility(View.GONE);
//        binding.recyclerviewFragmentSeries.setVisibility(View.VISIBLE);
//        createdAdapter(seriesEntities);
//    }
//
//    @Override
//    public void showError(String message) {
//        //TODO getActivity.getTheme could return nullPointer
//        binding.progressbarLoadingSeries.setVisibility(View.GONE);
//        binding.recyclerviewFragmentSeries.setVisibility(View.VISIBLE);
//        //AlertDialogUtils.showSimpleDialog("Erro", message, getActivity());
//    }
//
//    private void loadSeries() {
//        seriesPresenter.loadSeries(characterId);
//    }
//
//    private void createdAdapter(List<SeriesEntity> seriesResource) {
//        SeriesAdapter adapter = new SeriesAdapter(seriesResource);
//        binding.recyclerviewFragmentSeries.setLayoutManager(
//                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
//        binding.recyclerviewFragmentSeries.setAdapter(adapter);
//        binding.recyclerviewFragmentSeries.addItemDecoration(new ItemOffSetDecorationHorizontal(8));
//    }
//
//    public static SeriesFragment newInstance(int characterId) {
//        SeriesFragment fragment = new SeriesFragment();
//
//        Bundle args = new Bundle();
//        args.putInt(ARGUMENT_CHARACTER_ID, characterId);
//
//        fragment.setArguments(args);
//        return fragment;
//    }
}