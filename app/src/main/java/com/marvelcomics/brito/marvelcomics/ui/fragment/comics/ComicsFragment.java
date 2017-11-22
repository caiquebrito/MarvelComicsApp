package com.marvelcomics.brito.marvelcomics.ui.fragment.comics;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.marvelcomics.brito.entity.ComicEntity;
import com.marvelcomics.brito.marvelcomics.R;
import com.marvelcomics.brito.marvelcomics.databinding.FragmentComicsBinding;
import com.marvelcomics.brito.marvelcomics.ui.fragment.ItemOffSetDecorationHorizontal;
import com.marvelcomics.brito.presentation.ResourceModel;
import com.marvelcomics.brito.presentation.viewmodel.comics.ComicsViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public class ComicsFragment extends Fragment {

    private static final String ARGUMENT_CHARACTER_ID = "character_id_args";
    private int characterId;

    @Inject
    protected ComicsViewModel comicsViewModel;

    private FragmentComicsBinding binding;

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
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comics, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeComics();
    }

    private void observeComics() {
        comicsViewModel.loadComics(characterId).observe(this, listResourceModel -> {
            if (listResourceModel != null) {
                handleComicsResult(listResourceModel);
            }
        });
    }

    private void handleComicsResult(ResourceModel<List<ComicEntity>> listResourceModel) {
        switch (listResourceModel.getState()) {
            case LOADING:
                break;
            case SUCCESS:
                List<ComicEntity> comicResource = listResourceModel.getData();
                if (!comicResource.isEmpty()) {
                    createdAdapter(comicResource);
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

    private void createdAdapter(List<ComicEntity> comicResource) {
        ComicsAdapter adapter = new ComicsAdapter(comicResource);
        binding.recyclerviewFragmentComic.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        binding.recyclerviewFragmentComic.setAdapter(adapter);
        binding.recyclerviewFragmentComic.addItemDecoration(new ItemOffSetDecorationHorizontal(8));
    }

    public static ComicsFragment newInstance(int characterId) {
        ComicsFragment fragment = new ComicsFragment();

        Bundle args = new Bundle();
        args.putInt(ARGUMENT_CHARACTER_ID, characterId);

        fragment.setArguments(args);
        return fragment;
    }
}