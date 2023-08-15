package com.justingruenberg.beatyourneat.Model.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.justingruenberg.beatyourneat.Model.DAO.DAOInterfaces.UserModelDAO;
import com.justingruenberg.beatyourneat.Model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserDAO implements UserModelDAO {

    private DBHelper dbHelper;
    private ProfileDAO profileDAO;

    public UserDAO(Context context) {
        this.dbHelper = new DBHelper(context);
        this.profileDAO = new ProfileDAO(context);
    }

    private boolean getIntAsBoolean(int value) {
        return value != 0;
    }

    @Override
    public UserModel get(String userName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {dbHelper.getColumnUsername(), dbHelper.getColumnPassword()};
        String whereClause = dbHelper.getColumnUsername() + " = ?";
        String[] whereArgs = {userName};
        Cursor cursor = db.query(dbHelper.getUserTable(), columns, whereClause, whereArgs, null, null, null);
        UserModel user = null;
        if(cursor.moveToFirst()){
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(dbHelper.getColumnPassword()));
            user = new UserModel(userName, password, profileDAO.get(userName));
        }
        cursor.close();
        db.close();
        return user;
    }

    @Override
    public List<UserModel> getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<UserModel> userList = new ArrayList<>();
        String[] columns = {dbHelper.getColumnUsername(), dbHelper.getColumnPassword()};
        Cursor cursor = db.query(dbHelper.getUserTable(), columns, null, null, null, null, null);
        while (cursor.moveToNext()){
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(dbHelper.getColumnUsername()));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(dbHelper.getColumnPassword()));

            userList.add(new UserModel(username, password, profileDAO.get(username)));
        }
        cursor.close();
        return userList;
    }

    @Override
    public boolean add(UserModel model) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.getColumnUsername(), model.getUserName());
        cv.put(dbHelper.getColumnPassword(), model.getPassword());
        long result = db.insert(dbHelper.getUserTable(), null, cv);
        return (result != -1);
    }

    @Override
    public boolean update(UserModel model) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbHelper.getColumnPassword(), model.getPassword());

        String whereClause = dbHelper.getColumnUsername() + " = ?";
        String[] whereArgs = {model.getUserName()};
        long result = db.update(dbHelper.getUserTable(), values, whereClause, whereArgs);
        db.close();
        return (result != -1);
    }

    @Override
    public boolean delete(UserModel model) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String whereClause = dbHelper.getColumnUsername() + " = ?";
        String[] whereArgs = {model.getUserName()};
        int count = db.delete(dbHelper.getUserTable(), whereClause, whereArgs);
        return (count > 0);
    }

    @Override
    public boolean userExists(String username) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {dbHelper.getColumnUsername()};
        String whereClause = dbHelper.getColumnUsername() + " = ?";
        String[] whereArgs = {username};
        Cursor cursor = db.query(dbHelper.getUserTable(), columns, whereClause, whereArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return (count > 0);
    }

    public boolean updateAllUsernames(String oldUsername, String newUsername) {

        if (userExists(newUsername)) {
            return false;
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        boolean isSuccess = true;

        db.beginTransaction();

        try {

            ContentValues userValues = new ContentValues();
            userValues.put(dbHelper.getColumnUsername(), newUsername);
            String userWhereClause = dbHelper.getColumnUsername() + " = ?";
            int userRowsAffected = db.update(dbHelper.getUserTable(), userValues, userWhereClause, new String[]{oldUsername});

            ContentValues profileValues = new ContentValues();
            profileValues.put(dbHelper.getColumnProfileUsername(), newUsername);
            String profileWhereClause = dbHelper.getColumnProfileUsername() + " = ?";
            int profileRowsAffected = db.update(dbHelper.getProfileTable(), profileValues, profileWhereClause, new String[]{oldUsername});


            ContentValues weightValues = new ContentValues();
            weightValues.put(dbHelper.getColumnWeightUsername(), newUsername);
            String weightWhereClause = dbHelper.getColumnWeightUsername() + " = ?";
            int weightRowsAffected = db.update(dbHelper.getWeightTable(), weightValues, weightWhereClause, new String[]{oldUsername});


            if (userRowsAffected > 0 && profileRowsAffected > 0 && weightRowsAffected > 0) {
                db.setTransactionSuccessful();
            } else {
                isSuccess = false;
            }
        } catch (Exception e) {
            isSuccess = false;
        } finally {
            db.endTransaction();
        }

        db.close();
        return isSuccess;
    }


}
