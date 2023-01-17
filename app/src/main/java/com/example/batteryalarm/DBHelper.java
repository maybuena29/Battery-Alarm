package com.example.batteryalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context) {
        super(context, "user_database.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE tbluserdata(user_id INTEGER PRIMARY KEY AUTOINCREMENT, user_name TEXT NOT NULL, user_address TEXT, user_contact TEXT, user_username TEXT, user_password TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS tbluserdata");
    }

    public Boolean insertUserData(String name, String address, String contact, String username, String password)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("user_name", name);
        contentValues.put("user_address", address);
        contentValues.put("user_contact", contact);
        contentValues.put("user_username", username);
        contentValues.put("user_password", password);

        Cursor cursor = DB.rawQuery("SELECT * FROM tbluserdata WHERE user_username = ?", new String[]{username});

        if(cursor.getCount() > 0){
            return false;
        }else{
            long result = DB.insert("tbluserdata", null, contentValues);
            if(result == -1){
                return false;
            }else{
                return true;
            }
        }
    }

    public Boolean updateUserData(String id, String name, String address, String contact, String username, String password)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("address", address);
        contentValues.put("contact", contact);
        contentValues.put("username", username);
        contentValues.put("password", password);

        Cursor cursor = DB.rawQuery("SELECT * FROM tbluserdata WHERE user_id = ?", new String[]{id});
        if (cursor.getCount() > 0) {
            long result = DB.update("tbluserdata", contentValues, "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deleteUserData(String id)
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM tbluserdata WHERE user_id = ?", new String[]{id});

        if (cursor.getCount() > 0) {
            long result = DB.delete("tbluserdata", "user_id=?", new String[]{id});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getUserData()
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM tbluserdata", null);
        return cursor;
    }

    public boolean checkUserCredentials(String username, String password) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("SELECT * FROM tbluserdata WHERE user_username = ? AND user_password = ?", new String[]{username,password});

        if (cursor.moveToFirst()) {
            return true;
        }

        if(cursor!=null) {
            cursor.close();
        }
        DB.close();
        return false;
    }

}













