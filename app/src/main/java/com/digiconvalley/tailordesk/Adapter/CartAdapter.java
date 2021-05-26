package com.digiconvalley.tailordesk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.Model.CreateOrder;
import com.digiconvalley.tailordesk.Model.ServicePart;
import com.digiconvalley.tailordesk.R;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CreateOrder> createOrders;
    private CartEdit cartEdit;

    public CartAdapter(Context context, ArrayList<CreateOrder> createOrders, CartEdit cartEdit) {
        this.context = context;
        this.createOrders = createOrders;
        this.cartEdit = cartEdit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.cart_rcv_desgin,parent, false);
        return new ViewHolder(view,cartEdit);
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
        private CartAdapter.CartEdit cartEdit;
        private TextView orderName,editOrder,orderNo;

        public ViewHolder(@NonNull View itemView,CartEdit callBack) {
            super(itemView);

            orderName=itemView.findViewById(R.id.orderText);
            editOrder=itemView.findViewById(R.id.editOrder);
            orderNo=itemView.findViewById(R.id.orderNo);
            this.cartEdit=callBack;
        }

        public void bindData(final CreateOrder createOrder, final int position){
          /*  for(ServicePart servicePart:createOrder.getNoMeasurement()){
                Toast.makeText(context, "values ="+servicePart.getMeasurementValue(), Toast.LENGTH_SHORT).show();
                orderName.setText(servicePart.getMeasurementValue()+",");
            }*/

            orderName.setText(createOrder.getServiceName());
            orderNo.setText(createOrder.getItemNo());
            editOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(cartEdit!=null){
                        cartEdit.editOrder(createOrder,position);
                    }
                }
            });
        }
    }
    public interface CartEdit{
        public void editOrder(CreateOrder createOrder,int position);
    }
}
