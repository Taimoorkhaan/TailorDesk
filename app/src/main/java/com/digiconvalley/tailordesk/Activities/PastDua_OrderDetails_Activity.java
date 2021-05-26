package com.digiconvalley.tailordesk.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.digiconvalley.tailordesk.Adapter.OrderDetail_Adapter;
import com.digiconvalley.tailordesk.Model.TailorOrders.OrderSuit;
import com.digiconvalley.tailordesk.Model.TailorOrders.TailorOrders;
import com.digiconvalley.tailordesk.R;

import java.util.ArrayList;

public class PastDua_OrderDetails_Activity extends AppCompatActivity {
    private RecyclerView rcvOrderDetails;
    private TextView orderDeliveryDate,orderStatus,orderbill,orderReceivedAmount,
            orderRemainingAmount,customerName,remainingDateText;
    private OrderDetail_Adapter orderDetail_adapter;
    private TailorOrders tailorOrders;
    private Button receiveMoreBtn,confrmBtn;
    private int receivedAmount=-1;
    private ImageView backImage,phoneBtn;
    private String remainingBill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_past_dua__order_details_);

        rcvOrderDetails=findViewById(R.id.rcvCustomerOrderDetails);
        orderDeliveryDate=findViewById(R.id.dueDateCustomerOrderInfo);
        orderStatus=findViewById(R.id.orderStatusCustomer);
        orderbill=findViewById(R.id.totalBillAmount_edt);
        orderReceivedAmount=findViewById(R.id.amount_customerOrderInfo);
        orderRemainingAmount=findViewById(R.id.update_amount_customerOrderInfo);
        customerName=findViewById(R.id.cutomerNameOrderInfo);
        receiveMoreBtn=findViewById(R.id.recevie_more_payment);
        confrmBtn=findViewById(R.id.Confirm_Order_CustomerOrderInfo);
        backImage=findViewById(R.id.back_btn_customerOrderDetail);
        remainingDateText=findViewById(R.id.balance);
        phoneBtn=findViewById(R.id.phone);

        if(getIntent()!=null && getIntent().hasExtra("OrderDetails")){

            tailorOrders= (TailorOrders) getIntent().getSerializableExtra("OrderDetails");

            rcvOrderDetails.setLayoutManager(new LinearLayoutManager(this));
            orderDetail_adapter=new OrderDetail_Adapter(this, (ArrayList<OrderSuit>) tailorOrders.getOrderSuit(),
                    null);
            rcvOrderDetails.setAdapter(orderDetail_adapter);

            // customerName.setText(tailorOrders.getOrderSuit().get(0).getCustomerName());
            orderDeliveryDate.setText(tailorOrders.getOrderDeliveryDate().substring(0,10));
            orderStatus.setText(tailorOrders.getOrderStatus());

            orderbill.setText(tailorOrders.getTotalPrice().toString());
            orderReceivedAmount.setText(tailorOrders.getRecievedAmount().toString());
            orderRemainingAmount.setText(tailorOrders.getRamainingAmount().toString());
            if(tailorOrders.getOrderSuit().size()>0)
                customerName.setText(tailorOrders.getOrderSuit().get(0).getCustomerName());


            remainingBill = String.valueOf(tailorOrders.getTotalPrice()-(tailorOrders.getRecievedAmount()+tailorOrders.getDiscountAmount()));

            if(remainingBill.substring(0,1).equalsIgnoreCase("-")) {
                orderRemainingAmount.setText("" +remainingBill.substring(1,remainingBill.length()));
                orderRemainingAmount.setTextColor(getResources().getColor(R.color.green));
                remainingDateText.setText("Balance");
                remainingDateText.setTextColor(getResources().getColor(R.color.green));
            }
            else {
                orderRemainingAmount.setText(remainingBill);
                orderRemainingAmount.setTextColor(getResources().getColor(R.color.red));
                remainingDateText.setText("Balance Due");
                remainingDateText.setTextColor(getResources().getColor(R.color.red));
            }
        }

        if(getIntent()!=null && getIntent().hasExtra("ReceivedAmount")){

            tailorOrders= (TailorOrders) getIntent().getSerializableExtra("OrderDetails");

            receivedAmount=getIntent().getIntExtra("ReceivedAmount",0);
            rcvOrderDetails.setLayoutManager(new LinearLayoutManager(this));
            orderDetail_adapter=new OrderDetail_Adapter(this, (ArrayList<OrderSuit>) tailorOrders.getOrderSuit(),
                    null);
            rcvOrderDetails.setAdapter(orderDetail_adapter);

            // customerName.setText(tailorOrders.getOrderSuit().get(0).getCustomerName());
            orderDeliveryDate.setText(tailorOrders.getOrderDeliveryDate().substring(0,10));
            orderStatus.setText(tailorOrders.getOrderStatus());

            orderbill.setText(tailorOrders.getTotalPrice().toString());
            orderReceivedAmount.setText(String.valueOf(tailorOrders.getRecievedAmount()+receivedAmount));

            orderRemainingAmount.setText(String.valueOf(tailorOrders.getRamainingAmount()-receivedAmount));

        }

        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tailorOrders!=null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:" + tailorOrders.getOrderSuit().get(0).getCustomerPhoneNo()));
                    startActivity(intent);
                }
            }
        });

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(PastDua_OrderDetails_Activity.this,MainHome_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}