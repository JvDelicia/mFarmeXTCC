package com.example.a3aetim.Myndie;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.MenuItemCompat;
import android.transition.ChangeBounds;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.a3aetim.Myndie.Classes.Application;
import com.example.a3aetim.Myndie.Classes.ImageDAO;
import com.example.a3aetim.Myndie.Classes.User;
import com.example.a3aetim.Myndie.Connection.AppConfig;
import com.example.a3aetim.Myndie.Connection.AppController;
import com.example.a3aetim.Myndie.Fragments.*;
import com.example.a3aetim.Myndie.Images.DownloadImage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.example.a3aetim.Myndie.Splash.PREF_NAME;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    User loggedUser;
    NavigationView navigationView;
    ArrayList<Application> app;
    public static final String TAG = AppController.class.getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        loadLocale();
        getWindow().setSharedElementExitTransition(new ChangeBounds());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState == null) {
            app = new ArrayList<>();
            getAllApps();
        }
        setLoggedData();
        setLoggedUser();
    }

    private void setLoggedData() {
        loggedUser = (User) getIntent().getSerializableExtra("LoggedUser");
        //RoundedBitmapDrawable imgRound = RoundedBitmapDrawableFactory.create(getResources(),img);
        //imgRound.setCornerRadius(100);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        ImageView navImgView = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.imgvNavHeader);
        TextView txtNomeUsu = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtvNameUserNav);
        TextView txtEmailUsu = (TextView) navigationView.getHeaderView(0).findViewById(R.id.txtEmailUserNav);
        //navImgView.setImageDrawable(imgRound);
        new DownloadImage(navImgView).execute(loggedUser.getPicUser());
        txtEmailUsu.setText(loggedUser.getEmailUser());
        txtNomeUsu.setText(loggedUser.getNameUser());
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openProf();
            }
        });
    }

    private void openMarket() {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.frameContentMain, new MarketFragment(app));
        ft.commit();
    }

    @Override
    public void onRestart(){
        super.onRestart();
        reiniciar();
    }

    private void openProf(){
        Intent intent = new Intent(this,ProfileActivity.class);
        intent.putExtra("ProfileUser",loggedUser);
        startActivity(intent);
    }

    private void setLoggedUser() {
        String FILENAME = "logged_user";
        File file =getFileStreamPath(FILENAME);
        try{
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(loggedUser);
            oos.close();
            fos.close();
        }catch (Exception e){}
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        SearchManager searchManager = (SearchManager)getSystemService(Context.SEARCH_SERVICE);
        android.support.v7.widget.SearchView searchView;
        MenuItem menuItem = menu.findItem(R.id.search_menu);
        searchView = (android.support.v7.widget.SearchView)menuItem.getActionView();
        assert searchManager != null;
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint(getResources().getString(R.string.activity_title_item_search));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_Market) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frameContentMain, new MarketFragment(app));
            ft.commit();
        } else if (id == R.id.nav_Avaliation) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frameContentMain, new CommentFragment());
            ft.commit();
        } /*else if (id == R.id.nav_Discussions) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frameContentMain, new DiscussionFragment());
            ft.commit();

        } */else if (id == R.id.nav_Confing) {
            Intent i = new Intent(this,SettingsActivity.class);
            startActivity(i);
           /* FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frameContentMain, new SettingsFragment());
            ft.commit();
*/
        } else if (id == R.id.nav_RateUs) {
            final String appName = "Myndie";
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appName)));
            }

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logOut(View view){
        String FILENAME = "logged_user";
        File file =getFileStreamPath(FILENAME);
        file.delete();
        finish();
    }

    public void setLocale(String lang){
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        getBaseContext().getResources().updateConfiguration(configuration,getBaseContext().getResources().getDisplayMetrics());
    }

    public void loadLocale(){
        SharedPreferences sp = getSharedPreferences(PREF_NAME,0);
        String language = sp.getString("lang","");
        setLocale(language);
    }

    public void reiniciar(){
        finish();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ECLAIR) {
            overridePendingTransition(0, 0);
            startActivity(getIntent());
            overridePendingTransition(0, 0);
        }
    }

    private void getAllApps() {
        final ArrayList arrayList = new ArrayList();
        // Tag used to cancel the request
        StringRequest strReq = new StringRequest(Request.Method.GET,
                AppConfig.URL_ListaApps, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray listaAplicativosResponse = new JSONArray(response);
                    //JSONArray appJSONArray = jsonObjectApp.getJSONArray("app");
                    //JSONObject jApp;

                    //boolean error = jsonObjectApp.getBoolean("error");

                    // Check for error node in json
                    // user successfully logged in
                    // Create login session
                    // Now store the user in SQLite
                        /*String uid = jObj.getString("Id");

                        JSONObject user = jObj.getJSONObject("User");
                        String username = user.getString("Username");

                        AlertDialog d = new AlertDialog.Builder(MainActivity.this).setMessage(uid + username).show();*/

                    //Toast.makeText(getApplicationContext(), uid + username, Toast.LENGTH_LONG).show();
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
                    openMarket();
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