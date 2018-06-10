package com.marvelcomics.brito.marvelcomics.ui.fragment.series;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marvelcomics.brito.entity.SeriesEntity;
import com.marvelcomics.brito.marvelcomics.R;
import com.marvelcomics.brito.marvelcomics.databinding.FragmentSeriesBinding;
import com.marvelcomics.brito.marvelcomics.ui.activity.home.HomeActivity;
import com.marvelcomics.brito.marvelcomics.ui.fragment.ItemOffSetDecorationHorizontal;
import com.marvelcomics.brito.presentation.presenter.home.HomeContract;
import com.marvelcomics.brito.presentation.presenter.series.SeriesContract;
import com.marvelcomics.brito.presentation.presenter.series.SeriesPresenter;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class SeriesFragment extends Fragment implements SeriesContract.View {

    private static final String ARGUMENT_CHARACTER_ID = "character_id_args";
    private static final String ARGUMENT_HOME_VIEW = "home_view_args";
    private int characterId;

    @Inject
    protected SeriesPresenter seriesPresenter;

    private FragmentSeriesBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        characterId = args.getInt(ARGUMENT_CHARACTER_ID);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        AndroidSupportInjection.inject(this);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_series, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadSeries();
    }

    @Override
    public void showSeries(List<SeriesEntity> seriesEntities) {
        binding.progressbarLoadingSeries.setVisibility(View.GONE);
        binding.recyclerviewFragmentSeries.setVisibility(View.VISIBLE);
        createdAdapter(seriesEntities);
    }

    @Override
    public void showError(String message) {
        //TODO getActivity.getTheme could return nullPointer
        binding.progressbarLoadingSeries.setVisibility(View.GONE);
        binding.recyclerviewFragmentSeries.setVisibility(View.VISIBLE);
        //AlertDialogUtils.showSimpleDialog("Erro", message, getActivity());
    }

    private void loadSeries() {
        seriesPresenter.loadSeries(characterId);
    }

    private void createdAdapter(List<SeriesEntity> seriesResource) {
        SeriesAdapter adapter = new SeriesAdapter(seriesResource);
        binding.recyclerviewFragmentSeries.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerviewFragmentSeries.setAdapter(adapter);
        binding.recyclerviewFragmentSeries.addItemDecoration(new ItemOffSetDecorationHorizontal(8));
    }

    public static SeriesFragment newInstance(int characterId) {
        SeriesFragment fragment = new SeriesFragment();

        Bundle args = new Bundle();
        args.putInt(ARGUMENT_CHARACTER_ID, characterId);

        fragment.setArguments(args);
        return fragment;
    }
}
