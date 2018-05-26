package com.marvelcomics.brito.presentation.presenter.home;

import com.marvelcomics.brito.domain.character.CharactersUseCase;
import com.marvelcomics.brito.entity.CharacterEntity;
import com.marvelcomics.brito.presentation.presenter.BasePresenter;
import com.marvelcomics.brito.presentation.presenter.PresenterObserver;

import java.util.List;

public class HomePresenter extends BasePresenter implements HomeContract.Presenter {

    private CharactersUseCase charactersUseCase;
    private HomeContract.View view;

    public HomePresenter(CharactersUseCase charactersUseCase) {
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
                    onErrorObserver(new Exception("Não foi encontrado nenhum personagem com esse nome."));
                }
            }

            @Override
            public void onErrorObserver(Throwable throwable) {
                view.showError(throwable.getMessage());
            }
        });
    }

    public void setView(HomeContract.View view) {
        this.view = view;
    }
}
