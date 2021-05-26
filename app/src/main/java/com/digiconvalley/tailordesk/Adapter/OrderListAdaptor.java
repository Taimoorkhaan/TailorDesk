package com.digiconvalley.tailordesk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.Model.CreateOrder;
import com.digiconvalley.tailordesk.R;

import java.util.ArrayList;

public class OrderListAdaptor extends RecyclerView.Adapter<OrderListAdaptor.ViewHolder> {
    private Context context;
    private ArrayList<CreateOrder> createOrders;

    public OrderListAdaptor(Context context, ArrayList<CreateOrder> createOrders) {
        this.context = context;
        this.createOrders = createOrders;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());

        View view=layoutInflater.inflate(R.layout.order_list_rcv_thankyou,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBindData(createOrders.get(position));
    }

    @Override
    public int getItemCount() {
       return createOrders.size();

    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView orderPrice,orderItemName,orderItemNumber,orderDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            orderPrice=itemView.findViewById(R.id.orderPrice);
            orderItemName=itemView.findViewById(R.id.pant01_customerOrderInfo);
            orderItemNumber=itemView.findViewById(R.id.item1232_customerOrderInfo);
            orderDate=itemView.findViewById(R.id.date_customerOrderInfo);
        }

        public void onBindData(CreateOrder createOrder){

            orderPrice.setText("Rs. "+createOrder.getStitchingPrice());
            orderItemName.setText(createOrder.getServiceName());
            orderItemNumber.setText(createOrder.getItemNo());
            if(createOrder.getDevlieryDate()!=null)
            orderDate.setText(createOrder.getDevlieryDate());
        }
    }
}
