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

    public UserDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }

    private boolean getIntAsBoolean(int value) {
        return value != 0;
    }

    @Override
    public UserModel get(String userName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {dbHelper.getColumnUsername(), dbHelper.getColumnPassword(), dbHelper.getColumnInitialized()};
        String whereClause = dbHelper.getColumnUsername() + " = ?";
        String[] whereArgs = {userName};
        Cursor cursor = db.query(dbHelper.getUserTable(), columns, whereClause, whereArgs, null, null, null);
        UserModel user = null;
        if(cursor.moveToFirst()){
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(dbHelper.getColumnPassword()));
            @SuppressLint("Range") int initializedInt = cursor.getInt(cursor.getColumnIndex(dbHelper.getColumnInitialized()));
            boolean initialized = getIntAsBoolean(initializedInt);
            user = new UserModel(userName, password, initialized);
        }
        cursor.close();
        db.close();
        return user;
    }

    @Override
    public List<UserModel> getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<UserModel> userList = new ArrayList<>();
        String[] columns = {dbHelper.getColumnUsername(), dbHelper.getColumnPassword(), dbHelper.getColumnInitialized()};
        Cursor cursor = db.query(dbHelper.getUserTable(), columns, null, null, null, null, null);
        while (cursor.moveToNext()){
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(dbHelper.getColumnUsername()));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(dbHelper.getColumnPassword()));
            @SuppressLint("Range") int initializedInt = cursor.getInt(cursor.getColumnIndex(dbHelper.getColumnInitialized()));
            userList.add(new UserModel(username, password, getIntAsBoolean(initializedInt)));
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
        cv.put(dbHelper.getColumnInitialized(), model.isInitialized());
        long result = db.insert(dbHelper.getUserTable(), null, cv);
        return (result != -1);
    }

    @Override
    public boolean update(UserModel model) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbHelper.getColumnPassword(), model.getPassword());
        values.put(dbHelper.getColumnInitialized(), model.isInitialized() ? 1 : 0);

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
}
