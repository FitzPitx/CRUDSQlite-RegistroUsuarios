package com.edu.uniempresarial.crud_registrousuarios.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.edu.uniempresarial.crud_registrousuarios.MainActivity;
import com.edu.uniempresarial.crud_registrousuarios.classes.User;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class UserDAO {
    private ManageDB manageDB;
    Context context;
    View view;
    User user;

    public static int document = 0;

    public UserDAO(Context context, View view) {
        this.context = context;
        this.view = view;
        manageDB = new ManageDB(context);
    }

    public void insertUser(User myUser) {
        SQLiteDatabase dbDuplicates = manageDB.getReadableDatabase();
        String query = "SELECT * FROM users WHERE usu_document = " + myUser.getDocument() + " AND usu_status = 0";

        //query para restaurar el registro y guardarlo en una variable para compararlo
        String queryDocument = "SELECT * FROM users WHERE usu_document = " + myUser.getDocument();
        Cursor cursorDocument = dbDuplicates.rawQuery(queryDocument, null);
        if (cursorDocument.moveToFirst()){
            document = cursorDocument.getInt(cursorDocument.getColumnIndex("usu_document"));
        }

        if (document == myUser.getDocument()){
            SQLiteDatabase db = manageDB.getWritableDatabase();
            if (db != null) {
                ContentValues values = new ContentValues();
                values.put("usu_document", myUser.getDocument());
                values.put("usu_user", myUser.getUser());
                values.put("usu_names", myUser.getNames());
                values.put("usu_last_names", myUser.getLastnames());
                values.put("usu_pass", myUser.getPass());
                values.put("usu_status", 1);
                long cant = db.update("users", values, "usu_document=" + myUser.getDocument(), null);
                if (cant > 0) {
                    Toast.makeText(context, "El registro ha sido restaurado", Toast.LENGTH_LONG).show();
                }
            }
            Cursor cursor = dbDuplicates.rawQuery(query, null);
            if (cursor.moveToFirst()) {
                do {
                    user = new User();
                    user.setDocument(cursor.getInt(0));
                    user.setUser(cursor.getString(1));
                    user.setNames(cursor.getString(2));
                    user.setLastnames(cursor.getString(3));
                    user.setPass(cursor.getString(4));
                    user.setStatus(cursor.getInt(5));
                } while (cursor.moveToNext());
            }
        } else {
            try {
                SQLiteDatabase db = manageDB.getWritableDatabase();
                if (db != null) {
                    ContentValues values = new ContentValues();
                    values.put("usu_document", myUser.getDocument());
                    values.put("usu_user", myUser.getUser());
                    values.put("usu_names", myUser.getNames());
                    values.put("usu_last_names", myUser.getLastnames());
                    values.put("usu_pass", myUser.getPass());
                    values.put("usu_status", 1);
                    long cod = db.insert("users", null, values);
                    Snackbar.make(view, "Usuario registrado con exito: " + cod, Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(view, "Error al registrar usuario: ", Snackbar.LENGTH_LONG).show();
                }
            } catch (SQLException e) {
                Log.i("ERROR", "ERROR AL REGISTRAR USUARIO: " + e.getMessage());
            }
        }
    }

    public ArrayList<User> getUserList() {
        SQLiteDatabase db = manageDB.getReadableDatabase();
        String query = "SELECT * FROM users WHERE usu_status = 1";
        ArrayList<User> userList = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setDocument(cursor.getInt(0));
                user.setUser(cursor.getString(1));
                user.setNames(cursor.getString(2));
                user.setLastnames(cursor.getString(3));
                user.setPass(cursor.getString(4));
                user.setStatus(cursor.getInt(5));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }

    public void updateDB(User myUser) {
        SQLiteDatabase db = manageDB.getWritableDatabase();
        if (db != null) {
            ContentValues values = new ContentValues();
            values = MainActivity.contentValues;
            long cant = db.update("users", values, "usu_document=" + myUser.getDocument(), null);
            if (cant > 0) {
                Toast.makeText(context, "El registro ha sido actualizado", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "El registro no fue encontrado", Toast.LENGTH_LONG).show();
            }
        }

    }

    public void changeStatus(User myUser) {
        SQLiteDatabase db = manageDB.getWritableDatabase();
        if (db != null) {
            ContentValues values = new ContentValues();
            values.put("usu_status", 0);
            long cant = db.update("users", values, "usu_document=" + myUser.getDocument(), null);
            if (cant > 0) {
                Toast.makeText(context, "El registro ha sido eliminado", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(context, "El registro no fue encontrado", Toast.LENGTH_LONG).show();
            }
        }
    }

}
