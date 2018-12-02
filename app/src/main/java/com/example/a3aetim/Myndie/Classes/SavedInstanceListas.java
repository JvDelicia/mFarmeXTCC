package com.example.a3aetim.Myndie.Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class SavedInstanceListas implements Serializable {
    public static final String KEY = "SavedListas";
    public ArrayList<Application> apps;
    public ArrayList<Genre> genres;
    public ArrayList<Application> partners;

    public SavedInstanceListas(ArrayList<Application> apps, ArrayList<Genre> genres, ArrayList<Application> partners) {
        this.apps = apps;
        this.genres = genres;
        this.partners = partners;
    }
}
