package com.digiconvalley.tailordesk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.CreateOrder;
import com.digiconvalley.tailordesk.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CheckOutAdaptor extends RecyclerView.Adapter<CheckOutAdaptor.ViewHolder> {
    private Context context;
    private ArrayList<CreateOrder> createOrders;
    private Checkout checkout;

    public CheckOutAdaptor(Context context, ArrayList<CreateOrder> createOrders, Checkout checkout) {
        this.context = context;
        this.createOrders = createOrders;
        this.checkout = checkout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.list_item,parent, false);
        return new ViewHolder(view,checkout);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(createOrders.get(position),position);
    }

    @Override
    public int getItemCount() {
        return createOrders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private CheckOutAdaptor.Checkout checkout;
        private TextView itemNo,itemDate,itemName,itemPrice,itemEdit;
        private ImageView removeService,serviceImage;

        public ViewHolder(@NonNull View itemView,Checkout callBack) {
            super(itemView);

            this.checkout=callBack;
            itemNo=itemView.findViewById(R.id.itemNo);
            itemDate=itemView.findViewById(R.id.servicedate);
            itemName=itemView.findViewById(R.id.serviceName);
            itemPrice=itemView.findViewById(R.id.servicePrice);
            removeService=itemView.findViewById(R.id.removeService);
            serviceImage=itemView.findViewById(R.id.cardimage);
            itemEdit=itemView.findViewById(R.id.editService);

        }

        public void bindData(final CreateOrder createOrder, final int index){
            itemNo.setText(createOrder.getItemNo());
            if(createOrder.getDevlieryDate()!=null)
            itemDate.setText("Due on "+createOrder.getDevlieryDate());
            itemName.setText(createOrder.getServiceName());
            itemPrice.setText("Rs. "+createOrder.getStitchingPrice());


            Picasso.get().load(StaticData.baseUrlImages+createOrder.getServiceImage()).fit().centerCrop()
                    .error(R.drawable.ic_solid)
                    .into(serviceImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError(Exception e) {

                        }
                    });

            removeService.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkout!=null){
                        checkout.removeService(createOrder);
                    }
                }
            });

            itemEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(checkout!=null){
                        checkout.editService(createOrder,index);
                    }
                }
            });
        }
    }
    public interface Checkout{
        public void removeService(CreateOrder createOrder);
        public void editService(CreateOrder createOrder,int index);
    }
}
