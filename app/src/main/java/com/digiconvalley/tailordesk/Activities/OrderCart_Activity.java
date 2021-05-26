package com.digiconvalley.tailordesk.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digiconvalley.tailordesk.Adapter.CartAdapter;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.CreateOrder;
import com.digiconvalley.tailordesk.R;

import java.util.Random;

public class OrderCart_Activity extends AppCompatActivity implements CartAdapter.CartEdit {
    private RecyclerView rcv_Orders;
    private TextView addMoreItems,addMember,toolbarText;
    private CartAdapter cartAdapter;
    private Button completeOrder;
    private int order_no;
    private ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_cart_);

        rcv_Orders=findViewById(R.id.orderRcv);
        addMoreItems=findViewById(R.id.addmore);
        completeOrder=findViewById(R.id.continue_order);
        addMember=findViewById(R.id.addmember);
        back_btn=findViewById(R.id.back_button);
        toolbarText=findViewById(R.id.title);





            toolbarText.setText(StaticData.customerName+"\n Order # "+StaticData.orderNo);
        rcv_Orders.setLayoutManager(new LinearLayoutManager(this));
        cartAdapter=new CartAdapter(this, StaticData.createOrders,this);
        rcv_Orders.setAdapter(cartAdapter);


        addMoreItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OrderCart_Activity.this,SelectCategoryActivity.class);
                startActivity(intent);

            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        addMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Intent intent=new Intent(OrderCart_Activity.this,AddCustomerMember.class);
                startActivity(intent);
                finish();*/
            }
        });

        completeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(OrderCart_Activity.this,CheckOutActivity.class);
                startActivity(intent);

            }
        });
    }

    @Override
    public void editOrder(CreateOrder createOrder, int position) {

        Intent intent=new Intent(OrderCart_Activity.this,CreateOrder_Activity.class);
        intent.putExtra("EditOrder",createOrder);
        intent.putExtra("Position",position);
        startActivity(intent);

    }

}