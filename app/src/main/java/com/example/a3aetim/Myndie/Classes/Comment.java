package com.example.a3aetim.Myndie.Classes;

import java.io.Serializable;

public class Comment implements Serializable{
    private int _IdComment;
    private String Value;
    private String Date;
    private int UserId;
    private int ApplicationId;

    public Comment(int _IdComment, String value, String date, int userId, int applicationId) {
        this._IdComment = _IdComment;
        this.Value = value;
        this.Date = date;
        UserId = userId;
        ApplicationId = applicationId;
    }

    public int get_IdComment() {
        return _IdComment;
    }

    public String getValue() {
        return Value;
    }

    public String getDate() {
        return Date;
    }

    public int getUserId() {
        return UserId;
    }

    public int getApplicationId() {
        return ApplicationId;
    }
}
