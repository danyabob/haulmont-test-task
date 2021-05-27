/*
 * Copyright (c) 2021.
 * Danil Sheyukhin
 * danya.bob@gmail.com
 */

package com.danil.sheyukhin.haulmonttesttask.dao;

import java.util.List;

public interface Dao<T> {

    int create(T object);

    T getById(Integer id);

    List<T> getAll();

    void update(T object);

    void delete(Integer id);

}
