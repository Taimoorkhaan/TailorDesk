package com.digiconvalley.tailordesk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.Model.CatagoryMain;
import com.digiconvalley.tailordesk.Model.TailorCustomers;
import com.digiconvalley.tailordesk.R;

import java.util.ArrayList;

public class AllCustomerAdapter extends RecyclerView.Adapter<AllCustomerAdapter.viewHolder> {
    private Context context;
    private ArrayList<TailorCustomers> tailorCustomers;
    private CustomerList customerList;

    public AllCustomerAdapter(Context context, ArrayList<TailorCustomers> tailorCustomers, CustomerList customerList) {
        this.context = context;
        this.tailorCustomers = tailorCustomers;
        this.customerList = customerList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.customer_rcv_view,parent, false);
        return new viewHolder(view,customerList);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
           holder.bindData(tailorCustomers.get(position));
    }

    @Override
    public int getItemCount() {
        return tailorCustomers.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
          private TextView customerName;
          AllCustomerAdapter.CustomerList customerList;

        public viewHolder(@NonNull View itemView,CustomerList callBack) {
            super(itemView);
          customerName=itemView.findViewById(R.id.customerName);
          customerList=callBack;
        }
        public void bindData(final TailorCustomers tailorCustomers){
               customerName.setText(tailorCustomers.getCustomerName());
              customerName.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      if(customerList!=null){
                          customerList.customerClick(tailorCustomers);
                      }
                  }
              });
        }
    }

    public interface CustomerList{

        public void customerClick(TailorCustomers customers);
    }
}
