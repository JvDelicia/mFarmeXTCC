package com.example.a3aetim.Myndie;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.a3aetim.Myndie.Fragments.SettingsFragment;

import java.util.Locale;

import static com.example.a3aetim.Myndie.Splash.PREF_NAME;

public class SettingsActivity extends AppCompatActivity {
    public static final String KEY_NOT = "notification_chk";
    public static final String KEY_SOUND = "sound_chk";
    public static final String KEY_VIBRATION = "vibration_chk";
    public static final String KEY_LANGUAGE = "selec_lang";
    String idiomPref="";
    SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLocale();
        setContentView(R.layout.activity_settings);
        getSupportActionBar().setTitle(getString(R.string.title_activity_settings));
        if(savedInstanceState == null) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.FrameSettings, new SettingsFragment());
            ft.commit();
        }
        carregarValores();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
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

    public void carregarValores(){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
                if(key.equals("selec_lang")){
                    carregarValores();
                    loadLocale();
                    restartThis();
                }
            }
        };

        sharedPref.registerOnSharedPreferenceChangeListener(listener);
        idiomPref = sharedPref.getString(KEY_LANGUAGE, "-1");
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean switchPref = sp.getBoolean(SettingsActivity.KEY_NOT, false);
        Boolean switchPref1 = sp.getBoolean(SettingsActivity.KEY_SOUND, false);
        Boolean switchPref2 = sp.getBoolean(SettingsActivity.KEY_VIBRATION, false);
        PreferenceManager.setDefaultValues(this, R.xml.fragment_settings, false);
        SharedPreferences.Editor editor = getSharedPreferences(PREF_NAME,0).edit();
        editor.putString("lang",idiomPref);
        editor.commit();
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

    @Override
    protected void onResume() {
        super.onResume();
        PreferenceManager.getDefaultSharedPreferences(this).registerOnSharedPreferenceChangeListener(listener);
    }



    @Override
    protected void onPause() {
        super.onPause();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(listener);

    }

    private void restartThis() {
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }


}
