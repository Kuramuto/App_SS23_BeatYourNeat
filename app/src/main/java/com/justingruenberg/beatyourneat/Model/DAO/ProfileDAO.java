package com.justingruenberg.beatyourneat.Model.DAO;


import android.content.Context;

import com.justingruenberg.beatyourneat.Model.DAO.DAOInterfaces.ProfileModelDAO;

import java.util.List;

public class ProfileDAO implements ProfileModelDAO {

    private DBHelper dbHelper;

    public ProfileDAO(Context context){
        this.dbHelper = new DBHelper(context);
    }


    @Override
    public ProfileDAO get(String name) {
        return null;
    }

    @Override
    public List<ProfileDAO> getAll() {
        return null;
    }

    @Override
    public boolean add(ProfileDAO model) {
        return false;
    }

    @Override
    public boolean update(ProfileDAO model) {
        return false;
    }

    @Override
    public boolean delete(ProfileDAO model) {
        return false;
    }
}
