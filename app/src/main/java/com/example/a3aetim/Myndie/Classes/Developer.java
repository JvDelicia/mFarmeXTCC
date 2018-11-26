package com.example.a3aetim.Myndie.Classes;

import java.io.Serializable;

public class Developer implements Serializable {
    private int _IdDev;
    private String Info;
    private int NumSoft;
    private String Name;

    public Developer(int _IdDev, String info, int numSoft, String name) {
        this._IdDev = _IdDev;
        Info = info;
        NumSoft = numSoft;
        Name = name;
    }

    public int get_IdDev() {
        return _IdDev;
    }

    public String getInfo() {
        return Info;
    }

    public int getNumSoft() {
        return NumSoft;
    }

    public String getName() {
        return Name;
    }
}
