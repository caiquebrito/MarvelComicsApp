package com.marvelcomics.brito.marvelcomics.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;

import com.marvelcomics.brito.entity.CharacterEntity;
import com.marvelcomics.brito.infrastructure.utils.MarvelThumbnailAspectRatio;
import com.marvelcomics.brito.marvelcomics.R;
import com.marvelcomics.brito.marvelcomics.databinding.ActivityHomeBinding;
import com.marvelcomics.brito.presentation.ResourceModel;
import com.marvelcomics.brito.presentation.viewmodel.home.HomeViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class HomeActivity extends AppCompatActivity {

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

    public void searchViewFocus() {
        binding.searchviewMarvelCharacter.setIconified(false);
    }

    private void observeCharacter(String name) {
        homeViewModel.loadCharacters(name).observe(this, listResourceModel -> {
            if (listResourceModel != null) {
                handleCharactersResult(listResourceModel);
            }
        });
    }

    private void handleCharactersResult(ResourceModel<List<CharacterEntity>> listResourceModel) {
        switch (listResourceModel.getState()) {
            case LOADING:
                break;
            case SUCCESS:
                List<CharacterEntity> characterResource = listResourceModel.getData();
                updateViewWithCharacterInfo(characterResource.get(0));
                break;
            case ERROR:
                binding.textviewMarvelCharacterResult.setText("Error Marvel API: " + listResourceModel.getMessage());
                break;
            default:
                // do nothing
        }
    }

    private void updateViewWithCharacterInfo(CharacterEntity characterEntity) {
        binding.textviewMarvelCharacterResult.setText(characterEntity.getDescription());
        Picasso.with(this)
                .load(
                        characterEntity.getFullUrlThumbnailWithAspect(MarvelThumbnailAspectRatio.Full.FULLSIZE))
                .into(binding.imageviewMarvelCharacterResult);
    }
}
