package com.digiconvalley.tailordesk.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.digiconvalley.tailordesk.DrawerActivities.BackUp;
import com.digiconvalley.tailordesk.DrawerActivities.FAQs;
import com.digiconvalley.tailordesk.DrawerActivities.Profile;
import com.digiconvalley.tailordesk.DrawerActivities.Settings;
import com.digiconvalley.tailordesk.DrawerActivities.Support;
import com.digiconvalley.tailordesk.DrawerActivities.Team;
import com.digiconvalley.tailordesk.R;
import com.google.android.material.navigation.NavigationView;

public class NavigationDrawer extends AppCompatActivity {
    private NavigationView nav;
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle drawerToggle;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        nav = findViewById(R.id.navMenu);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerlayout = findViewById(R.id.drawer);
        drawerToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_nav_icon);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.mProfile:
                        Toast.makeText(NavigationDrawer.this, "Profile", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Profile.class);
                        startActivity(intent);
                        mDrawerlayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.mCustomerList:
                        Toast.makeText(NavigationDrawer.this, "Customer", Toast.LENGTH_SHORT).show();
                        Intent intent2 = new Intent(getApplicationContext(), Customer_List.class);
                        startActivity(intent2);
                        mDrawerlayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.mTeam:
                        Toast.makeText(NavigationDrawer.this, "Team", Toast.LENGTH_SHORT).show();
                        Intent intent3 = new Intent(getApplicationContext(), Team.class);
                        startActivity(intent3);
                        mDrawerlayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.mBackup:
                        Toast.makeText(NavigationDrawer.this, "BackUp", Toast.LENGTH_SHORT).show();
                        Intent intent4 = new Intent(getApplicationContext(), BackUp.class);
                        startActivity(intent4);
                        mDrawerlayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.mSupport:
                        Toast.makeText(NavigationDrawer.this, "Support", Toast.LENGTH_SHORT).show();
                        Intent intent5 = new Intent(getApplicationContext(), Support.class);
                        startActivity(intent5);
                        mDrawerlayout.closeDrawer(GravityCompat.START);
                        break;

                    case R.id.mFaq:
                        Toast.makeText(NavigationDrawer.this, "FAQ", Toast.LENGTH_SHORT).show();
                        Intent intent6 = new Intent(getApplicationContext(), FAQs.class);
                        startActivity(intent6);
                        mDrawerlayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.mSetting:
                        Toast.makeText(NavigationDrawer.this, "Setting", Toast.LENGTH_SHORT).show();
                        Intent intent7 = new Intent(getApplicationContext(), Settings.class);
                        startActivity(intent7);
                        mDrawerlayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.mLogout:
                        Toast.makeText(NavigationDrawer.this, "LogOut", Toast.LENGTH_SHORT).show();
                        Intent intent8 = new Intent(getApplicationContext(), Splash_Activity.class);
                        startActivity(intent8);
                        mDrawerlayout.closeDrawer(GravityCompat.START);
                        break;


                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}