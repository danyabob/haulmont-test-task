package com.danil.sheyukhin.haulmonttesttask.dao;

import com.danil.sheyukhin.haulmonttesttask.entities.Entity;

import java.util.List;

public interface Dao<T extends Entity> {

    T getById(Integer id);

    List<T> getAll();

    int create(T object);

    void update(T object);

    void delete(Integer id);

}
