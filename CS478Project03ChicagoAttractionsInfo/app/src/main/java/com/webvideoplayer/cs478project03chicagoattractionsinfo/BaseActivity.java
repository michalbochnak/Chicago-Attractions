//
// Michal Bochnak
// CS 478, Project #3
// UIC, April 2, 2018
//


package com.webvideoplayer.cs478project03chicagoattractionsinfo;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import com.webvideoplayer.cs478project03chicagoattractionsinfo.MyListFragment.CommunicationInterface;


//
// Class template for the restaurants and the attractions subclasses
// Handles most of the work that both subclasses need
//
public abstract class BaseActivity extends AppCompatActivity implements
        CommunicationInterface {

    private static final String LIST_FRAGM_TAG = "listFragment";
    private static final String WEB_FRAGM_TAG = "webViewFragment";
    private static String TAG = "BaseActivity";
    private String mode = "list";
    private int index = -1;
    private MyListFragment listFragment;
    private WebViewFragment webViewFragment;
    private FragmentManager fragmentManager;
    private FrameLayout listFrameLayout;
    private FrameLayout webViewFrameLayout;
    private String[] URLs;

    @Override
    // override default menu and inflate with menu options
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return  true;
    }

    @Override
    public void onBackPressed() {
        determineSetup("list");
    }

    @Override
    public void onItemSelection(int i) {
        index = i;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        // add fragment for web view
        if(!webViewFragment.isAdded()) {
            ft.add(R.id.web_view_fragment_container, webViewFragment, WEB_FRAGM_TAG);
        }
        else {
            ft.show(webViewFragment);
        }

        // add to stack
        ft.addToBackStack(null);
        ft.commit();
        fragmentManager.executePendingTransactions();

        determineSetup("webview");
        // show / reload the page
        webViewFragment.openURL(URLs[index]);
    }

    @Override
    // handle click from menu
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.attractions_menu_option:
                startActivity(new Intent(this, AttractionsActivity.class));
                return true;
            case R.id.restaurants_menu_option:
                startActivity(new Intent(this, RestaurantsActivity.class));
                return true;
            case R.id.exit_menu_item:
                exitApp();
        }

        return false;
    }

    @Override
    public void onResume(){
        super.onResume();
        // determine which activity should ber launched
        launchActivity(getIntent());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignVariables();

        listFragment = (MyListFragment)fragmentManager.findFragmentByTag
                (LIST_FRAGM_TAG);
        webViewFragment = (WebViewFragment)fragmentManager.findFragmentByTag
                (WEB_FRAGM_TAG);

        if (listFragment == null) {
            listFragment = new MyListFragment();
        }
        if (webViewFragment == null) {
            webViewFragment = new WebViewFragment();
        }

        addListFragment();

        // check if there is bundle for saved instance
        if (savedInstanceState != null) {
            determineSetup((String)savedInstanceState.getSerializable("mode"));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("mode", mode);
        outState.putSerializable("index", index);
    }

    // get appropriate array to fill the list
    protected void setUrlArray(int resourceId) {
        this.URLs = getResources().getStringArray(resourceId);
    }

    private void exitApp() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory( Intent.CATEGORY_HOME );
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void assignVariables() {
        URLs = getResources().getStringArray(R.array
                .attractions_urls);
        listFrameLayout = (FrameLayout)findViewById(R.id
                .list_fragment_container);
        webViewFrameLayout = (FrameLayout)findViewById(R.id
                .web_view_fragment_container);
        fragmentManager = getFragmentManager();
    }

    private void addListFragment() {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.list_fragment_container, listFragment, LIST_FRAGM_TAG);
        ft.commit();
        // reset layout when back stack changes
        fragmentManager
                .addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        listFragmentVisible();
                    }
                });
    }

    private void portraitSetup(String visible) {
        if (visible.equals("list"))
            listFragmentVisible();
        else if (visible.equals("webview")) {
            webviewVisible();
        }
    }

    private void landscapeSetup(String mode) {
        if (mode.equals("list")) {
            listFragmentVisible();
        }
        else if (mode.equals("webview")) {
            bothFragmentsVisible();
        }
    }

    private void bothFragmentsVisible() {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, MATCH_PARENT);
        lp.weight = 1;
        listFrameLayout.setLayoutParams(lp);
        lp = new LinearLayout.LayoutParams(0, MATCH_PARENT);
        lp.weight = 2;
        webViewFrameLayout.setLayoutParams(lp);
        mode = "webview";
    }

    private void listFragmentVisible() {
        listFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                MATCH_PARENT, MATCH_PARENT));
        webViewFrameLayout.setLayoutParams(new LinearLayout.LayoutParams
                (0, MATCH_PARENT));
        mode = "list";
    }

    private void webviewVisible() {
        listFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(
                0, MATCH_PARENT));
        webViewFrameLayout.setLayoutParams(new LinearLayout.LayoutParams
                (MATCH_PARENT, MATCH_PARENT));
        mode = "webview";
    }

    private void launchActivity(Intent intent) {
        // run attractions by default if no intent was sent
        if (intent.hasExtra("activity")) {
            String activity = intent.getStringExtra("activity");
            if (activity.equals("restaurants")) {
                startActivity(new Intent(this, RestaurantsActivity.class));
            }
            else {
                startActivity(new Intent(this, AttractionsActivity.class));
            }
        }
    }

    private void determineSetup(String mode) {
        if (getResources().getConfiguration().orientation == Configuration
                .ORIENTATION_PORTRAIT) {

            portraitSetup(mode);
        }
        else {
            landscapeSetup(mode);
        }
    }

}   // BaseActivity class
