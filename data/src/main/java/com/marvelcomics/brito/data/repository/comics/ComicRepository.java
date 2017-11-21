package com.marvelcomics.brito.data.repository.comics;

import com.marvelcomics.brito.entity.ComicEntity;

import java.util.List;

import io.reactivex.Observable;

public interface ComicRepository {

    Observable<List<ComicEntity>> comics(int characterId);
}
