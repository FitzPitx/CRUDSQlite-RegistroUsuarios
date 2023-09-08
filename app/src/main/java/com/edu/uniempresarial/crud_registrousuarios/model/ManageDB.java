package com.edu.uniempresarial.crud_registrousuarios.model;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ManageDB extends SQLiteOpenHelper {

    private static final String DATA_BASE_USERS = "dbusuarios.db";
    private static final int VERSION = 1;
    private static final String TABLE_USERS = "users";

    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_USERS + "(usu_document INTEGER PRIMARY KEY," +
            "usu_user varchar(35) NOT NULL," +
            "usu_names varchar(100) NOT NULL," +
            "usu_last_names varchar(100) NOT NULL," +
            "usu_pass varchar(20) NOT NULL," +
            "usu_status INTEGER CHECK(usu_status IN (0,1)) NOT NULL);" ;

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_USERS;
    public ManageDB(Context context) {
        super(context, DATA_BASE_USERS, null, VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        Log.i("DATABASE", "DATABASE CREATED"+CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }
}
