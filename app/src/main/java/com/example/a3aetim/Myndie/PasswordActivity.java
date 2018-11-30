package com.example.a3aetim.Myndie;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.a3aetim.Myndie.Connection.AppConfig;

public class PasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        WebView wv = (WebView)findViewById(R.id.webv);
        WebSettings ws = wv.getSettings();
        ws.setSupportZoom(false);
        ws.setJavaScriptEnabled(true);


        wv.loadUrl(AppConfig.URL_RecuperarSenha);

        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                Toast.makeText(PasswordActivity.this, "Um e-mail de recuperação da senha foi enviado para você!!", Toast.LENGTH_SHORT).show();
                finalizar();
                return false;
            }
        });
    }

    public void finalizar(){
        this.finish();
    }
}
