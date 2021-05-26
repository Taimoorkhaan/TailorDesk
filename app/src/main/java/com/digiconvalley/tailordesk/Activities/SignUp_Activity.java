package com.digiconvalley.tailordesk.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.digiconvalley.tailordesk.R;

import java.util.regex.Pattern;


public class SignUp_Activity extends AppCompatActivity {
   private Button submit_button;
   private EditText Phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        submit_button=findViewById(R.id.submit_button);
        Phone_number=findViewById(R.id.phone);



        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // (332|333|010|015)[0-9]{7}
                if(Phone_number.getText().toString().trim().length()>9) {
                    if(Pattern.matches("(3)+[0-9]{2}[0-9]{7}", Phone_number.getText().toString())) {
                        Intent intent = new Intent(SignUp_Activity.this, NumberVerfication_Activity.class);
                        intent.putExtra("number", "+92" + Phone_number.getText().toString().trim().replace(" ", ""));
                        startActivity(intent);
                    }
                    else{
                        Phone_number.setError("Please Enter Right Format");
                        Toast.makeText(SignUp_Activity.this, "Please Enter Right Format", Toast.LENGTH_SHORT).show();
                    }

                }
                else{
                    Toast.makeText(SignUp_Activity.this, "Kindly Enter your Number", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
 /*   public void enableSubmitIfReady() {
        boolean isReady = Phone_number.getText().toString().length() > 9;
        submit_button.setEnabled(isReady);
    }*/

    @Override
    public void onBackPressed() {

        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory(Intent.CATEGORY_HOME);
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }
}