package com.marvelcomics.brito.data.repository.series;

import com.marvelcomics.brito.data.datasource.remote.MarvelWebService;
import com.marvelcomics.brito.data.datasource.remote.response.SeriesResponse;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RemoteSeriesRepositoryTest {

    @InjectMocks
    private RemoteSeriesRepository remoteSeriesRepository;

    @Mock
    private MarvelWebService mockMarvelWebService;
    @Mock
    private RemoteMarvelContainer<SeriesResponse> mockResponseRemoteMarvelContainer;

    private int mockId;

    @Before
    public void setupMocks() {
        mockId = 1;
        when(mockMarvelWebService.series(anyInt())).thenReturn(Single.just(mockResponseRemoteMarvelContainer));
    }

    @Test
    public void remoteSeriesRepository_characters_shouldReturnObservable() {
        TestObserver<RemoteMarvelContainer<SeriesResponse>> testObserver =
                remoteSeriesRepository.series(mockId).test();

        testObserver
                .assertNoErrors()
                .assertValue(seriesResponseRemoteMarvelContainer -> seriesResponseRemoteMarvelContainer != null);
    }
}