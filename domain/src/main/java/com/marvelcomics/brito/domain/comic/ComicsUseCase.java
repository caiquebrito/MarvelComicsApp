package com.marvelcomics.brito.domain.comic;

import com.marvelcomics.brito.data.mapper.ComicEntityMapper;
import com.marvelcomics.brito.data.repository.comics.ComicRepository;
import com.marvelcomics.brito.domain.UseCase;
import com.marvelcomics.brito.entity.ComicEntity;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ComicsUseCase extends UseCase<Integer, List<ComicEntity>> {

    private ComicRepository comicRepository;

    @Inject
    protected ComicEntityMapper comicEntityMapper;

    @Inject
    public ComicsUseCase(ComicRepository comicRepository) {
        this.comicRepository = comicRepository;
    }

    @Override
    protected Observable<List<ComicEntity>> createObservable(Integer data) {
        return comicRepository.comics(data).map(comicEntityMapper::transform);
    }
}
