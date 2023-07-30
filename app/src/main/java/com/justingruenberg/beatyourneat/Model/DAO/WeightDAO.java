package com.justingruenberg.beatyourneat.Model.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.justingruenberg.beatyourneat.Model.DAO.DAOInterfaces.WeightModelDAO;
import com.justingruenberg.beatyourneat.Model.WeightModel;

import java.util.List;

public class WeightDAO implements WeightModelDAO {

   DBHelper dbHelper;
   UserDAO userDAO;

   public WeightDAO(Context context){
       dbHelper = new DBHelper(context);
       userDAO = new UserDAO(context);
   }

    @Override
    public WeightModel get(String date) {
       return null;
    }

    @Override
    public List<WeightModel> getAll() {
        return null;
    }

    @Override
    public boolean add(WeightModel model) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.getColumnWeights(), model.getWeight());
        cv.put(dbHelper.getColumnDate(), model.getDate());
        cv.put(dbHelper.getColumnWeightUsername(), model.getUserModel().getUserName());
        long result = db.insert(dbHelper.getWeightTable(), null, cv);
        return (result != -1);
    }

    @Override
    public boolean update(WeightModel model) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbHelper.getColumnWeightUsername(), model.getUserModel().getUserName());
        values.put(dbHelper.getColumnWeights(), model.getWeight());
        values.put(dbHelper.getColumnDate(), model.getDate());

        String whereClause = dbHelper.getColumnWeightUsername() + " = ? AND " + dbHelper.getColumnDate() + " = ?";
        String[] whereArgs = {model.getUserModel().getUserName(), model.getDate()};
        long result = db.update(dbHelper.getWeightTable(), values, whereClause, whereArgs);
        return (result != -1);
    }

    @Override
    public boolean delete(WeightModel model) {
        return false;
    }

    @Override
    public boolean dateForUserExists(WeightModel weightModel) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] columns = {dbHelper.getColumnWeightUsername(), dbHelper.getColumnWeights(), dbHelper.getColumnDate()};
        String whereClause = dbHelper.getColumnDate() + " = ? AND " + dbHelper.getColumnWeightUsername() + " = ?";
        String[] whereArgs = {weightModel.getDate(), weightModel.getUserModel().getUserName(),};
        Cursor cursor = db.query(dbHelper.getWeightTable(), columns, whereClause, whereArgs, null, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        return (count > 0);
    }
}
