package com.marvelcomics.brito.data.repository.character;

import com.marvelcomics.brito.data.datasource.remote.MarvelWebService;
import com.marvelcomics.brito.data.datasource.remote.response.CharacterResponse;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer;

import io.reactivex.Observable;

public class RemoteCharacterRepository implements CharacterRepository {

    private MarvelWebService marvelWebService;

    public RemoteCharacterRepository(MarvelWebService marvelWebService) {
        this.marvelWebService = marvelWebService;
    }

    @Override
    public Observable<RemoteMarvelContainer<CharacterResponse>> characters(String name) {
        return marvelWebService.characters(name).toObservable();
    }
}
