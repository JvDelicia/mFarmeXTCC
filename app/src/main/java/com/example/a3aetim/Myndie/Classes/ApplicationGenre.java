package com.example.a3aetim.Myndie.Classes;

import java.io.Serializable;

public class ApplicationGenre implements Serializable {
    private int _IdApplicationGenre;
    private int ApplicationId;
    private int GenreId;

    public ApplicationGenre(int _IdApplicationGenre, int applicationId, int genreId) {
        this._IdApplicationGenre = _IdApplicationGenre;
        ApplicationId = applicationId;
        GenreId = genreId;
    }

    public int get_IdApplicationGenre() {
        return _IdApplicationGenre;
    }

    public int getApplicationId() {
        return ApplicationId;
    }

    public int getGenreId() {
        return GenreId;
    }
}
