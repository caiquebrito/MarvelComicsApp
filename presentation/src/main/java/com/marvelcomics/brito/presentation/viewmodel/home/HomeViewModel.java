package com.marvelcomics.brito.presentation.viewmodel.home;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.marvelcomics.brito.domain.character.CharactersUseCase;
import com.marvelcomics.brito.entity.CharacterEntity;
import com.marvelcomics.brito.presentation.ResourceModel;
import com.marvelcomics.brito.presentation.viewmodel.BaseObserver;
import com.marvelcomics.brito.presentation.viewmodel.BaseViewModel;

import java.util.List;

public class HomeViewModel extends BaseViewModel {

    private MutableLiveData<ResourceModel<List<CharacterEntity>>> characters;
    private CharactersUseCase charactersUseCase;

    public void setCharactersUseCase(CharactersUseCase charactersUseCase) {
        this.charactersUseCase = charactersUseCase;
    }

    public LiveData<ResourceModel<List<CharacterEntity>>> loadCharacters(String name) {
        characters = new MutableLiveData<>();
        characters.postValue(new ResourceModel<>(ResourceModel.State.LOADING, null, null));
        charactersUseCase.execute(
                name,
                new BaseObserver<>(characters));
        return characters;
    }
}
