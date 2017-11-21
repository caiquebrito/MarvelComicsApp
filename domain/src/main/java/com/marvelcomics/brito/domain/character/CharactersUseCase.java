package com.marvelcomics.brito.domain.character;

import com.marvelcomics.brito.data.repository.character.CharacterRepository;
import com.marvelcomics.brito.domain.UseCase;
import com.marvelcomics.brito.entity.CharacterEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CharactersUseCase extends UseCase<String, List<CharacterEntity>> {

    private CharacterRepository characterRepository;

    @Inject
    public CharactersUseCase(CharacterRepository characterRepository) {
        this.characterRepository = characterRepository;
    }

    @Override
    protected Observable<List<CharacterEntity>> createObservable(String name) {
        return characterRepository.characters(name);
    }
}