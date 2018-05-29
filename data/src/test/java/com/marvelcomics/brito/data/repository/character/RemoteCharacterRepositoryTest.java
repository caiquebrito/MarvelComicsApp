package com.marvelcomics.brito.data.repository.character;

import com.marvelcomics.brito.data.datasource.remote.MarvelWebService;
import com.marvelcomics.brito.data.datasource.remote.response.CharacterResponse;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RemoteCharacterRepositoryTest {

    @InjectMocks
    private RemoteCharacterRepository remoteCharacterRepository;

    @Mock
    private MarvelWebService mockMarvelWebService;
    @Mock
    private RemoteMarvelContainer<CharacterResponse> mockResponseRemoteMarvelContainer;

    private String mockName;

    @Before
    public void setupMocks() {
        mockName = "mockName";
        when(mockMarvelWebService.characters(any())).thenReturn(Single.just(mockResponseRemoteMarvelContainer));
    }

    @Test
    public void remoteCharacterRepository_characters_shouldReturnObservable() {
        TestObserver<RemoteMarvelContainer<CharacterResponse>> testObserver =
                remoteCharacterRepository.characters(mockName).test();

        testObserver
                .assertNoErrors()
                .assertValue(characterResponseRemoteMarvelContainer -> characterResponseRemoteMarvelContainer != null);
    }
}