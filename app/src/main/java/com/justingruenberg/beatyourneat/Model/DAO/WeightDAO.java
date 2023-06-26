package com.justingruenberg.beatyourneat.Model.DAO;

import android.content.ContentValues;
import android.content.Context;
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
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.getColumnWeights(), model.getWeight());
        cv.put(dbHelper.getColumnDate(), model.getDate());

        String whereClause = dbHelper.getColumnDate() + " = ?";
        String[] whereArgs = {model.getDate()};
        long result = db.update(dbHelper.getWeightTable(), cv, whereClause, whereArgs);
        return (result != -1);
    }

    @Override
    public boolean delete(WeightModel model) {
        return false;
    }
}
