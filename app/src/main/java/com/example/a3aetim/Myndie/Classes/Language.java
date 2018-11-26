package com.example.a3aetim.Myndie.Classes;

import java.io.Serializable;

public class Language implements Serializable {
    private int _IdLanguage;
    private String Name;

    public Language(int _IdLanguage, String name) {
        this._IdLanguage = _IdLanguage;
        Name = name;
    }

    public int get_IdLanguage() {
        return _IdLanguage;
    }

    public String getName() {
        return Name;
    }
}
