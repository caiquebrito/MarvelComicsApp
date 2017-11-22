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
import com.marvelcomics.brito.marvelcomics.ui.fragment.ItemOffSetDecorationHorizontal;
import com.marvelcomics.brito.presentation.ResourceModel;
import com.marvelcomics.brito.presentation.viewmodel.series.SeriesViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class SeriesFragment extends Fragment {

    private static final String ARGUMENT_CHARACTER_ID = "character_id_args";
    private int characterId;

    @Inject
    protected SeriesViewModel seriesViewModel;

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
        observeComics();
    }

    private void observeComics() {
        seriesViewModel.loadSeries(characterId).observe(this, listResourceModel -> {
            if (listResourceModel != null) {
                handleSeriesResult(listResourceModel);
            }
        });
    }

    private void handleSeriesResult(ResourceModel<List<SeriesEntity>> listResourceModel) {
        switch (listResourceModel.getState()) {
            case LOADING:
                break;
            case SUCCESS:
                List<SeriesEntity> seriesResource = listResourceModel.getData();
                if (!seriesResource.isEmpty()) {
                    createdAdapter(seriesResource);
                } else {
                    //TODO DialogAlert
                }
                break;
            case ERROR:
                //TODO DialogAlert
                //binding.textviewMarvelCharacterResult.setText("Error Marvel API: " + listResourceModel.getMessage());
                break;
            default:
                // do nothing
        }
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
