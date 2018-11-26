package com.example.a3aetim.Myndie.Classes;

import java.io.Serializable;

public class TypeApp implements Serializable {
    private int _IdTypeApp;
    private String Name;

    public TypeApp(int _IdTypeApp, String name) {
        this._IdTypeApp = _IdTypeApp;
        Name = name;
    }

    public int get_IdTypeApp() {
        return _IdTypeApp;
    }

    public String getName() {
        return Name;
    }
}
