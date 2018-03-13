package com.marvelcomics.brito.data.datasource.local.character;

import com.marvelcomics.brito.data.datasource.local.LocalDataSource;

import javax.inject.Inject;

public class CharacterDataSource implements LocalDataSource<CharacterRoom> {

    private CharacterDao characterDao;

    @Inject
    public CharacterDataSource(CharacterDao characterDao) {
        this.characterDao = characterDao;
    }

    @Override
    public void insert(CharacterRoom data) {
        characterDao.insertCharacter(data);
    }

    @Override
    public CharacterRoom getById(long id) {
        return characterDao.getCharacterById(id);
    }

    @Override
    public Iterable<CharacterRoom> getAll() {
        return characterDao.getAllCharacters();
    }

    @Override
    public void delete(CharacterRoom data) {
        characterDao.deleteCharacter(data);
    }

    @Override
    public void update(CharacterRoom data) {
        characterDao.updateCharacter(data);
    }
}
