package com.justingruenberg.beatyourneat.Model.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.justingruenberg.beatyourneat.Model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "BeatYourNeat.db";
    private static final int DATABASE_VERSION = 1;

    private static final String USER_TABLE = "user_table";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";

    private static final String PROFILE_TABLE = "profile_table";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_HEIGHT = "height";
    private static final String COLUMN_BIRTHDATE = "birthdate";
    private static final String COLUMN_WEIGHT = "weight";
    private static final String COLUMN_PROFILE_USERNAME = "username";

    private static final String WEIGHT_TABLE = "weight_table";
    private static final String COLUMN_WEIGHTS = "weights";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_WEIGHT_USERNAME = "username";


    public DBHelper(Context context) {
        super(context,DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //Create User table
        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" +
                COLUMN_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_PASSWORD + " TEXT" +
                ")";
        sqLiteDatabase.execSQL(createTableStatement);

        // Create Profile table
        String createProfileTableQuery = "CREATE TABLE " + PROFILE_TABLE + "(" +
                COLUMN_PROFILE_USERNAME + " TEXT PRIMARY KEY, " +
                COLUMN_WEIGHT + " REAL, " +
                COLUMN_HEIGHT + " INTEGER, " +
                COLUMN_BIRTHDATE + " TEXT, " +
                COLUMN_GENDER + " TEXT, " +
                "FOREIGN KEY(" + COLUMN_PROFILE_USERNAME + ") REFERENCES " + USER_TABLE + "(" + COLUMN_USERNAME + ")" + " ON DELETE CASCADE" +
                ")";
        sqLiteDatabase.execSQL(createProfileTableQuery);

        // Create Weight table
        String createWeightTableQuery = "CREATE TABLE " + WEIGHT_TABLE + "(" +
                COLUMN_WEIGHT_USERNAME + " TEXT, " +
                COLUMN_DATE + " TEXT PRIMARY KEY, " +
                COLUMN_WEIGHTS + " REAL, " +
                "FOREIGN KEY(" + COLUMN_WEIGHT_USERNAME + ") REFERENCES " + USER_TABLE + "(" + COLUMN_USERNAME + ")" + " ON DELETE CASCADE" +
                ")";
        sqLiteDatabase.execSQL(createWeightTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        String dropTableStatement = "DROP TABLE IF EXISTS " + USER_TABLE;
        sqLiteDatabase.execSQL(dropTableStatement);
    }

    public String getUserTable(){
        return USER_TABLE;
    }

    public String getColumnUsername(){
        return COLUMN_USERNAME;
    }

    public String getColumnPassword(){
        return COLUMN_PASSWORD;
    }

    public String getProfileTable(){
        return PROFILE_TABLE;
    }

    public String getColumnGender(){
        return COLUMN_GENDER;
    }

    public String getColumnHeight(){
        return COLUMN_HEIGHT;
    }

    public String getColumnBirthdate(){
        return COLUMN_BIRTHDATE;
    }

    public String getColumnWeight(){
        return COLUMN_WEIGHT;
    }

    public String getColumnProfileUsername(){
        return COLUMN_PROFILE_USERNAME;
    }

    public String getWeightTable(){
        return WEIGHT_TABLE;
    }

    public String getColumnWeights(){
        return COLUMN_WEIGHTS;
    }

    public String getColumnDate(){
        return COLUMN_DATE;
    }

    public String getColumnWeightUsername(){
        return COLUMN_WEIGHT_USERNAME;
    }







}
