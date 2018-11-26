package com.example.a3aetim.Myndie.Classes;

import java.io.Serializable;

public class Genre implements Serializable {
    private int _IdGenre;
    private String Name;

    public Genre(int _IdGenre, String name) {
        this._IdGenre = _IdGenre;
        Name = name;
    }

    public int get_IdGenre() {
        return _IdGenre;
    }

    public String getName() {
        return Name;
    }
}
