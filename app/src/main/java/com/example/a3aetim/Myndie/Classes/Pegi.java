package com.example.a3aetim.Myndie.Classes;

import java.io.Serializable;

public class Pegi implements Serializable {
    private int _IdPegi;
    private String PhotoPath;
    private String Age;
    private String Desc;

    public Pegi(int _IdPegi, String photoPath, String age, String desc) {
        this._IdPegi = _IdPegi;
        PhotoPath = photoPath;
        Age = age;
        Desc = desc;
    }

    public int get_IdPegi() {
        return _IdPegi;
    }

    public String getPhotoPath() {
        return PhotoPath;
    }

    public String getAge() {
        return Age;
    }

    public String getDesc() {
        return Desc;
    }
}
