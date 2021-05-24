package com.danil.sheyukhin.haulmonttesttask.dao;

import com.danil.sheyukhin.haulmonttesttask.entities.Entity;

import java.util.List;

public interface Dao<T extends Entity> {

    int create(T object);

    T getById(Integer id);

    List<T> getAll();

    void update(T object);

    void delete(Integer id);

}
