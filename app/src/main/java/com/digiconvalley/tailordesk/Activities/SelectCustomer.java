package com.digiconvalley.tailordesk.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.digiconvalley.tailordesk.R;


public class SelectCustomer extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    ImageButton option_menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_customer);
        option_menu = findViewById(R.id.option_menu);
        option_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(SelectCustomer.this, view);
                popupMenu.setOnMenuItemClickListener(SelectCustomer.this);
                popupMenu.inflate(R.menu.menu);
                popupMenu.show();
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.add_new_conatct:
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
            case R.id.select_phone_contact:
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}