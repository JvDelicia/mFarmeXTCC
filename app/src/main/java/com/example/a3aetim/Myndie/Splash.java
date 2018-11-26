package com.example.a3aetim.Myndie;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.a3aetim.Myndie.Classes.User;
import com.example.a3aetim.Myndie.helper.DatabaseHelper;

public class Splash extends AppCompatActivity {
    public static String PREF_NAME = "Preferencias";
    SharedPreferences sp;
    Intent intent,i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        intent = new Intent(this, LoginActivity.class);
        i = new Intent(this, MainActivity.class);
        final Context co = this;
        carregar();
    }
    private boolean checkLoggedUser(){
        sp = getSharedPreferences(PREF_NAME,0);
        if(sp.getString("EmailLoggedUser","").length()==0){
            return false;
        }
        else {return true;}
    }
    private User getUser(){
        sp = getSharedPreferences(PREF_NAME,0);
        String emailUser = sp.getString("EmailLoggedUser","");
        DatabaseHelper helper = new DatabaseHelper(this);
        SQLiteDatabase db = helper.getReadableDatabase();
        String query = "SELECT * FROM User WHERE EmailUser =" + " '"+emailUser+"'";
        Cursor cursor = db.rawQuery(query,null);
        cursor.moveToFirst();
        User usuario = new User();
        for(int i = 0; i < cursor.getCount(); i++){
            int id = cursor.getInt(0);
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            String name = cursor.getString(3);
            String birth = cursor.getString(4);
            String email = emailUser;
            String pic = cursor.getString(6);
            int country = cursor.getInt(7);
            int type = cursor.getInt(8);
            String crt = cursor.getString(9);
            int idlang = cursor.getInt(10);
            int iddev = cursor.getInt(11);

            usuario = new User(id,birth,country,crt,email,iddev,idlang,username,name,password,pic,type);
        }
        return usuario;
    }
    @Override
    protected void onResume() {
        super.onResume();
        carregar();
    }

    public void carregar(){
        Thread thread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();
                }
                finally {
                    boolean chk = checkLoggedUser();
                    User user;
                    if(chk){
                        user = getUser();
                        i.putExtra("LoggedUser",user);
                        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                        startActivity(i);
                    }
                    else {
                        startActivity(intent);
                        finish();
                    }
                }
            }
        }; thread.start();
    }
}
