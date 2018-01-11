package com.marvelcomics.brito.data.datasource.local;

public interface LocalDataSource<T> {
    void insert(T data);
    T getById(long id);
    Iterable<T> getAll();
    void delete(T data);
    void update(T data);
}
