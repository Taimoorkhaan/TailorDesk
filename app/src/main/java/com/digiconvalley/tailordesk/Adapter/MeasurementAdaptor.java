package com.digiconvalley.tailordesk.Adapter;


import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.CreateOrder;
import com.digiconvalley.tailordesk.Model.ServicePart;
import com.digiconvalley.tailordesk.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MeasurementAdaptor extends RecyclerView.Adapter<MeasurementAdaptor.ViewHolder> {
    private Context context;
    private ArrayList<ServicePart> serviceParts;
    private MeasurementFun measurementFun;

    public MeasurementAdaptor(Context context, ArrayList<ServicePart> serviceParts, MeasurementFun measurementFun) {
        this.context = context;
        this.serviceParts = serviceParts;
        this.measurementFun = measurementFun;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.measurment_rcv_desgin,parent, false);
        return new ViewHolder(view,measurementFun);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(serviceParts.get(position)!=null)
        holder.onBindData(serviceParts.get(position),position);
    }

    @Override
    public int getItemCount() {
        return serviceParts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView serviceName;
        private EditText measurementNo;
        private ImageView serviceImage;
        private MeasurementAdaptor.MeasurementFun measurementFun;

        public ViewHolder(@NonNull View itemView,MeasurementFun callback) {
            super(itemView);
            serviceName=itemView.findViewById(R.id.serviceName);
            measurementNo=itemView.findViewById(R.id.pant_measurementNo);
            serviceImage=itemView.findViewById(R.id.serviceImage);
            this.measurementFun=callback;
        }
        public void onBindData(final ServicePart servicePart, final int position){
            serviceName.setText(servicePart.getServicePartName());

            Picasso.get().load(StaticData.baseUrlImages+servicePart.getServicePartIcon()).fit().centerCrop()
                    .error(R.drawable.ic_solid)
                    .into(serviceImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });

           // if(!servicePart.getMeasurementValue().equalsIgnoreCase(""))
            if(servicePart.getMeasurementValue()!=null)
               measurementNo.setText(servicePart.getMeasurementValue());


            measurementNo.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                   // Toast.makeText(context, "Value is "+charSequence, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void afterTextChanged(Editable editable) {

                    if(measurementFun!=null){
                        servicePart.setMeasurementValue(editable.toString());
                       measurementFun.measurementValues(servicePart,position);
                    }
                }
            });
        }
    }
    public interface MeasurementFun{
        public void measurementValues(ServicePart servicePart,int Position);
    }

   }
