package com.example.a3aetim.Myndie.Classes;

import java.io.Serializable;

public class Country implements Serializable {
    private int _IdCountry;
    private String Name;

    public Country(int _IdCountry, String name) {
        this._IdCountry = _IdCountry;
        Name = name;
    }

    public int get_IdCountry() {
        return _IdCountry;
    }

    public String getName() {
        return Name;
    }
}
