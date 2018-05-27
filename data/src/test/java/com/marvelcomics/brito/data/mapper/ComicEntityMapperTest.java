package com.marvelcomics.brito.data.mapper;

import com.marvelcomics.brito.data.datasource.remote.response.ComicResponse;
import com.marvelcomics.brito.data.datasource.remote.response.ThumbnailResponse;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelData;
import com.marvelcomics.brito.entity.ComicEntity;
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
public class ComicEntityMapperTest {

    @InjectMocks
    private ComicEntityMapper mockComicEntityMapper;

    @Mock
    private RemoteMarvelContainer<ComicResponse> mockComicResponseContainer;
    @Mock
    private RemoteMarvelData<ComicResponse> mockComicResponseData;

    @Mock
    private ThumbnailResponse mockThumbnailResponse;
    @Mock
    private ThumbnailEntityMapper mockThumbnailEntityMapper;
    @Mock
    private ThumbnailEntity mockThumbnailEntity;


    @Before
    public void setupMock() {
        when(mockThumbnailEntityMapper.transform(mockThumbnailResponse)).thenReturn(mockThumbnailEntity);
        when(mockComicResponseContainer.getRemoteMarvelData()).thenReturn(mockComicResponseData);
        List<ComicResponse> comicResponseList = new ArrayList<>();
        ComicResponse comicResponse = new ComicResponse();
        comicResponse.setDescription("Description mock");
        comicResponse.setTitle("Title mock");
        comicResponse.setId(1);
        comicResponse.setThumbnailResponse(mockThumbnailResponse);
        comicResponseList.add(comicResponse);
        when(mockComicResponseData.getResults()).thenReturn(comicResponseList);
    }

    @Test
    public void comicEntityMapper_transform_shouldTransformObject() throws MarvelApiException {
        Object object = mockComicEntityMapper.transform(mockComicResponseContainer).get(0);
        assertThat("Object should return list off " + ComicEntity.class.getCanonicalName(),
                object, instanceOf(ComicEntity.class));
    }

    @Test(expected = MarvelApiException.class)
    public void comicEntityMapper_transform_shouldThrowException() throws MarvelApiException {
        when(mockComicResponseContainer.getRemoteMarvelData()).thenThrow(NullPointerException.class);
        mockComicEntityMapper.transform(mockComicResponseContainer);
    }
}
