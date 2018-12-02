package com.example.a3aetim.Myndie.Fragments;

import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.a3aetim.Myndie.Adapters.ApplicationAdapter;
import com.example.a3aetim.Myndie.Adapters.BackAppAdapter;
import com.example.a3aetim.Myndie.ApplicationActivity;
import com.example.a3aetim.Myndie.Classes.Application;
import com.example.a3aetim.Myndie.Connection.AppConfig;
import com.example.a3aetim.Myndie.Connection.AppController;
import com.example.a3aetim.Myndie.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WishlistFragment extends Fragment {
    public ArrayList<Application> app = new ArrayList<>();
    private RecyclerView recyclerViewWishlist;
    private RecyclerView.LayoutManager mRVLayoutManagerWishlist, mRVLManagerFundoWishlist;
    private ApplicationAdapter mAppAdapter;
    private BackAppAdapter mBackAppAdapter;
    private ArrayList<String> fundoWishlist;
    final public int iduser = 2;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void load(){
        fundoWishlist = new ArrayList<>();
        fundoWishlist.add(getResources().getString(R.string.activity_title_item_wishlist));
        mRVLayoutManagerWishlist = new LinearLayoutManager(getActivity(),LinearLayoutManager.HORIZONTAL,false);
        mRVLManagerFundoWishlist = new LinearLayoutManager(getActivity());
    }

    public void setRecyclerViewWishlist(){
        mAppAdapter = new ApplicationAdapter(app);
        mBackAppAdapter = new BackAppAdapter(fundoWishlist, mAppAdapter, mRVLayoutManagerWishlist);
        recyclerViewWishlist.setLayoutManager(mRVLManagerFundoWishlist);
        recyclerViewWishlist.setAdapter(mAppAdapter);

        mAppAdapter.setOnitemClickListener(new ApplicationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
                Intent i = new Intent(getContext(),ApplicationActivity.class);
                i.putExtra("App",app.get(position));
                ImageView mImgvApp = (ImageView)view.findViewById(R.id.imgvAppItemMarket);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), Pair.<View, String>create(mImgvApp,"AppTransition"));
                startActivity(i,options.toBundle());
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //load();
        View v = inflater.inflate(R.layout.fragment_wishlist, container, false);
        /*recyclerViewWishlist = (RecyclerView)v.findViewById(R.id.recyclerViewWishlist);
        recyclerViewWishlist.setHasFixedSize(true);
        getListApps();
        setRecyclerViewWishlist();*/
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void getListApps(){
        final ArrayList arrayList = new ArrayList();
        // Tag used to cancel the request
        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_Wishlist+"?uid="+Integer.toString(iduser), new Response.Listener<String>() {
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
                    app.addAll(arrayList);

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
            /*@Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("uid", "2");

                return params;
            }*/
        };


        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }
}
