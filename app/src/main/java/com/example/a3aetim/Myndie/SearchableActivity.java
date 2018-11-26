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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.a3aetim.Myndie.Adapters.ApplicationAdapter;
import com.example.a3aetim.Myndie.Classes.Application;
import com.example.a3aetim.Myndie.Classes.Genre;
import com.example.a3aetim.Myndie.helper.DatabaseHelper;

import java.sql.Date;
import java.util.ArrayList;

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

        private void getApps(String queryNome){
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
        }
        public void searchHandler(Intent intent){
            if(Intent.ACTION_SEARCH.toLowerCase().equalsIgnoreCase(intent.getAction().toLowerCase())){
                if(intent.getSerializableExtra("Genre") != null){
                    Genre g = (Genre) intent.getSerializableExtra("Genre");
                    toolbar.setTitle(g.getName());
                    getAppsByGenre(g.get_IdGenre());
                }
                else{
                    String query = intent.getStringExtra(SearchManager.QUERY);
                    toolbar.setTitle(query);
                    getApps(query);
                }
        }
    }

    private void getAppsByGenre(int idGenre) {
        Cursor cursorapp = db.rawQuery("SELECT _IdApp, NameApp, PriceApp,VersionApp, PublisherNameApp, ReleaseDateApp, DescApp from Application INNER JOIN ApplicationGenre on Application._IdApp = ApplicationGenre._IdApp_FK WHERE Application._IdApp = ApplicationGenre._IdApp_FK AND _IdGen_FK ="+idGenre, null);
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
    }

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
