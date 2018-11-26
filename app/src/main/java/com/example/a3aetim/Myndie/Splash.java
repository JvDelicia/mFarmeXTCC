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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;

public class Splash extends AppCompatActivity {
    public static String PREF_NAME = "Preferencias";
    User loggedUser;
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
        loggedUser = new User();
        String FILENAME = "logged_user";
        File file =getFileStreamPath(FILENAME);
        try{
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);
            loggedUser = (User) ois.readObject();
            fis.close();
            ois.close();
        }
        catch (FileNotFoundException fnfe){
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return true;
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
                        i.putExtra("LoggedUser",loggedUser);
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
