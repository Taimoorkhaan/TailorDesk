package com.digiconvalley.tailordesk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.TailorOrders.SubOrderStatus;
import com.digiconvalley.tailordesk.R;

import java.util.ArrayList;

public class SubOrderStatusAdapter extends RecyclerView.Adapter<SubOrderStatusAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> subOrderStatuses;
    private SubOrderClick subOrderClick;

    public SubOrderStatusAdapter(Context context, ArrayList<String> subOrderStatuses, SubOrderClick subOrderClick) {
        this.context = context;
        this.subOrderStatuses = subOrderStatuses;
        this.subOrderClick = subOrderClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());

        View view=layoutInflater.inflate(R.layout.suborderstatus_rcv_view,parent, false);
        return new ViewHolder(view,subOrderClick);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.statusBtn.setText(subOrderStatuses.get(position));
        holder.statusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(holder.subOrderClick!=null){
                    holder.subOrderClick.OnStatusClick(subOrderStatuses.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return subOrderStatuses.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private Button statusBtn;
        private SubOrderClick subOrderClick;



        public ViewHolder(@NonNull View itemView,SubOrderClick callback) {
            super(itemView);

            statusBtn=itemView.findViewById(R.id.btnStatus);
            subOrderClick=callback;


        }
    }
   public interface  SubOrderClick{
        public void OnStatusClick(String subOrderStatus);
    }
}
