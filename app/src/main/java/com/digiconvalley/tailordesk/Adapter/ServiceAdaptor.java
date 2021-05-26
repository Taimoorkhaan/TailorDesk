package com.digiconvalley.tailordesk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.Model.Service;
import com.digiconvalley.tailordesk.Model.TailorCustomers;
import com.digiconvalley.tailordesk.R;

import java.util.ArrayList;

public class ServiceAdaptor extends RecyclerView.Adapter<ServiceAdaptor.ViewHolder> {
    private Context context;
    private ArrayList<Service> serviceArrayList;
    private ServicesList servicesList;

    public ServiceAdaptor(Context context, ArrayList<Service> serviceArrayList, ServicesList servicesList) {
        this.context = context;
        this.serviceArrayList = serviceArrayList;
        this.servicesList = servicesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.rcv_services_design,parent, false);
        return new ViewHolder(view,servicesList);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.bindData(serviceArrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return serviceArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView serviceName;
        private ServiceAdaptor.ServicesList servicesList;
        public ViewHolder(@NonNull View itemView,ServicesList callBack) {
            super(itemView);

            serviceName=itemView.findViewById(R.id.serviceText);
            this.servicesList=callBack;
        }
        private void bindData(final Service service){
            if(service!=null)
            serviceName.setText(service.getServiceName());

            serviceName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(servicesList!=null){
                        servicesList.ServiceOnClick(service);
                    }
                }
            });
        }
    }
    public interface ServicesList{
        public void ServiceOnClick(Service service);
    }
}
