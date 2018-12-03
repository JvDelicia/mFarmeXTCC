package com.example.a3aetim.Myndie.Fragments;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.a3aetim.Myndie.Adapters.ApplicationAdapter;
import com.example.a3aetim.Myndie.Adapters.BackAppAdapter;
import com.example.a3aetim.Myndie.Adapters.GenreAdapter;
import com.example.a3aetim.Myndie.ApplicationActivity;
import com.example.a3aetim.Myndie.Classes.Application;
import com.example.a3aetim.Myndie.Classes.Genre;
import com.example.a3aetim.Myndie.Classes.SavedInstanceListas;
import com.example.a3aetim.Myndie.Connection.AppConfig;
import com.example.a3aetim.Myndie.Connection.AppController;
import com.example.a3aetim.Myndie.R;
import com.example.a3aetim.Myndie.SearchableActivity;
import com.example.a3aetim.Myndie.helper.DatabaseHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MarketFragment extends Fragment {
    private DatabaseHelper helper;
    private ArrayList<Application> appNew,appFeatured,appAvaliation,partners;
    private ArrayList<Genre> genre;
    private ArrayList<String> fundoNew,fundoFeatured,fundoAvaliation,fundoPartner;
    private RecyclerView mRecyclerViewNew,mRecyclerViewFeatured,mRecyclerViewAvaliation,genreRecyclerView,mRecyclerViewPartner;
    private ApplicationAdapter mRVAdapterNew,mRVAdapterFeatured,mRVAdapterAvaliation,mAppAdapterPartners;
    private BackAppAdapter mBackAdapterNew,mBackAdapterFeatured,mBackAdapterAvaliation,mBackAdapterPartner;
    private RecyclerView.LayoutManager mRVLManagerNew,mRVLManagerFeatured,mRVLManagerAvaliation,mRVLManagerFundoNew,mRVLManagerFundoFeatured,mRVLManagerFundoAvaliation,mRVLManagerPartner,mRVLManagerFundoPartner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        load();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_market, container, false);
        view.findViewById(R.id.recyclerViewMarket);
        mRecyclerViewNew = view.findViewById(R.id.recyclerViewMarket);
        mRecyclerViewNew.setHasFixedSize(true);
        ///
        mRecyclerViewFeatured = view.findViewById(R.id.recyclerViewMarketFeatured);
        mRecyclerViewFeatured.setHasFixedSize(true);
        ///
        mRecyclerViewAvaliation = view.findViewById(R.id.recyclerViewMarketAvaliation);
        mRecyclerViewAvaliation.setHasFixedSize(true);
        ///
        genreRecyclerView = view.findViewById(R.id.recyclerViewMarketGenre);
        genreRecyclerView.setHasFixedSize(true);
        ///
        mRecyclerViewPartner = view.findViewById(R.id.recyclerViewMarketPartner);
        mRecyclerViewPartner.setHasFixedSize(true);

        if(savedInstanceState != null){
            SavedInstanceListas savedListas = (SavedInstanceListas) savedInstanceState.getSerializable(SavedInstanceListas.KEY);
            appNew = savedListas.appsNew;
            appFeatured = savedListas.appsFeatured;
            appAvaliation = savedListas.appsAvaliation;
            genre = savedListas.genres;
            partners = savedListas.partners;
        }
        if(savedInstanceState == null || appNew.size() == 0 || appFeatured.size() == 0 || appAvaliation.size() == 0 || genre.size() == 0 || partners.size() == 0){
            getNewApps();
            getFeaturedApps();
            getAvaliatedApps();
            getPartnersApps();
            getAllGenres();
        }
        else{
            setmRecyclerViewNew();
            setmRecyclerViewFeatured();
            setmRecyclerViewAvaliation();
            setmRecyclerViewPartners();
            setGenreMarket();
        }

        return view;
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(SavedInstanceListas.KEY, new SavedInstanceListas(appNew,appFeatured,appAvaliation,genre,partners));
    }

    private void load(){
        helper = new DatabaseHelper(getActivity());
        appNew = new ArrayList<>();
        appFeatured = new ArrayList<>();
        appAvaliation = new ArrayList<>();
        partners = new ArrayList<>();
        genre = new ArrayList<>();
        fundoNew = new ArrayList<String>();
        fundoNew.add(getResources().getString(R.string.market_base_new));
        fundoFeatured = new ArrayList<String>();
        fundoFeatured.add(getResources().getString(R.string.market_base_featured));
        fundoAvaliation = new ArrayList<String>();
        fundoAvaliation.add(getResources().getString(R.string.market_base_avaliated));
        fundoPartner = new ArrayList<>();
        fundoPartner.add(getResources().getString(R.string.market_base_partners));
        mRVLManagerNew = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mRVLManagerFeatured = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mRVLManagerAvaliation = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mRVLManagerPartner = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mRVLManagerFundoNew = new LinearLayoutManager(getActivity());
        mRVLManagerFundoFeatured = new LinearLayoutManager(getActivity());
        mRVLManagerFundoAvaliation = new LinearLayoutManager(getActivity());
        mRVLManagerFundoPartner = new LinearLayoutManager(getActivity());
    }

    private void setmRecyclerViewNew(){
        mRVAdapterNew = new ApplicationAdapter(appNew);
        mBackAdapterNew = new BackAppAdapter(fundoNew,mRVAdapterNew,mRVLManagerNew);
        //Define quadro que fica atrás dos apps
        mRecyclerViewNew.setLayoutManager(mRVLManagerFundoNew);
        mRecyclerViewNew.setAdapter(mBackAdapterNew);

        mRVAdapterNew.setOnitemClickListener(new ApplicationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
                Intent i = new Intent(getContext(),ApplicationActivity.class);
                i.putExtra("App",appNew.get(position));
                ImageView mImgvApp = (ImageView)view.findViewById(R.id.imgvAppItemMarket);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.<View, String>create(mImgvApp,"AppTransition"));
                startActivity(i,options.toBundle());
            }
        });
    }
    private void setmRecyclerViewFeatured(){
        mRVAdapterFeatured = new ApplicationAdapter(appFeatured);
        mBackAdapterFeatured = new BackAppAdapter(fundoFeatured,mRVAdapterFeatured,mRVLManagerFeatured);
        //Define quadro que fica atrás dos apps
        mRecyclerViewFeatured.setLayoutManager(mRVLManagerFundoFeatured);
        mRecyclerViewFeatured.setAdapter(mBackAdapterFeatured);

        mRVAdapterFeatured.setOnitemClickListener(new ApplicationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
                Intent i = new Intent(getContext(),ApplicationActivity.class);
                i.putExtra("App",appFeatured.get(position));
                ImageView mImgvApp = (ImageView)view.findViewById(R.id.imgvAppItemMarket);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.<View, String>create(mImgvApp,"AppTransition"));
                startActivity(i,options.toBundle());
            }
        });
    }
    private void setmRecyclerViewAvaliation(){
        mRVAdapterAvaliation = new ApplicationAdapter(appAvaliation);
        mBackAdapterAvaliation = new BackAppAdapter(fundoAvaliation,mRVAdapterAvaliation,mRVLManagerAvaliation);
        //Define quadro que fica atrás dos apps
        mRecyclerViewAvaliation.setLayoutManager(mRVLManagerFundoAvaliation);
        mRecyclerViewAvaliation.setAdapter(mBackAdapterAvaliation);

        mRVAdapterAvaliation.setOnitemClickListener(new ApplicationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
                Intent i = new Intent(getContext(),ApplicationActivity.class);
                i.putExtra("App",appAvaliation.get(position));
                ImageView mImgvApp = (ImageView)view.findViewById(R.id.imgvAppItemMarket);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.<View, String>create(mImgvApp,"AppTransition"));
                startActivity(i,options.toBundle());
            }
        });
    }
    private void setGenreMarket(){
        GenreAdapter mGenredapter = new GenreAdapter(genre);
        //Define quadro que fica atrás dos apps

        genreRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false));
        genreRecyclerView.setAdapter(mGenredapter);

        mGenredapter.setOnitemClickListener(new GenreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
                Intent i = new Intent(getContext(),SearchableActivity.class);
                i.setAction(Intent.ACTION_SEARCH);
                i.putExtra("Genre",genre.get(position));
                startActivity(i);
            }
        });
    }

    private void setmRecyclerViewPartners(){
        mAppAdapterPartners = new ApplicationAdapter(partners);
        mBackAdapterPartner = new BackAppAdapter(fundoPartner,mAppAdapterPartners,mRVLManagerPartner);
        //Define quadro que fica atrás dos apps
        mRecyclerViewPartner.setLayoutManager(mRVLManagerFundoPartner);
        mRecyclerViewPartner.setAdapter(mBackAdapterPartner);

        mAppAdapterPartners.setOnitemClickListener(new ApplicationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
                Intent i = new Intent(getContext(),ApplicationActivity.class);
                i.putExtra("App",partners.get(position));
                ImageView mImgvApp = (ImageView)view.findViewById(R.id.imgvAppItemMarket);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.<View, String>create(mImgvApp,"AppTransition"));
                startActivity(i,options.toBundle());
            }
        });
    }

    @Override
    public void onDestroy(){
        helper.close();
        super.onDestroy();
    }
    ///////////////////
    private void getAllGenres() {
        final ArrayList arrayList = new ArrayList();
        // Tag used to cancel the request
        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_ListaGeneros, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray listaAplicativosResponse = new JSONArray(response);

                    for (int i = 0; i < listaAplicativosResponse.length(); i++) {
                        JSONObject jsonObjectApp = listaAplicativosResponse.getJSONObject(i);
                        int id = Integer.parseInt(jsonObjectApp.getString("Id"));
                        String name = jsonObjectApp.getString("Name");
                        Genre objetoGenre = new Genre(id,name);
                        arrayList.add(objetoGenre);
                    }
                    genre.addAll(arrayList);
                    setGenreMarket();
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }
    ///////////////////
    private void getNewApps() {
        final ArrayList arrayList = new ArrayList();
        // Tag used to cancel the request
        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_ListaNewApps, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray listaAplicativosResponse = new JSONArray(response);

                    for (int i = 0; i < listaAplicativosResponse.length(); i++) {
                        JSONObject jsonObjectApp = listaAplicativosResponse.getJSONObject(i);
                        int idapp = Integer.parseInt(jsonObjectApp.getString("Id"));
                        String title = jsonObjectApp.getString("Name");
                        String desc = jsonObjectApp.getString("Desc");
                        String version = jsonObjectApp.getString("Version");
                        double price = Double.parseDouble(jsonObjectApp.getString("Price"));
                        String publisher = jsonObjectApp.getString("PublisherName");
                        String releasedate = jsonObjectApp.getString("ReleaseDate");
                        String imageURL = jsonObjectApp.getString("ImageUrl");
                        imageURL = "https://myndie.azurewebsites.net/"+imageURL;
                        int devId = jsonObjectApp.getInt("DeveloperId");
                        int typeAppId = jsonObjectApp.getInt("TypeAppId");
                        int pegiId = jsonObjectApp.getInt("PegiId");
                        Application objetoApp = new Application(idapp, title, price, version, desc, publisher, releasedate,imageURL,devId,typeAppId,pegiId);
                        arrayList.add(objetoApp);
                    }
                    appNew.addAll(arrayList);
                    //openMarket();
                    setmRecyclerViewNew();
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }
    ///////////////////
    private void getFeaturedApps() {
        final ArrayList arrayList = new ArrayList();
        // Tag used to cancel the request
        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_ListaFeaturedApps, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray listaAplicativosResponse = new JSONArray(response);

                    for (int i = 0; i < listaAplicativosResponse.length(); i++) {
                        JSONObject jsonObjectApp = listaAplicativosResponse.getJSONObject(i);
                        int idapp = Integer.parseInt(jsonObjectApp.getString("Id"));
                        String title = jsonObjectApp.getString("Name");
                        String desc = jsonObjectApp.getString("Desc");
                        String version = jsonObjectApp.getString("Version");
                        double price = Double.parseDouble(jsonObjectApp.getString("Price"));
                        String publisher = jsonObjectApp.getString("PublisherName");
                        String releasedate = jsonObjectApp.getString("ReleaseDate");
                        String imageURL = jsonObjectApp.getString("ImageUrl");
                        imageURL = "https://myndie.azurewebsites.net/"+imageURL;
                        int devId = jsonObjectApp.getInt("DeveloperId");
                        int typeAppId = jsonObjectApp.getInt("TypeAppId");
                        int pegiId = jsonObjectApp.getInt("PegiId");
                        Application objetoApp = new Application(idapp, title, price, version, desc, publisher, releasedate,imageURL,devId,typeAppId,pegiId);
                        arrayList.add(objetoApp);
                    }
                    appFeatured.addAll(arrayList);
                    setmRecyclerViewFeatured();
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }
    ///////////////////
    private void getAvaliatedApps() {
        final ArrayList arrayList = new ArrayList();
        // Tag used to cancel the request
        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_ListaAvaliatedApps, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray listaAplicativosResponse = new JSONArray(response);

                    for (int i = 0; i < listaAplicativosResponse.length(); i++) {
                        JSONObject jsonObjectApp = listaAplicativosResponse.getJSONObject(i);
                        int idapp = Integer.parseInt(jsonObjectApp.getString("Id"));
                        String title = jsonObjectApp.getString("Name");
                        String desc = jsonObjectApp.getString("Desc");
                        String version = jsonObjectApp.getString("Version");
                        double price = Double.parseDouble(jsonObjectApp.getString("Price"));
                        String publisher = jsonObjectApp.getString("PublisherName");
                        String releasedate = jsonObjectApp.getString("ReleaseDate");
                        String imageURL = jsonObjectApp.getString("ImageUrl");
                        imageURL = "https://myndie.azurewebsites.net/"+imageURL;
                        int devId = jsonObjectApp.getInt("DeveloperId");
                        int typeAppId = jsonObjectApp.getInt("TypeAppId");
                        int pegiId = jsonObjectApp.getInt("PegiId");
                        Application objetoApp = new Application(idapp, title, price, version, desc, publisher, releasedate,imageURL,devId,typeAppId,pegiId);
                        arrayList.add(objetoApp);
                    }
                    appAvaliation.addAll(arrayList);
                    setmRecyclerViewAvaliation();
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }
    ///////////////////
    private void getPartnersApps() {
        final ArrayList arrayList = new ArrayList();
        // Tag used to cancel the request
        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_ConsultaPartnersApp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray listaAplicativosResponse = new JSONArray(response);

                    for (int i = 0; i < listaAplicativosResponse.length(); i++) {
                        JSONObject jsonObjectApp = listaAplicativosResponse.getJSONObject(i);
                        int idapp = Integer.parseInt(jsonObjectApp.getString("Id"));
                        String title = jsonObjectApp.getString("Name");
                        String desc = jsonObjectApp.getString("Desc");
                        String version = jsonObjectApp.getString("Version");
                        double price = Double.parseDouble(jsonObjectApp.getString("Price"));
                        String publisher = jsonObjectApp.getString("PublisherName");
                        String releasedate = jsonObjectApp.getString("ReleaseDate");
                        String imageURL = jsonObjectApp.getString("ImageUrl");
                        imageURL = "https://myndie.azurewebsites.net/"+imageURL;
                        int devId = jsonObjectApp.getInt("DeveloperId");
                        int typeAppId = jsonObjectApp.getInt("TypeAppId");
                        int pegiId = jsonObjectApp.getInt("PegiId");
                        Application objetoApp = new Application(idapp, title, price, version, desc, publisher, releasedate,imageURL,devId,typeAppId,pegiId);
                        arrayList.add(objetoApp);
                    }
                    partners.addAll(arrayList);
                    setmRecyclerViewPartners();
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();

                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }) {

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }
}