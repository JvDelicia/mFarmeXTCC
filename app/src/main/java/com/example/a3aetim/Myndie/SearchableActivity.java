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
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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

import org.json.JSONArray;
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
    public ArrayList<Application> app;
    private RecyclerView mRecyclerView;
    private ApplicationAdapter mRVAdapter;
    private RecyclerView.LayoutManager mRVLManager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);

        toolbar = (Toolbar) findViewById(R.id.toolbar_Search);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("");
        mRecyclerView = findViewById(R.id.recyclerSearchActivity);
        mRecyclerView.setHasFixedSize(true);
        searchHandler(getIntent());

    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        searchHandler(intent);
    }

    private void load() {
        helper = new DatabaseHelper(this);
        db = helper.getReadableDatabase();
        //mRVLManager = new GridLayoutManager(this,3);
        mRVLManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
    }

    private void setmRecyclerView() {
        load();
        mRVAdapter = new ApplicationAdapter(app);
        mRecyclerView.setLayoutManager(mRVLManager);
        mRecyclerView.setAdapter(mRVAdapter);

        mRVAdapter.setOnitemClickListener(new ApplicationAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(getApplicationContext(), ApplicationActivity.class);
                i.putExtra("App", app.get(position));
                ImageView mImgvApp = (ImageView) view.findViewById(R.id.imgvAppItemMarket);
                //ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, Pair.<View, String>create(mImgvApp,"AppTransition"));
                startActivity(i/*,options.toBundle()*/);
            }
        });
    }

    public void searchHandler(Intent intent) {
        if (Intent.ACTION_SEARCH.toLowerCase().equalsIgnoreCase(intent.getAction().toLowerCase())) {
            if (intent.getSerializableExtra("Genre") != null) {
                Genre g = (Genre) intent.getSerializableExtra("Genre");
                toolbar.setTitle(g.getName());
                app = new ArrayList<>();
                getAppsByGenre(g.get_IdGenre());
            } else {
                String query = intent.getStringExtra(SearchManager.QUERY);
                toolbar.setTitle(query);
                app = new ArrayList<>();
                getAppsByName(query);
            }
        }
    }

    private void getAppsByGenre(final int idGenre) {

        String tag_string_req = "req_login";
        final ArrayList arrayList = new ArrayList();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ConsultaAppGeneros, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                try {
                    JSONArray jObj = new JSONArray(response);

                    for (int i = 0; i < jObj.length(); i++) {
                        JSONObject jsonObjectApp = jObj.getJSONObject(i);
                        int idapp = Integer.parseInt(jsonObjectApp.getString("Id"));
                        String title = jsonObjectApp.getString("Name");
                        String desc = jsonObjectApp.getString("Desc");
                        String version = jsonObjectApp.getString("Version");
                        double price = Double.parseDouble(jsonObjectApp.getString("Price"));
                        String publisher = jsonObjectApp.getString("PublisherName");
                        String releasedate = jsonObjectApp.getString("ReleaseDate");
                        String imageURL = jsonObjectApp.getString("ImageUrl");
                        imageURL = "https://myndie.azurewebsites.net/" + imageURL;
                        int devId = jsonObjectApp.getInt("DeveloperId");
                        int typeAppId = jsonObjectApp.getInt("TypeAppId");
                        int pegiId = jsonObjectApp.getInt("PegiId");
                        Application objetoApp = new Application(idapp, title, price, version, desc, publisher, releasedate, imageURL, devId, typeAppId, pegiId);
                        arrayList.add(objetoApp);
                    }
                    app.addAll(arrayList);
                    setmRecyclerView();

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
                params.put("idGenre", Integer.toString(idGenre));

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    private void getAppsByName(final String appName) {

        String tag_string_req = "req_login";
        final ArrayList arrayList = new ArrayList();
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ConsultaAppNome, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, "Login Response: " + response.toString());

                try {
                    JSONArray jObj = new JSONArray(response);

                    for (int i = 0; i < jObj.length(); i++) {
                        JSONObject jsonObjectApp = jObj.getJSONObject(i);
                        int idapp = Integer.parseInt(jsonObjectApp.getString("Id"));
                        String title = jsonObjectApp.getString("Name");
                        String desc = jsonObjectApp.getString("Desc");
                        String version = jsonObjectApp.getString("Version");
                        double price = Double.parseDouble(jsonObjectApp.getString("Price"));
                        String publisher = jsonObjectApp.getString("PublisherName");
                        String releasedate = jsonObjectApp.getString("ReleaseDate");
                        String imageURL = jsonObjectApp.getString("ImageUrl");
                        imageURL = "https://myndie.azurewebsites.net/" + imageURL;
                        int devId = jsonObjectApp.getInt("DeveloperId");
                        int typeAppId = jsonObjectApp.getInt("TypeAppId");
                        int pegiId = jsonObjectApp.getInt("PegiId");
                        Application objetoApp = new Application(idapp, title, price, version, desc, publisher, releasedate, imageURL, devId, typeAppId, pegiId);
                        //Toast.makeText(SearchableActivity.this, objetoApp.getTitle(), Toast.LENGTH_SHORT).show();
                        arrayList.add(objetoApp);
                    }
                    app.addAll(arrayList);
                    setmRecyclerView();

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
                params.put("Name", appName);

                return params;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq, tag_string_req);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchablemenu, menu);
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
    protected void onDestroy() {
        helper.close();
        super.onDestroy();
    }
}