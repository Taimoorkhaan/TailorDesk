package com.digiconvalley.tailordesk.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.digiconvalley.tailordesk.Adapter.OrderListAdaptor;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.CreateOrder;
import com.digiconvalley.tailordesk.R;

public class OrderThankyouActivity extends AppCompatActivity {
    private RecyclerView rcv_OrderList;
    private OrderListAdaptor orderListAdaptor;
    private Button btnGotoHome;
    private TextView totalBill,paymentReceived,remainBalance,discountAmount,orderNo,balance;
    private String remainingPayment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_thankyou);

        btnGotoHome=findViewById(R.id.goToHome);
        rcv_OrderList=findViewById(R.id.orderList);
        totalBill=findViewById(R.id.totalBillAmount_edt);
        paymentReceived=findViewById(R.id.amount_customerOrderInfo);
        remainBalance=findViewById(R.id.update_amount_customerOrderInfo);
        discountAmount=findViewById(R.id.discount_Amount);
        orderNo=findViewById(R.id.orderNo);
        balance=findViewById(R.id.balance);



        if(getIntent()!=null && getIntent().hasExtra("TotalBill")){

            totalBill.setText(getIntent().getStringExtra("TotalBill"));
            paymentReceived.setText(getIntent().getStringExtra("ReceivedAmount"));
            remainingPayment=getIntent().getStringExtra("RemainingAmount");
            discountAmount.setText(getIntent().getStringExtra("Discount"));

            orderNo.setText("Order # "+getIntent().getIntExtra("Order_no",0));


            if(remainingPayment.substring(0,1).equalsIgnoreCase("-")) {
                remainBalance.setText("" + String.valueOf(remainingPayment).substring(1,remainingPayment.length()));
                remainBalance.setTextColor(getResources().getColor(R.color.green));
                balance.setText("Balance");
                balance.setTextColor(getResources().getColor(R.color.green));
            }

            else {
                remainBalance.setText("" + remainingPayment);
                remainBalance.setTextColor(getResources().getColor(R.color.red));
                balance.setText("Balance Due");
                balance.setTextColor(getResources().getColor(R.color.red));
            }

            StaticData.orderNo=0;
            StaticData.n=0;

        }

        rcv_OrderList.setLayoutManager(new LinearLayoutManager(this));


        orderListAdaptor=new OrderListAdaptor(this,StaticData.createOrders);
        rcv_OrderList.setAdapter(orderListAdaptor);


        btnGotoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StaticData.createOrders.clear();
                StaticData.customerid=null;
                StaticData.tailorShopId=null;
                StaticData.n=0;

                Intent intent=new Intent(OrderThankyouActivity.this,MainHome_Activity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
    //    super.onBackPressed();
        StaticData.createOrders.clear();
        StaticData.customerid=null;
        StaticData.tailorShopId=null;

        Intent intent=new Intent(OrderThankyouActivity.this,MainHome_Activity.class);
        startActivity(intent);
        finish();
    }
}