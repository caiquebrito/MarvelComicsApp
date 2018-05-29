package com.marvelcomics.brito.presentation.presenter.home;

import com.marvelcomics.brito.domain.character.CharactersUseCase;
import com.marvelcomics.brito.entity.CharacterEntity;
import com.marvelcomics.brito.infrastructure.di.ResourceProvider;
import com.marvelcomics.brito.infrastructure.di.SchedulersProvider;
import com.marvelcomics.brito.presentation.R;
import com.marvelcomics.brito.presentation.presenter.BasePresenter;
import com.marvelcomics.brito.presentation.presenter.PresenterObserver;

import java.util.List;

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {

    private CharactersUseCase charactersUseCase;
    private ResourceProvider resourceProvider;

    public HomePresenter(HomeContract.View view,
                            SchedulersProvider schedulersProvider,
                            CharactersUseCase charactersUseCase,
                            ResourceProvider resourceProvider) {
        super(view, schedulersProvider);
        this.resourceProvider = resourceProvider;
        this.charactersUseCase = charactersUseCase;
    }

    @Override
    public void loadCharacter(String name) {
        charactersUseCase.execute(name, new PresenterObserver<List<CharacterEntity>>() {
            @Override
            public void onNextObserver(List<CharacterEntity> characterEntities) {
                if (!characterEntities.isEmpty()) {
                    view.showCharacter(characterEntities.get(0));
                } else {
                    view.showError(resourceProvider.getString(R.string.home_character_not_found));
                }
            }

            @Override
            public void onErrorObserver(Throwable throwable) {
                view.showError(throwable.getMessage());
            }
        });
    }
}
