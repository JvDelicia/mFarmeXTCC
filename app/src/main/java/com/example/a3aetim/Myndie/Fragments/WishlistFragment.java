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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.a3aetim.Myndie.Adapters.ApplicationAdapter;
import com.example.a3aetim.Myndie.Adapters.BackAppAdapter;
import com.example.a3aetim.Myndie.Adapters.WishlistAdapter;
import com.example.a3aetim.Myndie.ApplicationActivity;
import com.example.a3aetim.Myndie.Classes.Application;
import com.example.a3aetim.Myndie.Connection.AppConfig;
import com.example.a3aetim.Myndie.Connection.AppController;
import com.example.a3aetim.Myndie.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WishlistFragment extends Fragment {
    final public ArrayList<Application> app = new ArrayList<>();
    private RecyclerView recyclerViewWishlist;
    private RecyclerView.LayoutManager mRVLayoutManagerWishlist;
    private WishlistAdapter mAppAdapter;
    static public int iduser;
    TextView tv;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int userid = getArguments().getInt("userid");
        iduser = userid;
    }

    public void load(){
        mRVLayoutManagerWishlist = new LinearLayoutManager(getActivity());

    }

    public void setRecyclerViewWishlist(){
        mAppAdapter = new WishlistAdapter(app);
        recyclerViewWishlist.setLayoutManager(mRVLayoutManagerWishlist);
        recyclerViewWishlist.setAdapter(mAppAdapter);

        mAppAdapter.setOnitemClickListener(new WishlistAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view,int position) {
                Intent i = new Intent(getContext(),ApplicationActivity.class);
                i.putExtra("App",app.get(position));
                ImageView mImgvApp = (ImageView)view.findViewById(R.id.imgvAppItemMarket);
                startActivity(i);
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_wishlist, container, false);
        load();
        tv = (TextView)v.findViewById(R.id.txtWish);
        recyclerViewWishlist = (RecyclerView)v.findViewById(R.id.recyclerViewWishlist);
        recyclerViewWishlist.setHasFixedSize(true);
        getListApps();



        return v;
    }

    public void verificar(){
        if(app.size() != 0){
            tv.setVisibility(View.INVISIBLE);
        }
        else{
            tv.setVisibility(View.VISIBLE);
        }
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
                    setRecyclerViewWishlist();
                    verificar();
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
