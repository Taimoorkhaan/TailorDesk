package com.digiconvalley.tailordesk.DrawerActivities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.digiconvalley.tailordesk.R;

public class Settings extends AppCompatActivity {
    private ImageView backBtn;
    private Button btnCm,btnInches;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        backBtn=findViewById(R.id.back_button_setting);
        btnCm=findViewById(R.id.btn_settings_CM);
        btnInches=findViewById(R.id.btn_settings_Inch);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnInches.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                btnInches.setBackgroundResource(R.drawable.button_bg);
                btnCm.setBackgroundResource(R.drawable.bg_faq);
                btnCm.setTextColor(getResources().getColor(R.color.app_color2));
                btnInches.setTextColor(getResources().getColor(R.color.white));
            }
        });
        btnCm.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onClick(View view) {
                btnCm.setBackgroundResource(R.drawable.button_bg);
                btnInches.setBackgroundResource(R.drawable.bg_faq);
                btnCm.setTextColor(getResources().getColor(R.color.white));
                btnInches.setTextColor(R.color.app_color);
            }
        });
    }
}