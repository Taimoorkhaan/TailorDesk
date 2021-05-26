package com.digiconvalley.tailordesk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.Model.TailorOrders.OrderReceivedAmountLog;
import com.digiconvalley.tailordesk.R;

import java.util.ArrayList;

public class ReceivedAmountAdapter extends RecyclerView.Adapter<ReceivedAmountAdapter.ViewHolder> {
    private Context context;
    private ArrayList<OrderReceivedAmountLog> orderReceivedAmountLogs;

    public ReceivedAmountAdapter(Context context, ArrayList<OrderReceivedAmountLog> orderReceivedAmountLogs) {
        this.context = context;
        this.orderReceivedAmountLogs = orderReceivedAmountLogs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());

        View view=layoutInflater.inflate(R.layout.receive_amount_log_view,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.onBindData(orderReceivedAmountLogs.get(position));
    }

    @Override
    public int getItemCount() {
        return orderReceivedAmountLogs.size();
      //  return 12;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView receiveAmount,receiveDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            receiveAmount=itemView.findViewById(R.id.price);
            receiveDate=itemView.findViewById(R.id.date);
        }

        public void onBindData(OrderReceivedAmountLog orderReceivedAmountLog){
            receiveAmount.setText(orderReceivedAmountLog.getAmount().toString());
            receiveDate.setText(orderReceivedAmountLog.getDate().substring(0,10));
        }
    }

}
