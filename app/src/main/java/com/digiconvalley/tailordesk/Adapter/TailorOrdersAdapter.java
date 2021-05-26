package com.digiconvalley.tailordesk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.ServicePart;
import com.digiconvalley.tailordesk.Model.TailorOrders.TailorOrders;
import com.digiconvalley.tailordesk.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TailorOrdersAdapter extends RecyclerView.Adapter<TailorOrdersAdapter.ViewHolder> implements AdapterView.OnItemSelectedListener {
    private Context context;
    private ArrayList<TailorOrders> tailorOrders;
    private OrderStatus orderStatus;
    private Boolean pastDuaStatus=false;

 /*   public TailorOrdersAdapter(Context context, ArrayList<TailorOrders> tailorOrders,OrderStatus orderStatus) {
        this.context = context;
        this.tailorOrders = tailorOrders;
    }*/

    public TailorOrdersAdapter(Context context, ArrayList<TailorOrders> tailorOrders, OrderStatus orderStatus) {
        this.context = context;
        this.tailorOrders = tailorOrders;
        this.orderStatus = orderStatus;
    }

    public TailorOrdersAdapter(Context context, ArrayList<TailorOrders> tailorOrders, OrderStatus orderStatus, Boolean pastDuaStatus) {
        this.context = context;
        this.tailorOrders = tailorOrders;
        this.orderStatus = orderStatus;
        this.pastDuaStatus = pastDuaStatus;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());

        View view=layoutInflater.inflate(R.layout.tailor_orders_rcv,parent, false);
        return new ViewHolder(view,orderStatus);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
          holder.onBindData(tailorOrders.get(position),position);
    }

    @Override
    public int getItemCount() {

        return tailorOrders.size();
       // return 4;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView orderDueDate,orderDeliveryDate,customerName,orderNumber,orderTotalBill;
        private ImageView CustomerImage,threeDot;
        private LinearLayout mainLayout;

        TailorOrdersAdapter.OrderStatus orderStatus;

        public ViewHolder(@NonNull View itemView,OrderStatus callBack) {
            super(itemView);

            orderDueDate=itemView.findViewById(R.id.text);
            orderDeliveryDate=itemView.findViewById(R.id.orderDueDate);
            customerName=itemView.findViewById(R.id.customerName);
            orderNumber=itemView.findViewById(R.id.customerOrderNo);
            orderTotalBill=itemView.findViewById(R.id.customerOrderPrice);
            CustomerImage=itemView.findViewById(R.id.imageView);
            threeDot=itemView.findViewById(R.id.threedot);
            mainLayout=itemView.findViewById(R.id.mainLayout);

            this.orderStatus=callBack;
            if(pastDuaStatus)
                threeDot.setVisibility(View.GONE);

        }

        public void onBindData(final TailorOrders tailorOrders, final int index){


            orderDueDate.setText(tailorOrders.getOrderDate().substring(0,10));
            orderDeliveryDate.setText("Due on "+tailorOrders.getOrderDeliveryDate().substring(0,10));
           if(tailorOrders.getOrderSuit().size()>0)
             customerName.setText(tailorOrders.getOrderSuit().get(0).getCustomerName());
           else
               customerName.setText("Customer");
            orderNumber.setText("Order #"+tailorOrders.getOrderNo());
            orderTotalBill.setText("Rs "+tailorOrders.getTotalPrice().toString()+" ("+tailorOrders.getOrderSuit().size()+"Items )");

            threeDot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(orderStatus!=null){

                        showPopup(view,orderStatus,tailorOrders,index);
                    }
                }
            });

            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(orderStatus!=null){
                        orderStatus.orderClick(tailorOrders);
                    }
                }
            });
            if(tailorOrders.getOrderSuit().size()>0)
            Picasso.get().load(StaticData.baseUrlImages+ tailorOrders.getOrderSuit().get(0).getCustomerFacePic()).fit().centerCrop()
                    .placeholder(R.drawable.personimage)
                    .error(R.drawable.personimage)
                    .into(CustomerImage);
        }
    }
    public interface OrderStatus{
        public void changeStatus(TailorOrders tailorOrders,int index);
        public void orderClick(TailorOrders tailorOrders);
    }

    public void showPopup(final View v, final OrderStatus orderStatus, final TailorOrders tailorOrders, final int index) {

        PopupMenu popup = new PopupMenu(context, v);
        final MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.active_fragment_menu, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.change_order_status){
                    orderStatus.changeStatus(tailorOrders,index);
                }
                return false;
            }
        });


    }
}
