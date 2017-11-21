package com.marvelcomics.brito.data.repository.character;

import com.marvelcomics.brito.entity.CharacterEntity;

import java.util.List;

import io.reactivex.Observable;

public interface CharacterRepository {

    Observable<List<CharacterEntity>> characters(String name);
}
