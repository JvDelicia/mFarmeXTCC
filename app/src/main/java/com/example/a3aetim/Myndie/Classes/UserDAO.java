package com.example.a3aetim.Myndie.Classes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.a3aetim.Myndie.helper.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAO {

    DatabaseHelper helper;
    List<Map<String, Object>> users;

    public UserDAO(Context context) {
        this.helper = new DatabaseHelper(context);
    }

    public List<Map<String, Object>> listarUsuarios(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select _IdUser, LoginUser, PassUser, NameUser, BirthUser, EmailUser, PicUser, CountryUser, TypeUser, CrtDateUser, IdLang, IdDev from User", null);
        cursor.moveToFirst();
        users = new ArrayList<Map<String, Object>>();
        for(int i =0; i<cursor.getCount(); i++){
            Map<String,Object> item = new HashMap<String, Object>();
            String id = cursor.getString(0);
            String login = cursor.getString(1);
            String pass = cursor.getString(2);
            String name = cursor.getString(3);
            String birth = cursor.getString(4);
            String email = cursor.getString(5);
            String pic = cursor.getString(6);
            String country = cursor.getString(7);
            String type = cursor.getString(8);
            String crtdate = cursor.getString(9);
            String idlang = cursor.getString(10);
            String iddev = cursor.getString(11);
            item.put("_IdUser", id);
            item.put("LoginUser", login);
            item.put("PassUser", pass);
            item.put("NameUser", name);
            item.put("BirthUser", birth);
            item.put("EmailUser", email);
            item.put("PicUser", pic);
            item.put("CountryUser", country);
            item.put("TypeUser", type);
            item.put("CrtDateUser", crtdate);
            item.put("IdLang", idlang);
            item.put("IdDev", iddev);
            users.add(item);
            cursor.moveToNext();
        }
        return users;
    }

}
