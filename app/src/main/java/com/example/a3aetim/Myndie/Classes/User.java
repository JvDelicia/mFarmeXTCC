package com.example.a3aetim.Myndie.Classes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class User implements Serializable {
    private int _IdUser;
    private String Username;
    private String NameUser;
    private String PassUser;
    private String EmailUser;
    private String BirthUser;
    private String PicUser;
    private String CrtDateUser;
    private int CountryUser;
    private int IdLang;
    private int IdDev;
    private int ModeratorId;

    public User(){
        this._IdUser = 0;
        this.BirthUser = null;
        this.CountryUser = 0;
        this.CrtDateUser= null;
        this.EmailUser = null;
        this.IdDev=0;
        this.IdLang=0;
        this.Username=null;
        this.NameUser=null;
        this.PassUser=null;
        this.PicUser=null;
        this.ModeratorId=0;
    }
    public User(int id, String birthUser, int countryUser, String crtDateUser, String emailUser, int iddev, int idlang, String username, String nameUser, String passUser, String picUser, int moderatorId){
        this._IdUser = id;
        this.BirthUser = birthUser;
        this.CountryUser = countryUser;
        this.CrtDateUser = crtDateUser;
        this.EmailUser = emailUser;
        this.IdDev = iddev;
        this.IdLang = idlang;
        this.Username = username;
        this.NameUser = nameUser;
        this.PassUser = passUser;
        this.PicUser = picUser;
        this.ModeratorId = moderatorId;
    }

    public User(JSONObject user){
        try{
            this._IdUser = user.getInt("Id");
            this.BirthUser = user.getString("BirthDate");
            this.CountryUser = user.getInt("CountryId");
            this.CrtDateUser = user.getString("CrtDate");
            this.EmailUser = user.getString("Email");
            this.IdLang = user.getInt("LanguageId");
            this.Username = user.getString("Username");
            this.NameUser = user.getString("Name");
            this.PassUser = user.getString("Password");
            this.PicUser = "https://myndie.azurewebsites.net/"+user.getString("Picture");
            this.IdDev = user.getInt("DeveloperId");
            this.ModeratorId = user.getInt("ModeratorId");
        }catch (JSONException e){
            try{
                this.IdDev = user.getInt("DeveloperId");
            }
            catch (JSONException ex){
                this.IdDev = 0;
                try{
                    this.ModeratorId = user.getInt("ModeratorId");
                }
                catch (JSONException exc){this.ModeratorId = 0;}
            }
        }
    }


    public int get_IdUser() {
        return _IdUser;
    }

    public int getCountryUser() {
        return CountryUser;
    }

    public int getIdDev() {
        return IdDev;
    }

    public int getIdLang() {
        return IdLang;
    }

    public String getUsername() {
        return Username;
    }

    public int getModeratorId() {
        return ModeratorId;
    }

    public String getBirthUser() {
        return BirthUser;
    }

    public String getCrtDateUser() {
        return CrtDateUser;
    }

    public String getPassUser() {
        return PassUser;
    }

    public String getPicUser() {
        return PicUser;
    }

    public String getNameUser() {
        return NameUser;
    }

    public String getEmailUser() {
        return EmailUser;
    }
}