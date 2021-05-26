package com.digiconvalley.tailordesk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.TailorOrders.OrderSuit;
import com.digiconvalley.tailordesk.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderDetail_Adapter extends RecyclerView.Adapter<OrderDetail_Adapter.ViewHolder> {

    private Context context;
    private ArrayList<OrderSuit> orderSuits;
    private OrderEditStatus orderEditStatus;


    public OrderDetail_Adapter(Context context, ArrayList<OrderSuit> orderSuits, OrderEditStatus orderEditStatus) {
        this.context = context;
        this.orderSuits = orderSuits;
        this.orderEditStatus = orderEditStatus;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());

        View view=layoutInflater.inflate(R.layout.order_details_rcv_view,parent, false);
        return new OrderDetail_Adapter.ViewHolder(view,orderEditStatus);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBindData(orderSuits.get(position),position);
    }

    @Override
    public int getItemCount() {
        return orderSuits.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView service_name,orderNo,orderPrice,orderDuaDate,orderStatus;
        private OrderEditStatus orderEditStatus;
        private ImageButton threeDotBtn;
        private ImageView serviceImage;

        public ViewHolder(@NonNull View itemView,OrderEditStatus callback) {
            super(itemView);

            orderEditStatus=callback;

            service_name=itemView.findViewById(R.id.pant01_customerOrderInfo);
            orderNo=itemView.findViewById(R.id.item1232_customerOrderInfo);
            orderPrice=itemView.findViewById(R.id.rs300_customer_orderInfo);
            orderDuaDate=itemView.findViewById(R.id.date_customerOrderInfo);
            orderStatus=itemView.findViewById(R.id.set_message_customerOrderInfo);
            threeDotBtn=itemView.findViewById(R.id.dialog_menu_customerOrderInfo);
            serviceImage=itemView.findViewById(R.id.cardimageCustomerOrder);


        }

        public void onBindData(final OrderSuit orderSuit, final Integer index){

            service_name.setText(orderSuit.getOrderSuitName());
            orderNo.setText(orderSuit.getItemNumber());
            orderPrice.setText("Rs . "+orderSuit.getOrderSuitPrice());
            if(!orderSuit.getDeliveryDate().equalsIgnoreCase("0001-01-01T00:00:00"))
            orderDuaDate.setText(orderSuit.getDeliveryDate().substring(0,10));
            orderStatus.setText(orderSuit.getOrderSuitStatus());

            if(orderEditStatus==null){
                threeDotBtn.setVisibility(View.GONE);
            }

            Picasso.get().load(StaticData.baseUrlImages+ orderSuit.getCustomerFacePic()).fit().centerCrop()
                    .placeholder(R.drawable.personimage)
                    .error(R.drawable.personimage)
                    .into(serviceImage);


            threeDotBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                if(orderEditStatus!=null){
                  //  orderEditStatus.changeStatus(orderSuit);
                    showPopup(view,orderEditStatus,orderSuit,index);
                }
                }
            });

        }

    }
    public interface OrderEditStatus{

        public void changeStatus(OrderSuit orderSuit,Integer index);
        public void editOrder(OrderSuit orderSuit);
        public void taskStatus(OrderSuit orderSuit);
        public void deleteStatus(OrderSuit orderSuit, Integer index);
        //public void orderClick(TailorOrders tailorOrders);
    }
    public void showPopup(final View v, final OrderDetail_Adapter.OrderEditStatus orderEditStatus, final OrderSuit orderSuit, final Integer index) {

        PopupMenu popup = new PopupMenu(context, v);
        final MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.suit_order_menu, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.edit_item){
                    if(orderEditStatus!=null)
                        orderEditStatus.editOrder(orderSuit);
                }

                if(menuItem.getItemId()==R.id.change_item){
                    if(orderEditStatus!=null)
                        orderEditStatus.changeStatus(orderSuit,index);
                }

                if(menuItem.getItemId()==R.id.task_status) {
                }

                if(menuItem.getItemId()==R.id.delete_item){
                    if(orderEditStatus!=null)
                    orderEditStatus.deleteStatus(orderSuit,index);
                }

                return false;
            }
        });


    }
}
