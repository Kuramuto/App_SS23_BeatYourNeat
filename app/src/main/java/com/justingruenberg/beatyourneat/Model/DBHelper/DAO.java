package com.justingruenberg.beatyourneat.Model.DBHelper;

import java.util.List;

public interface DAO <T> {

    T get(String name);
    List<T> getAll();
    boolean add(T model);
    boolean update(T model);
    boolean delete(T model);
}
