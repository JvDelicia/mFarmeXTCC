package com.example.a3aetim.Myndie.Classes;

import java.io.Serializable;

public class Review implements Serializable {
    private int _IdReview;
    private String Value;
    private String Desc;
    private String Date;
    private String Version;
    private int UserId;
    private int ApplicationId;

    public Review(int _IdReview, String value, String desc, String date, String version, int userId, int applicationId) {
        this._IdReview = _IdReview;
        Value = value;
        Desc = desc;
        Date = date;
        Version = version;
        UserId = userId;
        ApplicationId = applicationId;
    }

    public int get_IdReview() {
        return _IdReview;
    }

    public String getValue() {
        return Value;
    }

    public String getDesc() {
        return Desc;
    }

    public String getDate() {
        return Date;
    }

    public String getVersion() {
        return Version;
    }

    public int getUserId() {
        return UserId;
    }

    public int getApplicationId() {
        return ApplicationId;
    }
}
