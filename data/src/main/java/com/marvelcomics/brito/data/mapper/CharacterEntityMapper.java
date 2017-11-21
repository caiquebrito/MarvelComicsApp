package com.marvelcomics.brito.data.mapper;

import com.marvelcomics.brito.data.datasource.remote.response.CharacterResponse;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer;
import com.marvelcomics.brito.entity.CharacterEntity;
import com.marvelcomics.brito.infrastructure.exception.MarvelApiException;

import java.util.ArrayList;
import java.util.List;

public class CharacterEntityMapper {

    public static List<CharacterEntity> transform(RemoteMarvelContainer<CharacterResponse> remoteMarvelContainer)
            throws MarvelApiException {

        try {
            List<CharacterEntity> characterEntityList = new ArrayList<>();
            for (CharacterResponse characterResponse : remoteMarvelContainer.getRemoteMarvelData().getResults()) {
                CharacterEntity characterEntity = new CharacterEntity(
                        characterResponse.getId(),
                        characterResponse.getName(),
                        characterResponse.getDescription(),
                        ThumbnailEntityMapper.transform(characterResponse.getThumbnailResponse())
                );
                characterEntityList.add(characterEntity);
            }
            return characterEntityList;
        } catch (NullPointerException e) {
            throw new MarvelApiException("Result from server return nulls", e);
        }
    }
}
