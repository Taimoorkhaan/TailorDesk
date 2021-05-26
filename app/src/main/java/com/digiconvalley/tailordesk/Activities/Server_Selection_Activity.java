package com.digiconvalley.tailordesk.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;

public class Server_Selection_Activity extends AppCompatActivity {
    private Button liveServer,waleedServer,suhabServer,unknowServer;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server__selection_);

        liveServer=findViewById(R.id.liveServer);
        waleedServer=findViewById(R.id.waleedServer);
        suhabServer=findViewById(R.id.suhabServer);
        unknowServer=findViewById(R.id.UNKNOWNServer);


        liveServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  StaticData.baseUrl="http://3.14.105.67/";
                StaticData.baseUrl="https://tailordesk.azurewebsites.net/";
                StaticData.baseUrlImages="https://tailordesk.azurewebsites.net/Uploads/";
                Intent intent=new Intent(Server_Selection_Activity.this,Splash_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        waleedServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StaticData.baseUrl="https://stiching.conveyor.cloud/";
                Intent intent=new Intent(Server_Selection_Activity.this,Splash_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        suhabServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StaticData.baseUrl="https://stiching-iu2.conveyor.cloud/";
                Intent intent=new Intent(Server_Selection_Activity.this,Splash_Activity.class);
                startActivity(intent);
                finish();

            }
        });

        unknowServer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // StaticData.baseUrlImages="http://3.14.105.67/Uploads/";
                StaticData.baseUrlImages="https://stiching-wo6.conveyor.cloud/Uploads/";
                StaticData.baseUrl="https://stiching-wo6.conveyor.cloud/";
                Intent intent=new Intent(Server_Selection_Activity.this,Splash_Activity.class);
                startActivity(intent);
                finish();

            }
        });

    }
}