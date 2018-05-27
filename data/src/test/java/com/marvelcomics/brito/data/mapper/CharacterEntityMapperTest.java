package com.marvelcomics.brito.data.mapper;

import com.marvelcomics.brito.data.datasource.remote.response.CharacterResponse;
import com.marvelcomics.brito.data.datasource.remote.response.ThumbnailResponse;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelData;
import com.marvelcomics.brito.entity.CharacterEntity;
import com.marvelcomics.brito.entity.ThumbnailEntity;
import com.marvelcomics.brito.infrastructure.exception.MarvelApiException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsInstanceOf.instanceOf;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CharacterEntityMapperTest {

    @InjectMocks
    private CharacterEntityMapper mockCharacterEntityMapper;

    @Mock
    private RemoteMarvelContainer<CharacterResponse> mockCharacterResponseContainer;
    @Mock
    private RemoteMarvelData<CharacterResponse> mockCharacterResponseData;

    @Mock
    private ThumbnailResponse mockThumbnailResponse;
    @Mock
    private ThumbnailEntityMapper mockThumbnailEntityMapper;
    @Mock
    private ThumbnailEntity mockThumbnailEntity;


    @Before
    public void setupMock() {
        when(mockThumbnailEntityMapper.transform(mockThumbnailResponse)).thenReturn(mockThumbnailEntity);
        when(mockCharacterResponseContainer.getRemoteMarvelData()).thenReturn(mockCharacterResponseData);
        List<CharacterResponse> characterResponseList = new ArrayList<>();
        CharacterResponse characterResponse = new CharacterResponse();
        characterResponse.setDescription("Description mock");
        characterResponse.setName("Name mock");
        characterResponse.setModified("Modified mock");
        characterResponse.setId(1);
        characterResponse.setThumbnailResponse(mockThumbnailResponse);
        characterResponseList.add(characterResponse);
        when(mockCharacterResponseData.getResults()).thenReturn(characterResponseList);
    }

    @Test
    public void chacterEntityMapper_transform_shouldTransformObject() throws MarvelApiException {
        Object object = mockCharacterEntityMapper.transform(mockCharacterResponseContainer).get(0);
        assertThat("Object should return list off " + CharacterEntity.class.getCanonicalName(),
                object, instanceOf(CharacterEntity.class));
    }

    @Test(expected = MarvelApiException.class)
    public void characterEntityMapper_transform_shouldThrowException() throws MarvelApiException {
        when(mockCharacterResponseContainer.getRemoteMarvelData()).thenThrow(NullPointerException.class);
        mockCharacterEntityMapper.transform(mockCharacterResponseContainer);
    }
}
