package com.justingruenberg.beatyourneat.Model.DAO;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.justingruenberg.beatyourneat.Model.DAO.DAOInterfaces.ProfileModelDAO;
import com.justingruenberg.beatyourneat.Model.ProfileModel;

import java.util.List;

public class ProfileDAO implements ProfileModelDAO {

    private DBHelper dbHelper;

    public ProfileDAO(Context context){
        this.dbHelper = new DBHelper(context);
    }


    @Override
    public ProfileModel get(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {dbHelper.getColumnProfileUsername(), dbHelper.getColumnBirthdate(), dbHelper.getColumnHeight(), dbHelper.getColumnWeight(), dbHelper.getColumnGender()};
        String whereClause = dbHelper.getColumnProfileUsername() + " = ?";
        String [] whereArgs = {username};
        Cursor cursor = db.query(dbHelper.getProfileTable(), columns, whereClause, whereArgs, null, null, null);


        return null;
    }

    @Override
    public List<ProfileModel> getAll() {
        return null;
    }

    @Override
    public boolean add(ProfileModel model) {
        return false;
    }

    @Override
    public boolean update(ProfileModel model) {
        return false;
    }

    @Override
    public boolean delete(ProfileModel model) {
        return false;
    }
}
