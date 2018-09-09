package com.webvideoplayer.cs478_project03_chicagoattractionshelper;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final String PERMISSION = "edu.uic.cs478.sp18.project3";
    private String intent = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setButtonListeners();
    }

    private void setButtonListeners() {
        setAttractionButtonListener();
        setRestaurantsButtonListener();
    }

    private void setAttractionButtonListener() {
        findViewById(R.id.attractions_button).setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),
                                "Attractions selected", Toast.LENGTH_SHORT).show();

                        intent = PERMISSION + ".attractions";
                        verifyPermissionAndBroadcast();
                        Log.i("MainAct", intent);
                    }
                });
    }

    private void setRestaurantsButtonListener() {
        findViewById(R.id.restaurants_button).setOnClickListener
                (new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getApplicationContext(),
                                "Restaurants selected", Toast.LENGTH_SHORT).show();

                        intent = PERMISSION + ".restaurants";
                        verifyPermissionAndBroadcast();
                    }
                });
    }

    private void verifyPermissionAndBroadcast() {
        // permission granted, proceed
        if (ContextCompat.checkSelfPermission(this, PERMISSION)
                == PackageManager.PERMISSION_GRANTED) {
            sendBroadcast(new Intent(intent));
        }
        else {
            ActivityCompat.requestPermissions(this, new String[]{PERMISSION}, 0);
        }
    }

    public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        if (results.length > 0) {
            // permission granted, proceed
            if (results[0] == PackageManager.PERMISSION_GRANTED) {
                sendBroadcast(new Intent(intent));
            }
            else {
                Toast.makeText(this, "Permission not granted 2.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
