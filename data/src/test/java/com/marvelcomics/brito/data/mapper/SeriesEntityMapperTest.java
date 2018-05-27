package com.marvelcomics.brito.data.mapper;

import com.marvelcomics.brito.data.datasource.remote.response.SeriesResponse;
import com.marvelcomics.brito.data.datasource.remote.response.ThumbnailResponse;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelData;
import com.marvelcomics.brito.entity.SeriesEntity;
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
public class SeriesEntityMapperTest {

    @InjectMocks
    private SeriesEntityMapper mockSeriesEntityMapper;

    @Mock
    private RemoteMarvelContainer<SeriesResponse> mockSeriesResponseContainer;
    @Mock
    private RemoteMarvelData<SeriesResponse> mockSeriesResponseData;

    @Mock
    private ThumbnailResponse mockThumbnailResponse;
    @Mock
    private ThumbnailEntityMapper mockThumbnailEntityMapper;
    @Mock
    private ThumbnailEntity mockThumbnailEntity;

    @Before
    public void setupMock() {
        when(mockThumbnailEntityMapper.transform(mockThumbnailResponse)).thenReturn(mockThumbnailEntity);
        when(mockSeriesResponseContainer.getRemoteMarvelData()).thenReturn(mockSeriesResponseData);
        List<SeriesResponse> seriesResponseList = new ArrayList<>();
        SeriesResponse seriesResponse = new SeriesResponse();
        seriesResponse.setDescription("Description mock");
        seriesResponse.setTitle("Title mock");
        seriesResponse.setId(1);
        seriesResponse.setThumbnailResponse(mockThumbnailResponse);
        seriesResponseList.add(seriesResponse);
        when(mockSeriesResponseData.getResults()).thenReturn(seriesResponseList);
    }

    @Test
    public void seriesEntityMapper_transform_shouldTransformObject() throws MarvelApiException {
        Object object = mockSeriesEntityMapper.transform(mockSeriesResponseContainer).get(0);
        assertThat("Object should return list off " + SeriesEntity.class.getCanonicalName(),
                object, instanceOf(SeriesEntity.class));
    }

    @Test(expected = MarvelApiException.class)
    public void seriesEntityMapper_transform_shouldThrowException() throws MarvelApiException {
        when(mockSeriesResponseContainer.getRemoteMarvelData()).thenThrow(NullPointerException.class);
        mockSeriesEntityMapper.transform(mockSeriesResponseContainer);
    }
}
