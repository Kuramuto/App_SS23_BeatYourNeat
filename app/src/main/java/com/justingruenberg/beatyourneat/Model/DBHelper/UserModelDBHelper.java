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

public class UserModelDBHelper extends SQLiteOpenHelper implements UserModelDAO {

    private static final String DATABASE_NAME = "userTable.db";
    private static final int DATABASE_VERSION = 1;

    private static final String USER_TABLE = "user_table";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_INITIALIZED = "initialized";


    public UserModelDBHelper(Context context) {
        super(context,DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT, " +
                COLUMN_INITIALIZED + " INTEGER" +
                ")";
        sqLiteDatabase.execSQL(createTableStatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropTableStatement = "DROP TABLE IF EXISTS " + USER_TABLE;
        sqLiteDatabase.execSQL(dropTableStatement);
    }


    private boolean getIntAsBoolean(int value) {
        return value != 0;
    }

    @Override
    public UserModel get(String userName) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_INITIALIZED};
        String whereClause = COLUMN_USERNAME + " = ?";
        String[] whereArgs = {userName};
        Cursor cursor = db.query(USER_TABLE, columns, whereClause, whereArgs, null, null, null);
        UserModel user = null;
        if(cursor.moveToFirst()){
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            @SuppressLint("Range") int initializedInt = cursor.getInt(cursor.getColumnIndex(COLUMN_INITIALIZED));
            boolean initialized = getIntAsBoolean(initializedInt);
            user = new UserModel(userName, password, initialized);
        }
        cursor.close();
        db.close();
        return user;
    }

    @Override
    public List<UserModel> getAll() {
        SQLiteDatabase db = getReadableDatabase();
        List<UserModel> userList = new ArrayList<>();
        String[] columns = {COLUMN_USERNAME, COLUMN_PASSWORD, COLUMN_INITIALIZED};
        Cursor cursor = db.query(USER_TABLE, columns, null, null, null, null, null);
        while (cursor.moveToNext()){
            @SuppressLint("Range") String username = cursor.getString(cursor.getColumnIndex(COLUMN_USERNAME));
            @SuppressLint("Range") String password = cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD));
            @SuppressLint("Range") int initializedInt = cursor.getInt(cursor.getColumnIndex(COLUMN_INITIALIZED));
            userList.add(new UserModel(username, password, getIntAsBoolean(initializedInt)));
        }
        cursor.close();
        return userList;
    }

    @Override
    public boolean add(UserModel model) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, model.getUserName());
        cv.put(COLUMN_PASSWORD, model.getPassword());
        cv.put(COLUMN_INITIALIZED, model.isInitialized());
        long result = db.insert(USER_TABLE, null, cv);
        return (result != -1);
    }

    @Override
    public boolean update(UserModel model) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_PASSWORD, model.getPassword());
        values.put(COLUMN_INITIALIZED, model.isInitialized() ? 1 : 0);

        String whereClause = COLUMN_USERNAME + " = ?";
        String[] whereArgs = {model.getUserName()};
        long result = db.update(USER_TABLE, values, whereClause, whereArgs);
        db.close();
        return (result != -1);
    }

    @Override
    public boolean delete(UserModel model) {
        SQLiteDatabase db = getWritableDatabase();
        String whereClause = COLUMN_USERNAME + " = ?";
        String[] whereArgs = {model.getUserName()};
        int count = db.delete(USER_TABLE, whereClause, whereArgs);
        return (count > 0);
    }

    @Override
    public boolean userExists(String username) {
        SQLiteDatabase db = getReadableDatabase();
        String[] columns = {COLUMN_USERNAME};
        String whereClause = COLUMN_USERNAME + " = ?";
        String[] whereArgs = {username};
        Cursor cursor = db.query(USER_TABLE, columns, whereClause, whereArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return (count > 0);
    }
}
