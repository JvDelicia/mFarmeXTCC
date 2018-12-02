package com.example.a3aetim.Myndie;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a3aetim.Myndie.Classes.ImageDAO;
import com.example.a3aetim.Myndie.Classes.User;
import com.example.a3aetim.Myndie.Images.DownloadImage;

import es.dmoral.toasty.Toasty;

public class ProfileActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbar;
    private AppBarLayout appBarLayout;

    TextView mEmail,mCrtDate,mCountry,mLang;
    private Menu collapsedMenu;
    private boolean appBarExpanded = true;

    private User loggedUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        loggedUser = (User) getIntent().getSerializableExtra("ProfileUser");
        final Toolbar toolbar = (Toolbar) findViewById(R.id.anim_toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
       // mCountry = (TextView)findViewById(R.id.txtCountryProfile);
        mEmail = (TextView)findViewById(R.id.txtEmailProfile);
        mCrtDate = (TextView)findViewById(R.id.txtCreationDateProfile);
        //mLang = (TextView)findViewById(R.id.txtLanguageProfile);
        collapsingToolbar.setTitle(loggedUser.getNameUser());
        collapsingToolbar.setExpandedTitleColor(Color.WHITE);
        collapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);
        ImageView headerProfile = (ImageView)findViewById(R.id.header);
        new DownloadImage(headerProfile).execute(loggedUser.getPicUser());
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.d(ProfileActivity.class.getSimpleName(), "onOffsetChanged: verticalOffset: " + verticalOffset);

                //  Vertical offset == 0 indicates appBar is fully expanded.
                if (Math.abs(verticalOffset) > 200) {
                    appBarExpanded = false;
                    invalidateOptionsMenu();
                } else {
                    appBarExpanded = true;
                    collapsingToolbar.setPadding(0,10,0,0);
                    invalidateOptionsMenu();
                }
            }
        });
        mCrtDate.setText("Created at:"+String.valueOf(loggedUser.getCrtDateUser()));
        //mLang.setText(String.valueOf(loggedUser.getIdLang()));
        mEmail.setText(String.valueOf(loggedUser.getEmailUser()));
        //mCountry.setText(String.valueOf(loggedUser.getCountryUser()));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        collapsedMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            /*case R.id.action_settings:
                return true;*/
        }
        if (item.getTitle() == "Add") {
            Toasty.info(this, "clicked add", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}