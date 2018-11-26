package com.example.a3aetim.Myndie.Classes;

import java.io.Serializable;

public class User implements Serializable {
    private int _IdUser;
    private String LoginUser;
    private String PassUser;
    private String NameUser;
    private String BirthUser;
    private String EmailUser;
    private String PicUser;
    private int CountryUser;
    private int TypeUser;
    private String CrtDateUser;
    private int IdLang;
    private int IdDev;

    public User(){
        this._IdUser = 0;
        this.BirthUser = null;
        this.CountryUser = 0;
        this.CrtDateUser= null;
        this.EmailUser = null;
        this.IdDev=0;
        this.IdLang=0;
        this.LoginUser=null;
        this.NameUser=null;
        this.PassUser=null;
        this.PicUser=null;
        this.TypeUser=0;
    }
    public User(int id, String birthUser, int countryUser, String crtDateUser, String emailUser, int iddev, int idlang, String loginUser, String nameUser, String passUser, String picUser, int typeUser){
        this._IdUser = id;
        this.BirthUser = birthUser;
        this.CountryUser = countryUser;
        this.CrtDateUser = crtDateUser;
        this.EmailUser = emailUser;
        this.IdDev = iddev;
        this.IdLang = idlang;
        this.LoginUser = loginUser;
        this.NameUser = nameUser;
        this.PassUser = passUser;
        this.PicUser = picUser;
        this.TypeUser = typeUser;
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

    public int getTypeUser() {
        return TypeUser;
    }

    public String getBirthUser() {
        return BirthUser;
    }

    public String getCrtDateUser() {
        return CrtDateUser;
    }

    public String getLoginUser() {
        return LoginUser;
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