package com.marvelcomics.brito.data.repository.comics;

import com.marvelcomics.brito.data.datasource.remote.MarvelWebService;
import com.marvelcomics.brito.data.datasource.remote.response.ComicResponse;
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
public class RemoteComicRepositoryTest {

    @InjectMocks
    private RemoteComicRepository remoteComicRepository;

    @Mock
    private MarvelWebService mockMarvelWebService;
    @Mock
    private RemoteMarvelContainer<ComicResponse> mockResponseRemoteMarvelContainer;

    private int mockId;

    @Before
    public void setupMocks() {
        mockId = 1;
        when(mockMarvelWebService.comics(anyInt())).thenReturn(Single.just(mockResponseRemoteMarvelContainer));
    }

    @Test
    public void remoteComicsRepository_characters_shouldReturnObservable() {
        TestObserver<RemoteMarvelContainer<ComicResponse>> testObserver =
                remoteComicRepository.comics(mockId).test();

        testObserver
                .assertNoErrors()
                .assertValue(comicResponseRemoteMarvelContainer -> comicResponseRemoteMarvelContainer != null);
    }
}