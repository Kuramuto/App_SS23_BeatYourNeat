package com.justingruenberg.beatyourneat.Model.DAO.DAOInterfaces;

import java.util.List;

public interface DAO <T> {

    T get(String name);
    List<T> getAll();
    boolean add(T model);
    boolean update(T model);
    boolean delete(T model);
}
