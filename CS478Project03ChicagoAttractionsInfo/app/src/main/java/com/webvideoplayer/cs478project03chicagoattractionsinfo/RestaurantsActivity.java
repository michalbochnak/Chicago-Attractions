//
// Michal Bochnak
// CS 478, Project #3
// UIC, April 2, 2018
//


package com.webvideoplayer.cs478project03chicagoattractionsinfo;

import android.os.Bundle;


//
// List the restaurants and fills the URL array with links to them
//
public class RestaurantsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUrlArray(R.array.restaurants_urls);
    }

    public int getArrayId() {
        return R.array.restaurants;
    }
}
