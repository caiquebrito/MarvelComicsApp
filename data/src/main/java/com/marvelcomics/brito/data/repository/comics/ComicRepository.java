package com.marvelcomics.brito.data.repository.comics;

import com.marvelcomics.brito.data.datasource.remote.response.ComicResponse;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer;

import io.reactivex.Observable;

public interface ComicRepository {

    Observable<RemoteMarvelContainer<ComicResponse>> comics(int characterId);
}
