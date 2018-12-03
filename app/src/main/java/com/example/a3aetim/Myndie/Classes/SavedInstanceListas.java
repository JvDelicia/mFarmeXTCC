package com.example.a3aetim.Myndie.Classes;

import java.io.Serializable;
import java.util.ArrayList;

public class SavedInstanceListas implements Serializable {
    public static final String KEY = "SavedListas";
    public ArrayList<Application> appsNew,appsFeatured,appsAvaliation;
    public ArrayList<Genre> genres;
    public ArrayList<Application> partners;

    public SavedInstanceListas(ArrayList<Application> appsNew,ArrayList<Application> appsFeatured,ArrayList<Application> appsAvaliation, ArrayList<Genre> genres, ArrayList<Application> partners) {
        this.appsNew = appsNew;
        this.appsFeatured = appsFeatured;
        this.appsAvaliation = appsAvaliation;
        this.genres = genres;
        this.partners = partners;
    }
}
