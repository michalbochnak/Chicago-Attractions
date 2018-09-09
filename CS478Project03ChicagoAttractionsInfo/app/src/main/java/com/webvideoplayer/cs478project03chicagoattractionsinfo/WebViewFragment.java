//
// Michal Bochnak
// CS 478, Project #3
// UIC, April 2, 2018
//


package com.webvideoplayer.cs478project03chicagoattractionsinfo;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


//
// Fragment to display the content of the website
//
public class WebViewFragment extends Fragment {

    private static final String TAG = "WebViewFragment";
    private WebView wv;
    private String globalUrl = "";

    @Override
    public void onAttach(Context activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    // Called to create the content view for this Fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, getClass().getSimpleName() + ":onCreateView()");
        // Inflate the layout
        View v = inflater.inflate(R.layout.web_view_fragment, container,
                false);
        wv = (WebView)v.findViewById(R.id.webview_lay);
        if (!globalUrl.equals("")) {
            openURL(globalUrl);
        }
        return v;
    }

    // load URL website
    public void openURL(String url) {
        wv.loadUrl(url);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());
        globalUrl = url;
    }

}
