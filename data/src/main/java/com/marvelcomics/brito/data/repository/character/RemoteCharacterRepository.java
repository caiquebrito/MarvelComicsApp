package com.marvelcomics.brito.data.repository.character;

import com.marvelcomics.brito.data.datasource.remote.MarvelWebService;
import com.marvelcomics.brito.data.mapper.CharacterEntityMapper;
import com.marvelcomics.brito.entity.CharacterEntity;

import java.util.List;

import io.reactivex.Observable;

public class RemoteCharacterRepository implements CharacterRepository {

    private MarvelWebService marvelWebService;

    public RemoteCharacterRepository(MarvelWebService marvelWebService) {
        this.marvelWebService = marvelWebService;
    }

    @Override
    public Observable<List<CharacterEntity>> characters(String name) {
        return marvelWebService.characters(name)
                .map(CharacterEntityMapper::transform)
                .toObservable();
    }
}
