package com.digiconvalley.tailordesk.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import com.digiconvalley.tailordesk.Activities.SalesAndPackages.Tailor_Sales_Activity;
import com.digiconvalley.tailordesk.Model.TailorProfile;
import com.digiconvalley.tailordesk.R;
import com.google.gson.Gson;

import java.util.Timer;
import java.util.TimerTask;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class Splash_Activity extends AppCompatActivity {
    private Timer timer;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private TailorProfile tailorProfile;
    private final int REQUEST_CODE_COARSE=100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        timer = new Timer();
        ActivityCompat.requestPermissions(Splash_Activity.this, new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, REQUEST_CODE_COARSE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case REQUEST_CODE_COARSE:

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && ((grantResults[0] + grantResults[1])
                        == PackageManager.PERMISSION_GRANTED
                )) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    startNextAction();

                }
                else {
                    ActivityCompat.requestPermissions(Splash_Activity.this, new String[]{ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION}, REQUEST_CODE_COARSE);
                }
        }
    }

    private void startNextAction() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                if (pref.contains("Loged") && pref.contains("Signup")) {
                    if (pref.getString("Loged", null).equalsIgnoreCase("yes") &&
                            pref.getString("Signup", null).equalsIgnoreCase("yes")) {

                        Intent intent = new Intent(getApplicationContext(), MainHome_Activity.class);
                        //  Intent intent = new Intent(getApplicationContext(), Tailor_Sales_Activity.class);
                        pref.contains("TailorBasicInfo");
                        Gson gson=new Gson();

                        tailorProfile=gson.fromJson(pref.getString("TailorBasicInfo","a"),TailorProfile.class);
                        startActivity(intent);
                        finish();

                    } else if (pref.getString("Loged", null).equalsIgnoreCase("yes")) {


                        Intent intent = new Intent(getApplicationContext(), SetupDetail01.class);
                        startActivity(intent);
                        finish();
                    } else if (pref.getString("Signup", null).equalsIgnoreCase("yes")) {



                        Intent intent = new Intent(getApplicationContext(), MainHome_Activity.class);
                        //    Intent intent = new Intent(getApplicationContext(), Tailor_Sales_Activity.class);
                        startActivity(intent);
                        finish();
                    }
                } else {


                    Intent intent = new Intent(getApplicationContext(), OnBoardingScreen.class);
                    startActivity(intent);
                    finish();
                }
            }

        }, 3000);

    }
}