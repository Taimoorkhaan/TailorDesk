package com.digiconvalley.tailordesk.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;


import com.digiconvalley.tailordesk.Activities.SalesAndPackages.TailorShopPackages_Activity;
import com.digiconvalley.tailordesk.Adapter.DrawerrAdapter;
import com.digiconvalley.tailordesk.Adapter.PagerAdapter;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.DrawerActivities.BackUp;
import com.digiconvalley.tailordesk.DrawerActivities.FAQs;
import com.digiconvalley.tailordesk.DrawerActivities.Profile;
import com.digiconvalley.tailordesk.DrawerActivities.Settings;
import com.digiconvalley.tailordesk.DrawerActivities.Support;
import com.digiconvalley.tailordesk.DrawerActivities.TailorCustomers_Activity;
import com.digiconvalley.tailordesk.DrawerActivities.Team;
import com.digiconvalley.tailordesk.Fragment.GalleryFragment;
import com.digiconvalley.tailordesk.Fragment.OrderFragment;
import com.digiconvalley.tailordesk.Fragment.OrdersFragment;
import com.digiconvalley.tailordesk.Fragment.ReportFragment;
import com.digiconvalley.tailordesk.Fragment.TasksFragment;
import com.digiconvalley.tailordesk.Model.DrawerItems;
import com.digiconvalley.tailordesk.Model.TailorProfile;
import com.digiconvalley.tailordesk.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Objects;


public class MainHome_Activity extends AppCompatActivity implements DrawerrAdapter.DrawerListener {

    TabLayout tabLayout;
    BottomNavigationView bottomNavigationView;
    ImageButton add_btn;
    ImageView imageView;
    ViewPager viewPager;
    TabItem itemactive,itempostdue,itemupcoming;
    PagerAdapter pagerAdapter;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private TailorProfile tailorProfile;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private FrameLayout mainHook;
    private RecyclerView drawerRcv;
    private ArrayList<String> strings;
    private ArrayList<DrawerItems> drawerItems=new ArrayList<>();
    private TextView headerName;
    public static Boolean active=false;
    public static TextView toolBarText;
    public static ImageView toolBarImg;
    Deque<Integer> integerDeque=new ArrayDeque<>(4);
    private boolean flag=true;
    private FragmentTransaction transaction;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_screen);

        strings=new ArrayList<>();
        mainHook=findViewById(R.id.MainHook);
        toolbar=findViewById(R.id.toolbar);
        bottomNavigationView= findViewById(R.id.bottom_nav);
        navigationView=findViewById(R.id.nav_drawer);
        drawerLayout=findViewById(R.id.drawerMain);
        imageView = findViewById(R.id.drawerIcon);
        toolBarImg=findViewById(R.id.toolBarImg);
        toolBarText=findViewById(R.id.toolBarText);

        integerDeque.push(R.id.bottom_order);


     Gson gson=new Gson();
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        StaticData.tailorProfile= gson.fromJson(pref.getString("TailorBasicInfo",null), TailorProfile.class);

//        Toast.makeText(this, ""+StaticData.tailorProfile.getTailorShopId(), Toast.LENGTH_SHORT).show();

       drawerRcv=findViewById(R.id.drawerRcv);
       headerName=findViewById(R.id.headerName);


       setSupportActionBar(toolbar);
       //THree dot icon changing
        toolbar.setOverflowIcon(ContextCompat.getDrawable(getApplicationContext(),R.drawable.plus_sign));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
      //  setOverflowButtonColor(toolbar,R.color.white);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.open, R.string.close);
        toggle.setDrawerIndicatorEnabled(false);
        //drawer.setDrawerListener(toggle);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        drawerItems.add(new DrawerItems("Profile",R.drawable.drawer_person));
        drawerItems.add(new DrawerItems("Customer List",R.drawable.drawer_customers));
        drawerItems.add(new DrawerItems("Team",R.drawable.drawer_team));
        drawerItems.add(new DrawerItems("Packages",R.drawable.drawer_package));
        drawerItems.add(new DrawerItems("Backup",R.drawable.drawer_backup));
        drawerItems.add(new DrawerItems("Support",R.drawable.drawer_support));
        drawerItems.add(new DrawerItems("F.A.Q",R.drawable.drawer_faq));
        drawerItems.add(new DrawerItems("Settings",R.drawable.drawer_setting));
        drawerItems.add(new DrawerItems("Logout",R.drawable.drawer_logout));


        drawerRcv.setLayoutManager(new LinearLayoutManager(this));
        DrawerrAdapter drawerAdapter=new DrawerrAdapter(this,drawerItems,this);
        drawerRcv.setAdapter(drawerAdapter);

        transaction = getSupportFragmentManager().beginTransaction();


        if(getIntent()!=null && getIntent().hasExtra("Fragment")){
            loadFragment(new GalleryFragment());
          /*  GalleryFragment galleryFragment=new GalleryFragment();
            transaction.replace(R.id.MainHook, galleryFragment).addToBackStack(null).commit();*/
        }
        else{
         //   Toast.makeText(this, "I am here", Toast.LENGTH_SHORT).show();
          //  loadFragment(new OrderFragment());
            loadFragment(new OrderFragment());

        }

        bottomNavigationView.setSelectedItemId(R.id.bottom_order);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else
                    drawerLayout.openDrawer(GravityCompat.START);
            }

        });


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                int id=menuItem.getItemId();

                if(integerDeque.contains(id)){
                    if(id==R.id.bottom_order){
                        if(integerDeque.size()!=1){
                            if(flag){
                                integerDeque.addFirst(R.id.bottom_order);
                                flag=false;
                            }
                        }
                    }
                    integerDeque.remove(id);
                }
                integerDeque.push(id);
                loadFragment(getFragment(id));
                return true;
            }
        });


    }
    public void showPopup(View v) {

        PopupMenu popup = new PopupMenu(this, v);
        final MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.home_main_menu, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.add_new_conatct){
                    Intent intent=new Intent(MainHome_Activity.this,AddCustomer.class);
                    startActivity(intent);
                }

                if(menuItem.getItemId()==R.id.show_customer){

                    Intent intent=new Intent(MainHome_Activity.this,CustomerHomeActivity.class);
                    startActivity(intent);
                }
                return false;
            }
        });


    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        int count = getSupportFragmentManager().getBackStackEntryCount();

        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);*/
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (drawerLayout.isDrawerOpen(GravityCompat.END)) {
            drawerLayout.closeDrawer(GravityCompat.END);
        }
        else {
            integerDeque.pop();
            if(!integerDeque.isEmpty()){
                loadFragment(getFragment(integerDeque.peek()));

            }
        /*    else if(integerDeque.getLast()==0){
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }*/
            else {
                finish();
            }
          /*  if (count == 1) {
                if (StaticData.createOrders.size() > 0)
                    StaticData.createOrders.clear();
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
                //additional code
            } else {

                if(active){
                    Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                    homeIntent.addCategory(Intent.CATEGORY_HOME);
                    homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(homeIntent);
                }
                else {

                     getSupportFragmentManager().popBackStack();
                     //getSupportFragmentManager().getPrimaryNavigationFragment();
                }
            }*/

        }
    }




    @Override
    public void itemClicked(DrawerItems drawerItems, int index) {
        switch (index){
            case 0:{
             //   Toast.makeText(this, "Tailor id ="+StaticData.tailorProfile.getTailorID(), Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(MainHome_Activity.this, Profile.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case 1:{

                drawerLayout.closeDrawer(GravityCompat.START);
                Intent intent=new Intent(MainHome_Activity.this, TailorCustomers_Activity.class);
                startActivity(intent);
                break;

            }
            case 2:{
                Intent intent=new Intent(MainHome_Activity.this, Team.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case 3:{
                Intent intent=new Intent(MainHome_Activity.this, TailorShopPackages_Activity.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case 4:{
                Intent intent=new Intent(MainHome_Activity.this, BackUp.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case 5:{
                Intent intent=new Intent(MainHome_Activity.this, Support.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case 6:{
                Intent intent=new Intent(MainHome_Activity.this, FAQs.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case 7:{
                Intent intent=new Intent(MainHome_Activity.this, Settings.class);
                startActivity(intent);
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            }
            case 8:{

                pref.edit().remove("Loged").commit();
                pref.edit().remove("Signup").commit();

                Intent intent=new Intent(MainHome_Activity.this,SignUp_Activity.class);
                startActivity(intent);
                finish();
                break;
            }
        }
    }

    public Fragment getFragment(int menuItem){
        switch (menuItem){
            case R.id.bottom_order:
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                OrderFragment orderFragmentt=new OrderFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                return orderFragmentt;

            case R.id.bottom_task:
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                TasksFragment tasksFragment=new TasksFragment();
                drawerLayout.closeDrawer(GravityCompat.START);
                return tasksFragment;

            case R.id.bottom_gallery:
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                GalleryFragment galleryFragment=new GalleryFragment();
                drawerLayout.closeDrawer(GravityCompat.START);

                return galleryFragment;

            case R.id.bottom_report:
                bottomNavigationView.getMenu().getItem(3).setChecked(true);
                ReportFragment reportFragment=new ReportFragment();

                drawerLayout.closeDrawer(GravityCompat.START);

                return reportFragment;
        }
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        return new OrdersFragment();
    }
    public void loadFragment(Fragment fragment){

        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.MainHook, fragment);
        transaction.commit();
    }
}