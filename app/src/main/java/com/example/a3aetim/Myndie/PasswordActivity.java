package com.example.a3aetim.Myndie;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.a3aetim.Myndie.Connection.AppConfig;

public class PasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        final WebView wv = (WebView)findViewById(R.id.webv);
        WebSettings ws = wv.getSettings();
        ws.setSupportZoom(false);
        ws.setJavaScriptEnabled(true);
        wv.setBackgroundColor(getResources().getColor(R.color.colorBackground));


        wv.loadUrl(AppConfig.URL_RecuperarSenha);

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Toast.makeText(PasswordActivity.this, getString(R.string.change_password), Toast.LENGTH_SHORT).show();
                finalizar();
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                ProgressBar pb = (ProgressBar)findViewById(R.id.pgbarpass);
                wv.setVisibility(View.INVISIBLE);
                pb.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                ProgressBar pb = (ProgressBar)findViewById(R.id.pgbarpass);
                pb.setVisibility(View.INVISIBLE);
                wv.setVisibility(View.VISIBLE);
            }
        });
    }

    public void finalizar(){
        this.finish();
    }
}
