package com.example.a3aetim.Myndie.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context,"dbMyndie",null , 1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE TypeApp(_IdTypeApp INTEGER PRIMARY KEY AUTOINCREMENT, NameTypeApp TEXT)");

        db.execSQL("CREATE TABLE Pegi(_IdPegi INTEGER PRIMARY KEY AUTOINCREMENT, PhotoPathPegi TEXT, OldPegi TEXT, DescPegi TEXT)");

        db.execSQL("CREATE TABLE Developer(_IdDev INTEGER PRIMARY KEY AUTOINCREMENT, InfoDev TEXT, NumSoftDev INTEGER, NameDev TEXT);");

        db.execSQL("CREATE TABLE Country(_IdCountry INTEGER PRIMARY KEY AUTOINCREMENT, NameCountry TEXT);");

        db.execSQL("CREATE TABLE Language(_IdLang INTEGER PRIMARY KEY AUTOINCREMENT, NameLang TEXT);");

        db.execSQL("CREATE TABLE Genre (_IdGenre INTEGER PRIMARY KEY AUTOINCREMENT, NameGen TEXT) ");

        db.execSQL("CREATE TABLE User(_IdUser INTEGER PRIMARY KEY AUTOINCREMENT, LoginUser TEXT UNIQUE, PassUser TEXT, NameUser TEXT, " +
                "BirthUser DATE, EmailUser TEXT, PicUser TEXT, CountryUser INTEGER, TypeUser TINYINT, CrtDateUser DATE, IdLang INTEGER, IdDev INTEGER REFERENCES Developer(_IdDev));");

        db.execSQL("CREATE TABLE Application(_IdApp INTEGER PRIMARY KEY AUTOINCREMENT, NameApp TEXT, VersionApp TEXT, PriceApp DECIMAL, PublisherNameApp TEXT, ReleaseDateApp DATE, ArquiveApp TEXT, DescApp TEXT, " +
                "IdDev INTEGER REFERENCES Developer(_IdDev), TypeApp INTEGER, PegiApp INTEGER);");

        db.execSQL("CREATE TABLE Comment(_IdComm INTEGER PRIMARY KEY AUTOINCREMENT, ValueComm TEXT, DateComm DATETIME, IdUser INTEGER REFERENCES User(_IdUser), IdApp INTEGER REFERENCES Application(_IdApp));");

        db.execSQL("CREATE TABLE Review(_IdRev INTEGER PRIMARY KEY AUTOINCREMENT, ValueRev TEXT, DescRev TEXT, DateRev DATETIME, VersionRev TEXT, IdUser INTEGER REFERENCES User(_IdUser), IdApp INTEGER REFERENCES Application(_IdApp));");

        db.execSQL("CREATE TABLE ImagesApp(_IdImgApp INTEGER PRIMARY KEY AUTOINCREMENT, UrlImgApp TEXT, IdUser_FK INTEGER REFERENCES User(_IdUser), IdApp_FK INTEGER REFERENCES Application(_IdApp))");

        db.execSQL("CREATE TABLE UpdateNotesApp(_IdUpdateNotesApp INTEGER, ValueUpdateNotes TEXT, IdApp_FK INTEGER REFERENCES Application(_IdApp));");

        db.execSQL("CREATE TABLE ApplicationGenre(_IdApp_FK INTEGER REFERENCES Application(_IdApp), _IdGen_FK INTEGER REFERENCES Genre(_IdGenre))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
