package edu.ch03.music;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class UserDao {
    private SQLiteHelper helper;

    public UserDao(Context context) {
        helper = new SQLiteHelper(context);
    }

    public void insert(User psw) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", psw.getName());
        values.put("password", psw.getPassword());
        values.put("phone", psw.getPhone());
        db.insert("secret", null, values);
        db.close();
    }

    public void delete(String name) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.delete("secret", "name=?", new String[]{name});
        db.close();
    }

    public int update(User password) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", password.getName());
        values.put("password", password.getPassword());
        values.put("phone", password.getPhone());
        int count = db.update("secret", values, "name=?",
                new String[]{password.getName()});
        db.close();
        return count;
    }

    public List<User> queryAll() {
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("secret", new String[]{"name", "password",
                "phone"}, null, null, null, null, null);
        List<User> list = new ArrayList<User>();
        while (c.moveToNext()) {
            @SuppressLint("Range") String name = c.getString(c.getColumnIndex("name"));
            @SuppressLint("Range") String psw = c.getString(c.getColumnIndex("password"));
            @SuppressLint("Range") String phone = c.getString(c.getColumnIndex("phone"));
            User password = new User(name, psw, phone);
            list.add(password);
        }
        c.close();
        db.close();
        return list;
    }

    @SuppressLint("Range")
    public String findData(String name, String data) {
        String phone = "";
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor c = db.query("secret", null, "name=?", new String[]{name},
                null, null, null);
        while (c.moveToNext()) {
            phone = c.getString(c.getColumnIndex(data));
        }
        c.close();
        db.close();
        return phone;
    }

    public void deleteAll() {
        SQLiteDatabase db = helper.getWritableDatabase();
        String sql = "DELETE FROM secret;";
        db.execSQL(sql);
        db.close();
    }
}

