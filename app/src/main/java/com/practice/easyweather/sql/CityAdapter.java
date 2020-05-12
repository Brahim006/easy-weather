package com.practice.easyweather.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class CityAdapter {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    public CityAdapter(Context context){
        this.context = context;
    }

    public void openDB(){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void closeDB(){
        db.close();
    }

    public String getCityNameByID(int id){

        Cursor c = db.query(DBHelper.TABLE_NAME,
                new String[]{DBHelper.COLUMN_NAME},
                "id = ?",new String[]{String.valueOf(id)},
                null, null,null);
        // Si hubo coincidencias
        if(c.moveToFirst()){
            int index = c.getColumnIndex(DBHelper.COLUMN_NAME);
            return c.getString(index);
        } else
            return null;

    } // fin getCityNameById

    public ArrayList<String> getAllCityNames(){

        Cursor c = db.query(DBHelper.TABLE_NAME,
                new String[]{DBHelper.COLUMN_NAME},
                null, null,
                null, null , DBHelper.COLUMN_NAME);
        // Verifica si hubo coincidencias
        if(!c.moveToFirst()){
            return null;
        } else {

            ArrayList<String> ret = new ArrayList<>();
            // Almacena el primero
            ret.add(c.getString(c.getColumnIndex(DBHelper.COLUMN_NAME)));
            // Procede a obtener los demás
            while(c.moveToNext()){
                ret.add(c.getString(c.getColumnIndex(DBHelper.COLUMN_NAME)));
            }
            return ret;
        }

    } // fin getAllCityNames

    public boolean insertCityName(String cityName){

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME,cityName);
        long code = db.insert(DBHelper.TABLE_NAME,null,values);
        // Retorna un booleano informando el éxito de la operación
        return code > -1;

    } // fin insertCityName

    public boolean deleteCityName(String cityName){

        int code = db.delete(DBHelper.TABLE_NAME,
                DBHelper.COLUMN_NAME + " = ? ",
                new String[] {cityName});
        // Retorna un booleano informando el éxito de la operación
        return code > 0;

    } // fin deleteCityName

}
