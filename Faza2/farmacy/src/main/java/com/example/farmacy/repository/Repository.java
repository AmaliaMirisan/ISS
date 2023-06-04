package com.example.farmacy.repository;

public interface Repository<E, ID> {
    void add(E entity) throws RepoException;
    void delete(ID id) throws RepoException;
    E findOne(ID id) throws RepoException;
    Iterable<E> findAll();
}
