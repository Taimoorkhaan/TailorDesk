package com.digiconvalley.tailordesk.Activities.SalesAndPackages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;

import com.digiconvalley.tailordesk.Adapter.SalesandPackages.SalesPagerAdapter;
import com.digiconvalley.tailordesk.R;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class Tailor_Sales_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor__sales_);

        ViewPager2 viewPager2 = findViewById(R.id.view_pager);
        viewPager2.setAdapter(new SalesPagerAdapter(this));

        TabLayout tabLayout = findViewById(R.id.tab_layout);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

                switch (position){
                    case 0: {
                        tab.setText("NEW ORDERS (17)");
                        break;
                    }

                    case 1: {
                        tab.setText("DELIVERED ORDERS");
                        break;
                    }


                }

            }
        });
        tabLayoutMediator.attach();
    }
}