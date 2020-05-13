package com.practice.easyweather.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Adaptador que provee métodos de conveniencia para el acceso y manipulación de
 * la base de datos.
 */
public class CityAdapter {

    private DBHelper dbHelper;
    private SQLiteDatabase db;
    private Context context;

    public CityAdapter(Context context){
        this.context = context;
        openDB();
    }

    private void openDB(){
        dbHelper = new DBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    protected void finalize() throws Throwable {
        db.close();
        super.finalize();
    }

    /**
     * Retorna todas las ciudades guardadas en la base de datos.
     * @return Una colección ArrayList de Strings.
     */
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

    /**
     * Inserta una nueva ciudad en la base de datos.
     * @param cityName El nombre de la nueva ciudad.
     * @return Un booleano informando el éxito de la operación.
     */
    public boolean insertCityName(String cityName){

        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_NAME,cityName);
        long code = db.insert(DBHelper.TABLE_NAME,null,values);
        // Retorna un booleano informando el éxito de la operación
        return code > -1;

    } // fin insertCityName

    /**
     * Borra una ciudad de la base de datos.
     * @param cityName El nombre de la ciudad a borrar.
     * @return Un booleano informando el éxito de la operación.
     */
    public boolean deleteCityName(String cityName){

        int code = db.delete(DBHelper.TABLE_NAME,
                DBHelper.COLUMN_NAME + " = ? ",
                new String[] {cityName});
        // Retorna un booleano informando el éxito de la operación
        return code > 0;

    } // fin deleteCityName

}
