package com.marvelcomics.brito.marvelcomics.ui.activity.home;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.marvelcomics.brito.entity.CharacterEntity;
import com.marvelcomics.brito.marvelcomics.R;
import com.marvelcomics.brito.marvelcomics.databinding.ActivityHomeBinding;
import com.marvelcomics.brito.marvelcomics.ui.ResourceModelHandler;
import com.marvelcomics.brito.marvelcomics.ui.activity.BaseActivity;
import com.marvelcomics.brito.marvelcomics.ui.fragment.character.CharacterFragment;
import com.marvelcomics.brito.marvelcomics.ui.fragment.comics.ComicsFragment;
import com.marvelcomics.brito.marvelcomics.ui.fragment.series.SeriesFragment;
import com.marvelcomics.brito.presentation.ResourceModel;
import com.marvelcomics.brito.presentation.viewmodel.home.HomeViewModel;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class HomeActivity extends BaseActivity implements ResourceModelHandler.ResourceModelListener<ResourceModel<List<CharacterEntity>>> {

    private ActivityHomeBinding binding;

    @Inject
    protected HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setViewActivity(this);
        initListeners();
    }

    @Override
    public void onSuccessState(ResourceModel<List<CharacterEntity>> resourceModel) {
        List<CharacterEntity> characterResource = resourceModel.getData();
        if (!characterResource.isEmpty()) {
            binding.imageviewLoading.setVisibility(View.GONE);
            CharacterEntity character = characterResource.get(0);
            instantiateCharacterFragment(character);
            instantiateComicsFragment(character.getId());
            instantiateSeriesFragment(character.getId());
        } else {
            //TODO DialogAlert
        }
    }

    @Override
    public void onErrorState(Throwable throwable) {
        //TODO DialogAlert
    }

    @Override
    public void onLoadingState() {
        binding.imageviewLoading.setVisibility(View.VISIBLE);
    }

    public void searchViewFocus() {
        binding.searchviewMarvelCharacter.setIconified(false);
    }

    private void initListeners() {
        binding.searchviewMarvelCharacter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                observeCharacter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void observeCharacter(String name) {
        homeViewModel.loadCharacters(name).observe(this, new Observer<ResourceModel<List<CharacterEntity>>>() {
            @Override
            public void onChanged(@Nullable ResourceModel<List<CharacterEntity>> listResourceModel) {
                onModelChanged(listResourceModel, HomeActivity.this);
            }
        });
    }

    private void instantiateCharacterFragment(CharacterEntity characterEntity) {
        CharacterFragment fragment = CharacterFragment.newInstance(characterEntity);
        commitFragment(R.id.fragment_home_character, fragment);
    }

    private void instantiateComicsFragment(int id) {
        ComicsFragment fragment = ComicsFragment.newInstance(id);
        commitFragment(R.id.fragment_home_comics, fragment);
    }

    private void instantiateSeriesFragment(int id) {
        SeriesFragment fragment = SeriesFragment.newInstance(id);
        commitFragment(R.id.fragment_home_series, fragment);
    }

    private void commitFragment(int containerId, Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(containerId,
                fragment, fragment.getClass().getSimpleName()).commit();
    }
}
