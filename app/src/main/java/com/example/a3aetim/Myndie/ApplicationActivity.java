package com.example.a3aetim.Myndie;

import android.app.SearchManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.example.a3aetim.Myndie.Classes.Application;
import com.example.a3aetim.Myndie.Classes.User;
import com.example.a3aetim.Myndie.Classes.UserDAO;
import com.example.a3aetim.Myndie.ViewHolder.CollapsedViewHolder;
import com.example.a3aetim.Myndie.ViewHolder.ExpandableViewHolder;
import com.sysdata.widget.accordion.ExpandableItemHolder;
import com.sysdata.widget.accordion.FancyAccordionView;
import com.sysdata.widget.accordion.Item;
import com.sysdata.widget.accordion.ItemAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    TextView mTitle,mPrice,mVersion, mPublisherName, mReleaseDate;
    Application app;
    FancyAccordionView mDesc;
    private Toolbar toolbar;
    private SliderLayout mSlider;
    private String KEY_EXPANDED_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        mPrice =(TextView)findViewById(R.id.txtPriceApp);
        mTitle =(TextView)findViewById(R.id.txtTitleApp);
        mVersion =(TextView)findViewById(R.id.txtVersionApp);
        mDesc =(FancyAccordionView) findViewById(R.id.txtDescApp);
        mPublisherName = (TextView)findViewById(R.id.txtPublisherNameApp);
        mReleaseDate = (TextView)findViewById(R.id.txtReleaseDateApp);
        mSlider = (SliderLayout)findViewById(R.id.sliderApplication);

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
        // restore the expanded item from state
        if (savedInstanceState != null) {
            mDesc.setExpandedItemId(savedInstanceState.getLong(KEY_EXPANDED_ID, Item.INVALID_ID));
        }
        preencherSlider();
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

    public void preencherCampos(){
        app  =(Application) getIntent().getSerializableExtra("App");
        mTitle.setText(app.getTitle());
        mVersion.setText(app.getVersion());
        mPublisherName.setText(app.getPublisherName());
        mReleaseDate.setText(String.valueOf(app.getReleaseDate()));
        mPrice.setText("R$ "+String.valueOf(app.getPrice()));
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

    public void preencherSlider(){
        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Descrição Imagem 1",R.drawable.ic_error_outline_white_48dp);
        file_maps.put("Descrição Imagem 2", R.drawable.ic_launcher_foreground);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    //.description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
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
