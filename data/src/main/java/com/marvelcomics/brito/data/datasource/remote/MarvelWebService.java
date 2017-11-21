package com.marvelcomics.brito.data.datasource.remote;

import com.marvelcomics.brito.data.datasource.remote.response.CharacterResponse;
import com.marvelcomics.brito.data.datasource.remote.response.ComicResponse;
import com.marvelcomics.brito.data.datasource.remote.response.SeriesResponse;
import com.marvelcomics.brito.data.datasource.remote.response.model.RemoteMarvelContainer;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MarvelWebService {

    String BASE_URL = "https://gateway.marvel.com/v1/public/";

    @GET("characters")
    Single<RemoteMarvelContainer<CharacterResponse>> characters(@Query("name") String name);

    @GET("characters/{characterId}/comics")
    Single<RemoteMarvelContainer<ComicResponse>> comics(@Path("characterId") int characterId);

    @GET("characters/{characterId}/series")
    Single<RemoteMarvelContainer<SeriesResponse>> series(@Path("characterId") int characterId);
}
