package com.example.a3aetim.Myndie.Fragments;


import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;

import com.example.a3aetim.Myndie.R;
import com.example.a3aetim.Myndie.SettingsActivity;

import java.util.Locale;

import static com.example.a3aetim.Myndie.Splash.PREF_NAME;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    public static final String KEY_LANGUAGE = "selec_lang";
    String idiomPref="";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

    }
    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        setPreferencesFromResource(R.xml.fragment_settings, s);
    }




}
