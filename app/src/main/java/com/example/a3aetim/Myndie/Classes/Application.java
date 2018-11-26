package com.example.a3aetim.Myndie.Classes;

import android.content.ClipData;

import com.sysdata.widget.accordion.Item;

import java.io.Serializable;
import java.util.Date;

public class Application extends Item implements Serializable{
    private int _IdApp;
    private String Title;
    private double Price;
    private String Description;
    private String Version;
    private String PublisherName;
    private String ReleaseDate;

    public Application() {
    }

    public Application(int id, String title, double preco, String ver, String desc, String publisher, String releasedate){
        _IdApp = id;
        Title = title;
        Price = preco;
        Description = desc;
        Version = ver;
        PublisherName = publisher;
        ReleaseDate = releasedate;
    }

    public Application(Application app){
        _IdApp = app._IdApp;
        Title = app.Title;
        Price = app.Price;
        Description = app.Description;
        Version = app.Version;
        PublisherName = app.PublisherName;
        ReleaseDate = app.ReleaseDate;
    }

    public int get_IdApp() {
        return _IdApp;
    }

    public String getTitle() {
        return Title;
    }

    public double getPrice() {
        return Price;
    }

    public String getDescription() {
        return Description;
    }

    public String getVersion() {
        return Version;
    }

    public String getReleaseDate(){return ReleaseDate;}

    public String getPublisherName(){return PublisherName; }

    @Override
    public int getUniqueId() {
        return get_IdApp();
    }

    public void set_IdApp(int _IdApp) {
        this._IdApp = _IdApp;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setVersion(String version) {
        Version = version;
    }

    public void setPublisherName(String publisherName) {
        PublisherName = publisherName;
    }

    public void setReleaseDate(String releaseDate) {
        ReleaseDate = releaseDate;
    }
}
