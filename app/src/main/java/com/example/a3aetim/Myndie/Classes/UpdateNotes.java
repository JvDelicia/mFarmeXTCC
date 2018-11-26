package com.example.a3aetim.Myndie.Classes;

import java.io.Serializable;

public class UpdateNotes implements Serializable {
    private int _IdUpdateNotes;
    private String Value;
    private int ApplicationId;

    public UpdateNotes(int _IdUpdateNotes, String value, int applicationId) {
        this._IdUpdateNotes = _IdUpdateNotes;
        Value = value;
        ApplicationId = applicationId;
    }

    public int get_IdUpdateNotes() {
        return _IdUpdateNotes;
    }

    public String getValue() {
        return Value;
    }

    public int getApplicationId() {
        return ApplicationId;
    }
}
