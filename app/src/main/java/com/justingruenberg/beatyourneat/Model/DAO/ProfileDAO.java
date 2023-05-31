package com.justingruenberg.beatyourneat.Model.DAO;


import android.annotation.SuppressLint;
import android.content.ContentValues;
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
        ProfileModel userProfile = null;
        if(cursor != null && cursor.moveToFirst()){
           @SuppressLint("Range") String gender = cursor.getString(cursor.getColumnIndex(dbHelper.getColumnGender()));
           @SuppressLint("Range") String birthdate = cursor.getString(cursor.getColumnIndex(dbHelper.getColumnBirthdate()));
           @SuppressLint("Range") int height = cursor.getInt(cursor.getColumnIndex(dbHelper.getColumnHeight()));
           @SuppressLint("Range") double weight = cursor.getDouble(cursor.getColumnIndex(dbHelper.getColumnWeight()));
            userProfile = new ProfileModel(gender, height, birthdate, weight, username);
            cursor.close();
            return userProfile;
        }
        return null;
    }

    @Override
    public List<ProfileModel> getAll() {
        return null;
    }

    @Override
    public boolean add(ProfileModel model) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.getColumnBirthdate(), model.getBirthdate());
        cv.put(dbHelper.getColumnGender(), model.getGender());
        cv.put(dbHelper.getColumnHeight(), model.getHeight());
        cv.put(dbHelper.getColumnWeight(), model.getWeight());
        cv.put(dbHelper.getColumnProfileUsername(), model.getUsername());
        long result = db.insert(dbHelper.getProfileTable(), null, cv);
        return (result != -1);
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
