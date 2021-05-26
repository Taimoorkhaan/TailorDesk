package com.digiconvalley.tailordesk.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.digiconvalley.tailordesk.Adapter.SliderAdapter;
import com.digiconvalley.tailordesk.R;


public class SliderViewActivity extends AppCompatActivity {

    ViewPager viewPager;
    SliderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_view);
        viewPager = findViewById(R.id.viewpager);

        adapter = new SliderAdapter(this);
        viewPager.setAdapter(adapter);

    }
}