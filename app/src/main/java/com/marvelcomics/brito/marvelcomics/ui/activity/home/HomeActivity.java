package com.marvelcomics.brito.marvelcomics.ui.activity.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.View;

import com.marvelcomics.brito.entity.CharacterEntity;
import com.marvelcomics.brito.infrastructure.utils.AlertDialogUtils;
import com.marvelcomics.brito.marvelcomics.R;
import com.marvelcomics.brito.marvelcomics.databinding.ActivityHomeBinding;
import com.marvelcomics.brito.marvelcomics.ui.fragment.character.CharacterFragment;
import com.marvelcomics.brito.marvelcomics.ui.fragment.comics.ComicsFragment;
import com.marvelcomics.brito.marvelcomics.ui.fragment.series.SeriesFragment;
import com.marvelcomics.brito.presentation.presenter.home.HomeContract;
import com.marvelcomics.brito.presentation.presenter.home.HomePresenter;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public class HomeActivity extends AppCompatActivity implements HomeContract.View {

    private ActivityHomeBinding binding;

    @Inject
    protected HomePresenter homePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidInjection.inject(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.setViewActivity(this);
        homePresenter.setView(this);
        initListeners();
    }

    @Override
    public void showCharacter(CharacterEntity character) {
        binding.imageviewLoading.setVisibility(View.GONE);
        instantiateCharacterFragment(character);
        instantiateComicsFragment(character.getId());
        instantiateSeriesFragment(character.getId());
    }

    @Override
    public void showError(String message) {
        binding.imageviewLoading.setVisibility(View.GONE);
        AlertDialogUtils.showSimpleDialog("Erro", message, this);
    }

    public void searchViewFocus() {
        binding.searchviewMarvelCharacter.setIconified(false);
    }

    private void initListeners() {
        binding.searchviewMarvelCharacter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                binding.imageviewLoading.setVisibility(View.VISIBLE);
                homePresenter.loadCharacter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
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
