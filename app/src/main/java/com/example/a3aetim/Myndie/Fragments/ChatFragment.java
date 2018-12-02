package com.example.a3aetim.Myndie.Fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.a3aetim.Myndie.Classes.User;
import com.example.a3aetim.Myndie.Connection.AppConfig;
import com.example.a3aetim.Myndie.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;


public class ChatFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_chatonline, container, false);

        final WebView wv = (WebView)v.findViewById(R.id.webv1);
        WebSettings ws = wv.getSettings();
        ws.setSupportZoom(false);
        ws.setJavaScriptEnabled(true);
        wv.setBackgroundColor(getResources().getColor(R.color.colorBackground));
        String username = getArguments().getString("username");
        String password = getArguments().getString("password");
        String url = AppConfig.URL_ChatOnline + "?Username="+username+"&Password="+password;

        wv.loadUrl(url);

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                //finalizar();
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                ProgressBar pb = (ProgressBar)v.findViewById(R.id.pgbarchat);
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                ProgressBar pb = (ProgressBar)v.findViewById(R.id.pgbarchat);
                pb.setVisibility(View.INVISIBLE);
                wv.setVisibility(View.VISIBLE);
            }
        });

        return v;
    }






    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


}
