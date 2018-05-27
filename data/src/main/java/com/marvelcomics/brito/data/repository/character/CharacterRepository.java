package com.marvelcomics.brito.data.repository.character;

import com.marvelcomics.brito.data.datasource.remote.response.CharacterResponse;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer;

import io.reactivex.Observable;

public interface CharacterRepository {

    Observable<RemoteMarvelContainer<CharacterResponse>> characters(String name);
}
