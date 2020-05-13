package com.practice.easyweather.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper que se encarga de crear y actualizar la base de datos interna de
 * la aplicación.
 */
public class DBHelper extends SQLiteOpenHelper {

    // Acceso DEFAULT para poder acceder a estas constanstes desde el mismo paquete
    static final String DB_NAME = "citynames.db",
                                TABLE_NAME = "cities",
                                COLUMN_NAME = "city_name",
                                DEFAULT_FIRST_CITY = "Buenos Aires" ;
    private static final int DB_VERSION = 1;

    public DBHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABLE_NAME +
                " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT)";
        db.execSQL(sql);
        // Inserta una primera ciudad
        db.execSQL("INSERT INTO " + TABLE_NAME + " (" + COLUMN_NAME +
                ") VALUES ('" + DEFAULT_FIRST_CITY + "')");

    } // fon onCreate

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if(newVersion > oldVersion){
            upgrateToNewVersion();
        }

    } // fin onUpgrade

    private void upgrateToNewVersion(){
        // Todo el código correspondiente a la nueva versión va en este método
    }

}
