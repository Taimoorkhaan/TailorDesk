package com.digiconvalley.tailordesk.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.digiconvalley.tailordesk.Model.TailorBasicProfile;
import com.digiconvalley.tailordesk.Model.TailorProfile;
import com.digiconvalley.tailordesk.R;
import com.google.gson.Gson;


public class SetupDetail01 extends AppCompatActivity {

    EditText name,shop_name,phone_number,email;
    Button Next_button;
    private String phoneNo;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private TailorProfile tailorProfile;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    public void onBackPressed() {

        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_detail01);

        name=findViewById(R.id.owner_name);
        shop_name=findViewById(R.id.owner_shop_name);
        email=findViewById(R.id.owner_email);
        phone_number=findViewById(R.id.owner_phone_number);
        Next_button=findViewById(R.id.next_btn);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        if(getIntent()!=null){
            phoneNo=getIntent().getStringExtra("Phone");
        }

        Gson gson=new Gson();
       tailorProfile= gson.fromJson(pref.getString("TailorBasicInfo",null),TailorProfile.class);

        phone_number.setText(tailorProfile.getTailorPhoneNo());
        phone_number.setEnabled(false);
        Next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(name.getText().toString().equals("")){
                    name.setError("Please Enter Your Name");
                    return;
                }
                 if(shop_name.getText().toString().equals("")){
                    shop_name.setError("Please Enter Your Shop Name");
                    return;
                }
                 if(!email.getText().toString().isEmpty()){
                    if(!email.getText().toString().trim().matches(emailPattern)){
                        email.setError("Please Enter Correct Format");
                        Toast.makeText(SetupDetail01.this, "Please Enter Correct Format", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }



                    TailorBasicProfile tailorBasicProfile=new TailorBasicProfile();
                    tailorBasicProfile.setPersonName(name.getText().toString());
                    tailorBasicProfile.setShopName(shop_name.getText().toString());
                    tailorBasicProfile.setTailorEmail(email.getText().toString());
                    tailorBasicProfile.setTailorPhoneNo(tailorProfile.getTailorPhoneNo());
                    tailorBasicProfile.setTailorID(tailorProfile.getTailorID());

                    Intent intent=new Intent(SetupDetail01.this,SetupDetail02.class);
                    intent.putExtra("tailorBasicInfo",tailorBasicProfile);
                    startActivity(intent);

            }
        });



    }


}