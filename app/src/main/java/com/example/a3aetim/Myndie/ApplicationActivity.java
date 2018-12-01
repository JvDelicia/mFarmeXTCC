package com.example.a3aetim.Myndie;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.a3aetim.Myndie.Classes.Application;
import com.example.a3aetim.Myndie.Classes.Image;
import com.example.a3aetim.Myndie.Classes.User;
import com.example.a3aetim.Myndie.Classes.UserDAO;
import com.example.a3aetim.Myndie.Connection.AppConfig;
import com.example.a3aetim.Myndie.Connection.AppController;
import com.example.a3aetim.Myndie.ViewHolder.CollapsedViewHolder;
import com.example.a3aetim.Myndie.ViewHolder.ExpandableViewHolder;
import com.sysdata.widget.accordion.ExpandableItemHolder;
import com.sysdata.widget.accordion.FancyAccordionView;
import com.sysdata.widget.accordion.Item;
import com.sysdata.widget.accordion.ItemAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;



import static com.example.a3aetim.Myndie.Connection.AppController.TAG;

public class ApplicationActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    TextView mTitle,mVersion, mPrice, mPublisherName, mReleaseDate;
    Application app;
    FancyAccordionView mDesc;
    private Toolbar toolbar;
    private SliderLayout mSlider;
    private String KEY_EXPANDED_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        mPrice =(TextView) findViewById(R.id.txtPriceApp);
        mTitle =(TextView)findViewById(R.id.txtTitleApp);
        mVersion =(TextView)findViewById(R.id.txtVersionApp);
        mDesc =(FancyAccordionView) findViewById(R.id.txtDescApp);
        mPublisherName = (TextView)findViewById(R.id.txtPublisherNameApp);
        mReleaseDate = (TextView)findViewById(R.id.txtReleaseDateApp);
        mSlider = (SliderLayout)findViewById(R.id.sliderApplication);
        app  =(Application) getIntent().getSerializableExtra("App");
        ImageButton back1 = (ImageButton) findViewById(R.id.backToMain);
        back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ItemAdapter.OnItemClickedListener mListener = new ItemAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(ItemAdapter.ItemViewHolder<?> viewHolder, int id) {
                ItemAdapter.ItemHolder itemHolder = viewHolder.getItemHolder();
                Application item = ((Application) itemHolder.item);

                switch (id) {
                    case ItemAdapter.OnItemClickedListener.ACTION_ID_COLLAPSED_VIEW:
                        break;
                    case ItemAdapter.OnItemClickedListener.ACTION_ID_EXPANDED_VIEW:
                        break;
                    default:
                        // do nothing
                        break;
                }
            }
        };
        // bind the factory to create view holder for item collapsed
        mDesc.setCollapsedViewHolderFactory(CollapsedViewHolder.Factory.create(R.layout.layout_collapsed), mListener);
        // bind the factory to create view holder for item expanded
        mDesc.setExpandedViewHolderFactory(ExpandableViewHolder.Factory.create(R.layout.layout_expanded), mListener);
        getImages();
        // restore the expanded item from state
        if (savedInstanceState != null) {
            mDesc.setExpandedItemId(savedInstanceState.getLong(KEY_EXPANDED_ID, Item.INVALID_ID));
        }
        preencherCampos();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.applicationmenu, menu);
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
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void preencherCampos() {
        mTitle.setText(app.getTitle());
        mVersion.setText(getString(R.string.app_activity_version)+" "+app.getVersion());
        mPublisherName.setText(getString(R.string.app_activity_publisher)+" "+app.getPublisherName());
        mReleaseDate.setText(getString(R.string.app_activity_releasedate)+" "+ app.getReleaseDate() );
        mPrice.setText(getString(R.string.app_activity_price)+" "+"US$ "+String.valueOf(app.getPrice()));
        preencherDesc();
    }

    private void preencherDesc() {
        final int dataCount = 1;

        final List<ExpandableItemHolder> itemHolders = new ArrayList<>(dataCount);
        Item itemModel;
        ExpandableItemHolder itemHolder;
        itemModel = new Application(app);
        itemHolder = new ExpandableItemHolder(itemModel);
        itemHolders.add(itemHolder);
        mDesc.setAdapterItems(itemHolders);
    }

    private void getImages() {
        final ArrayList arrayList = new ArrayList();
        // Tag used to cancel the request
        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.URL_ConsultaImagensApp, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray listaAplicativosResponse = new JSONArray(response);

                    for (int i = 0; i < listaAplicativosResponse.length(); i++) {
                        JSONObject jsonObjectApp = listaAplicativosResponse.getJSONObject(i);

                        String imageURL = jsonObjectApp.getString("Url");
                        imageURL = "https://myndie.azurewebsites.net/"+imageURL;
                        arrayList.add(imageURL);
                    }
                    preencherSlider(arrayList);
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();

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
                params.put("idapp", String.valueOf(app.get_IdApp()));

                return params;
            }

        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }

    public void preencherSlider(final ArrayList arrayList){
        ArrayList<Bitmap> fotos = new ArrayList<>();
        /*for(int i = 0 ; i < arrayList.size(); i++){
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(arrayList.get(i).toString()).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            fotos.add(bmp);
        }
*/
        HashMap<String,String> file_maps = new HashMap<String, String>();
        for(int i = 0 ; i < arrayList.size(); i++){
            file_maps.put("Imagem "+(i+1), String.valueOf(arrayList.get(i)));
        }

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    //.description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.FitCenterCrop)
                    .setOnSliderClickListener(this);
            //add your extra information
            /*textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);*/

            mSlider.addSlider(textSliderView);
        }

        mSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.stopAutoCycle(); //Ele não fica rodando sozinho
        //mSlider.setCustomAnimation(new DescriptionAnimation());
        //mSlider.setDuration(4000);
        mSlider.addOnPageChangeListener(this);
    }

    @Override
    protected void onStop() {
        //Importante para não sobrecarregar a memória do celular (ficaria rodando no fundo)
        mSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
