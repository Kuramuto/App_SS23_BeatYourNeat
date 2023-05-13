package com.justingruenberg.beatyourneat.Model.DBHelper;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.justingruenberg.beatyourneat.Model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class UserModelDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "userTable.db";
    private static final int DATABASE_VERSION = 1;

    private static final String USER_TABLE = "user_table";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";




    public UserModelDBHelper(Context context) {
        super(context,DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropTableStatement = "DROP TABLE IF EXISTS " + USER_TABLE;
        sqLiteDatabase.execSQL(dropTableStatement);
    }

    public boolean addUser(UserModel user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, user.getUserName());
        values.put(COLUMN_PASSWORD, user.getPassword());
        long result = db.insert(USER_TABLE, null, values);
        return (result != -1);
    }

    public UserModel getUser(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_USERNAME, COLUMN_PASSWORD};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(USER_TABLE, columns, selection, selectionArgs, null, null, null);
        UserModel user = null;
        if (cursor.moveToFirst()) {
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            user = new UserModel(username, password);
        }
        cursor.close();
        return user;
    }

    public List<UserModel> getAllUsers() {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_USERNAME, COLUMN_PASSWORD};
        Cursor cursor = db.query(USER_TABLE, columns, null, null, null, null, null);
        List<UserModel> users = new ArrayList<>();
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            UserModel user = new UserModel(username, password);
            users.add(user);
        }
        cursor.close();
        return users;
    }

    public boolean updateUser(UserModel user) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, user.getPassword());
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {user.getUserName()};
        int count = db.update(USER_TABLE, values, selection, selectionArgs);
        return (count > 0);
    }

    public boolean deleteUser(String username) {
        SQLiteDatabase db = getWritableDatabase();
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        int count = db.delete(USER_TABLE, selection, selectionArgs);
        return (count > 0);
    }

    public boolean checkUserExists(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_USERNAME};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(USER_TABLE, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return (count > 0);
    }

}
