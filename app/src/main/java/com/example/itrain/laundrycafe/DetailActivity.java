package com.example.itrain.laundrycafe;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.JsonArrayRequest;

public class DetailActivity extends AppCompatActivity {

    String url = "https://sheetsu.com/apis/v1.0/e25e61445b37";
    public static final String EXTRA_POSITION = "position";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Set Collapsing Toolbar layout to the screen
        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        // Set title of Detail page
        collapsingToolbar.setTitle(getString(R.string.services));

        int postion = getIntent().getIntExtra(EXTRA_POSITION, 0);
        Resources resources = getResources();
        String[] places = resources.getStringArray(R.array.services);
        collapsingToolbar.setTitle(places[postion % places.length]);

        String[] placeDetails = resources.getStringArray(R.array.moredescription);
        TextView placeDetail = (TextView) findViewById(R.id.place_detail);
        placeDetail.setText(placeDetails[postion % placeDetails.length]);

        //String[] placeLocations = resources.getStringArray(R.array.place_locations);
        //TextView placeLocation =  (TextView) findViewById(R.id.place_location);
        //placeLocation.setText(placeLocations[postion % placeLocations.length]);

        TypedArray placePictures = resources.obtainTypedArray(R.array.picture);
        ImageView placePicutre = (ImageView) findViewById(R.id.image);
        placePicutre.setImageDrawable(placePictures.getDrawable(postion % placePictures.length()));

        placePictures.recycle();
    }
}
