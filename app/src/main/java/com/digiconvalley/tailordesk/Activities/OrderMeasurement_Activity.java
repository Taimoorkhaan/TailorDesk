package com.digiconvalley.tailordesk.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digiconvalley.tailordesk.Adapter.MeasurementAdaptor;
import com.digiconvalley.tailordesk.Model.Service;
import com.digiconvalley.tailordesk.Model.ServicePart;
import com.digiconvalley.tailordesk.R;

import java.util.ArrayList;

public class OrderMeasurement_Activity extends AppCompatActivity {
    private RecyclerView rcv_measurement;
    private MeasurementAdaptor measurementAdaptor;
    private Service service;
    private ArrayList<ServicePart> serviceParts=new ArrayList<>();
    private ImageView backArrow;
    private TextView serviceName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_measurement_);

        rcv_measurement=findViewById(R.id.rcv_Measurement);
        backArrow=findViewById(R.id.back_button);
        serviceName=findViewById(R.id.title_service);

        if(getIntent()!=null){
            service= (Service) getIntent().getSerializableExtra("Services");
            serviceParts.addAll(service.getServicePart());
            serviceName.setText(service.getServiceName());
        }


        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

     /*   rcv_measurement.setLayoutManager(new LinearLayoutManager(this));
        measurementAdaptor=new MeasurementAdaptor(this, serviceParts);
        rcv_measurement.setAdapter(measurementAdaptor);*/
    }
}