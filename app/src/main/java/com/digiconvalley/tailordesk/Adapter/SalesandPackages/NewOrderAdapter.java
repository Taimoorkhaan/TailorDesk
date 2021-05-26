package com.digiconvalley.tailordesk.Adapter.SalesandPackages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.SalesandPackages.MainSalesOrder;
import com.digiconvalley.tailordesk.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewOrderAdapter extends RecyclerView.Adapter<NewOrderAdapter.viewHolder> {

    Context context;
    List<MainSalesOrder> arrayList;

    public NewOrderAdapter(Context context, List<MainSalesOrder> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.new_order, parent, false);

        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {

        Picasso.get().load(StaticData.baseUrlImages + arrayList.get(position).getTailorProfilePic()).into(holder.order_image);
        // holder.order_image.setImageResource(arrayList.get(position).getOrder_image());
        holder.orderUserName.setText(arrayList.get(position).getTailorName());
        holder.order_no.setText(arrayList.get(position).getOrderNo());
        if (arrayList.get(position).getTotalSuits()<10)
            holder.order_price.setText("Rs." + arrayList.get(position).getTotalPrice() + "( 0" + arrayList.get(position).getTotalSuits() + " items)");
        else
            holder.order_price.setText("Rs." + arrayList.get(position).getTotalPrice() + "( " + arrayList.get(position).getTotalSuits() + " items)");
        holder.order_date.setText(arrayList.get(position).getOrderDeliveryDate());
        // holder.order_adanceP.setText(arrayList.get(position).getOrder_advanceP());
        holder.order_advaceRs.setText("Rs. " + arrayList.get(position).getRemainingAmount());


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {
        ImageView order_image;
        TextView orderUserName, order_no, order_price, order_date, order_adanceP, order_advaceRs;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            order_image = itemView.findViewById(R.id.order_image);
            orderUserName = itemView.findViewById(R.id.order_userName);
            order_no = itemView.findViewById(R.id.order_no);
            order_price = itemView.findViewById(R.id.order_price);
            order_date = itemView.findViewById(R.id.order_date);
            order_adanceP = itemView.findViewById(R.id.order_advancePayment);
            order_advaceRs = itemView.findViewById(R.id.order_advanceRs);
        }
    }
}
