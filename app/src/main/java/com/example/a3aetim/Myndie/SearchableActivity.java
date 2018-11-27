package com.example.a3aetim.Myndie;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.a3aetim.Myndie.Adapters.ApplicationAdapter;
import com.example.a3aetim.Myndie.Classes.Application;
import com.example.a3aetim.Myndie.Classes.Genre;
import com.example.a3aetim.Myndie.Classes.User;
import com.example.a3aetim.Myndie.Connection.AppConfig;
import com.example.a3aetim.Myndie.Connection.AppController;
import com.example.a3aetim.Myndie.helper.DatabaseHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.a3aetim.Myndie.Connection.AppController.TAG;

public class SearchableActivity extends AppCompatActivity {
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    private ArrayList<Application> app;
    private RecyclerView mRecyclerView;
    private ApplicationAdapter mRVAdapter;
    private RecyclerView.LayoutManager mRVLManager;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        toolbar = (Toolbar)findViewById(R.id.toolbar_Search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("");
        mRecyclerView = findViewById(R.id.recyclerSearchActivity);
        mRecyclerView.setHasFixedSize(true);
        setmRecyclerView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        searchHandler(intent);
    }

    private void load(){
        helper = new DatabaseHelper(this);
        db = helper.getReadableDatabase();
        app = new ArrayList<>();
        mRVLManager = new LinearLayoutManager(this);
    }
    private void setmRecyclerView(){
        load();
        searchHandler(getIntent());
        mRVAdapter = new ApplicationAdapter(app);
        mRecyclerView.setLayoutManager(mRVLManager);
            mRecyclerView.setAdapter(mRVAdapter);

            mRVAdapter.setOnitemClickListener(new ApplicationAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view,int position) {
                    Intent i = new Intent(getApplicationContext(),ApplicationActivity.class);
                    i.putExtra("App",app.get(position));
                    ImageView mImgvApp = (ImageView)view.findViewById(R.id.imgvAppItemMarket);
                    //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair.<View, String>create(mImgvApp,"AppTransition"));
                    startActivity(i/*,options.toBundle()*/);
                }
            });
        }

        /*private void getApps(String queryNome){
            Cursor cursorapp = db.rawQuery("SELECT _IdApp, NameApp, PriceApp,VersionApp, PublisherNameApp, ReleaseDateApp, DescApp from Application WHERE NameApp LIKE '%"+ queryNome+"%'", null);
            int idapp;
            String nameapp,version, publisher, desc;
            double preco;
            String releasedate;
            cursorapp.moveToFirst();
            for (int j = 0; j < cursorapp.getCount(); j++) {
                idapp = cursorapp.getInt(0);
                nameapp = cursorapp.getString(1);
                preco = cursorapp.getDouble(2);
                version = cursorapp.getString(3);
                publisher = cursorapp.getString(4);
                releasedate = cursorapp.getString(5);
                desc = cursorapp.getString(6);
                app.add(new Application(idapp,nameapp,preco,version,desc,publisher,releasedate));
                cursorapp.moveToNext();
            }
            cursorapp.close();
        }*/

        public void searchHandler(Intent intent){
            if(Intent.ACTION_SEARCH.toLowerCase().equalsIgnoreCase(intent.getAction().toLowerCase())){
                if(intent.getSerializableExtra("Genre") != null){
                    Genre g = (Genre) intent.getSerializableExtra("Genre");
                    toolbar.setTitle(g.getName());
                    //getAppsByGenre(g.get_IdGenre());
                }
                else{
                    String query = intent.getStringExtra(SearchManager.QUERY);
                    toolbar.setTitle(query);
                    //getApps(query);
                }
        }
    }

   /* private void getAppsByGenre(int idGenre) {

        String tag_string_req = "req_login";

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ConsultaAppGeneros, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");

                    // Check for error node in json
                    if (!error) {
                        // Now store the user in SQLite
                        String uid = jObj.getString("Id");

                        JSONObject user = jObj.getJSONObject("User");
                        User usuario = new User(user);
                        logar(usuario);

                    } else {
                        // Error in login. Get the error message
                        String errorMsg = jObj.getString("error_msg");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {

            @Override
            protected Map<String, String> getParams() {
                // Posting parameters to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("idapp", email);
                params.put("password", password);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
        *//*Cursor cursorapp = db.rawQuery("SELECT _IdApp, NameApp, PriceApp,VersionApp, PublisherNameApp, ReleaseDateApp, DescApp from Application INNER JOIN ApplicationGenre on Application._IdApp = ApplicationGenre._IdApp_FK WHERE Application._IdApp = ApplicationGenre._IdApp_FK AND _IdGen_FK ="+idGenre, null);
        int idapp;
        String nameapp,version, publisher, desc;
        double preco;
        String releasedate;
        cursorapp.moveToFirst();
        for (int j = 0; j < cursorapp.getCount(); j++) {
            idapp = cursorapp.getInt(0);
            nameapp = cursorapp.getString(1);
            preco = cursorapp.getDouble(2);
            version = cursorapp.getString(3);
            publisher = cursorapp.getString(4);
            releasedate = cursorapp.getString(5);
            desc = cursorapp.getString(6);
            app.add(new Application(idapp,nameapp,preco,version,desc,publisher,releasedate));
            cursorapp.moveToNext();
        }
        cursorapp.close();*//*
    }
*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.searchablemenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy(){
        helper.close();
        super.onDestroy();
    }
}
