package com.digiconvalley.tailordesk.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digiconvalley.tailordesk.Adapter.OrderDetail_Adapter;
import com.digiconvalley.tailordesk.Adapter.ReceivedAmountAdapter;
import com.digiconvalley.tailordesk.Adapter.SubOrderStatusAdapter;
import com.digiconvalley.tailordesk.Adapter.TailorOrdersAdapter;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.CatagoryMain;
import com.digiconvalley.tailordesk.Model.TailorOrders.OrderSuit;
import com.digiconvalley.tailordesk.Model.TailorOrders.SubOrderStatus;
import com.digiconvalley.tailordesk.Model.TailorOrders.TailorOrders;
import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.Singleton.SingletonPattren;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TailorOrder_Detail_Activity extends AppCompatActivity implements OrderDetail_Adapter.OrderEditStatus,
SubOrderStatusAdapter.SubOrderClick{

    private RecyclerView rcvOrderDetails,rcvReceviedAmount;
    private TextView orderDeliveryDate,orderStatus,orderbill,orderReceivedAmount,orderRemainingAmount,
            customerName,orderText,remainingDateText,discountAmount;
    private OrderDetail_Adapter orderDetail_adapter;
    private ReceivedAmountAdapter receivedAmountAdapter;
    private TailorOrders tailorOrders;
    private Button receiveMoreBtn,confrmBtn;
    private int receivedAmount=-1,outerOrderSuitIndex=0;
    private ImageView backImage,phoneBtn;
    private JSONObject orderData,OrderReceivedAmountLog;
    private SubOrderStatus subOrderStatuses;
    private Dialog subOrderStatusDialog;
    private SubOrderStatusAdapter subOrderStatusAdapter;
    private OrderSuit orderSuitOuter;
    private RecyclerView subOrderRcv;
    private String remainingBill;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_order__detail_);

        rcvOrderDetails=findViewById(R.id.rcvCustomerOrderDetails);
        rcvReceviedAmount=findViewById(R.id.rcv_ReceivedAmount);
        orderDeliveryDate=findViewById(R.id.dueDateCustomerOrderInfo);
        orderStatus=findViewById(R.id.orderStatusCustomer);
        orderbill=findViewById(R.id.totalBillAmount_edt);
        orderReceivedAmount=findViewById(R.id.amount_customerOrderInfo);
        orderRemainingAmount=findViewById(R.id.update_amount_customerOrderInfo);
        customerName=findViewById(R.id.cutomerNameOrderInfo);
        receiveMoreBtn=findViewById(R.id.recevie_more_payment);
        confrmBtn=findViewById(R.id.Confirm_Order_CustomerOrderInfo);
        backImage=findViewById(R.id.back_btn_customerOrderDetail);
        orderText=findViewById(R.id.orderIdTitle);
        phoneBtn=findViewById(R.id.phone);
        remainingDateText=findViewById(R.id.balancetext);

        discountAmount=findViewById(R.id.discountAmount);


        if(getIntent()!=null && getIntent().hasExtra("OrderDetails")){

            tailorOrders= (TailorOrders) getIntent().getSerializableExtra("OrderDetails");

            rcvOrderDetails.setLayoutManager(new LinearLayoutManager(this));
            orderDetail_adapter=new OrderDetail_Adapter(this, (ArrayList<OrderSuit>) tailorOrders.getOrderSuit(),
                    TailorOrder_Detail_Activity.this);
            rcvOrderDetails.setAdapter(orderDetail_adapter);

            rcvReceviedAmount.setLayoutManager(new LinearLayoutManager(this));
            receivedAmountAdapter=new ReceivedAmountAdapter(this, (ArrayList<com.digiconvalley.tailordesk.Model.TailorOrders.OrderReceivedAmountLog>) tailorOrders.getOrderReceivedAmounts());
            rcvReceviedAmount.setAdapter(receivedAmountAdapter);

           // customerName.setText(tailorOrders.getOrderSuit().get(0).getCustomerName());
            orderDeliveryDate.setText(tailorOrders.getOrderDeliveryDate().substring(0,10));
            orderStatus.setText(tailorOrders.getOrderStatus());

            orderbill.setText(tailorOrders.getTotalPrice().toString());
            orderReceivedAmount.setText(tailorOrders.getRecievedAmount().toString());
            orderRemainingAmount.setText(tailorOrders.getRamainingAmount().toString());
            discountAmount.setText(tailorOrders.getDiscountAmount().toString());

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



            if(tailorOrders.getOrderSuit().size()>0)
            customerName.setText(tailorOrders.getOrderSuit().get(0).getCustomerName());
            orderText.setText("Order # "+tailorOrders.getOrderNo());
            getStatusData();
        }

        if(getIntent()!=null && getIntent().hasExtra("ReceivedAmount")){

            tailorOrders= (TailorOrders) getIntent().getSerializableExtra("OrderDetails");

            receivedAmount=getIntent().getIntExtra("ReceivedAmount",0);
            rcvOrderDetails.setLayoutManager(new LinearLayoutManager(this));
            orderDetail_adapter=new OrderDetail_Adapter(this, (ArrayList<OrderSuit>) tailorOrders.getOrderSuit(),
                    TailorOrder_Detail_Activity.this);
            rcvOrderDetails.setAdapter(orderDetail_adapter);

           // customerName.setText(tailorOrders.getOrderSuit().get(0).getCustomerName());
            orderDeliveryDate.setText(tailorOrders.getOrderDeliveryDate().substring(0,10));
            orderStatus.setText(tailorOrders.getOrderStatus());

            orderbill.setText(tailorOrders.getTotalPrice().toString());
            tailorOrders.setRecievedAmount(tailorOrders.getRecievedAmount()+receivedAmount);
            orderReceivedAmount.setText(String.valueOf(tailorOrders.getRecievedAmount()));

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

//            orderRemainingAmount.setText(String.valueOf(tailorOrders.getRamainingAmount()-receivedAmount));
            getStatusData();

        }

        phoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
               intent.setData(Uri.parse("tel:"+tailorOrders.getOrderSuit().get(0).getCustomerPhoneNo()));
                startActivity(intent);
            }
        });

        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TailorOrder_Detail_Activity.this,MainHome_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        receiveMoreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TailorOrder_Detail_Activity.this, ReceiveMorePayment_Activity.class);
                intent.putExtra("OrderDetails", tailorOrders);
                startActivity(intent);
            }
        });

        confrmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(receivedAmount!=-1)
                updateOrderBill();
                else
                    Toast.makeText(TailorOrder_Detail_Activity.this, "Kindly Enter Amount ...", Toast.LENGTH_SHORT).show();
            }
        });


    }

    public void updateOrderBill(){

        orderData=new JSONObject();
        OrderReceivedAmountLog=new JSONObject();
     //   Toast.makeText(this, ""+tailorOrders.getOrderId(), Toast.LENGTH_SHORT).show();


        try {
            orderData.put("OrderId",tailorOrders.getOrderId());
            orderData.put("RamainingAmount",String.valueOf(tailorOrders.getRamainingAmount()-receivedAmount));
            orderData.put("RecievedAmount",String.valueOf(receivedAmount+tailorOrders.getRecievedAmount()));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            OrderReceivedAmountLog.put("Amount",String.valueOf(receivedAmount+tailorOrders.getRecievedAmount()));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String url;

        url=StaticData.baseUrl+"TailorPostApi/UpdatePrice";
     /*   url = StaticData.baseUrl +"TailorPostApi/UpdatePrice?OrderId="+tailorOrders.getOrderId()+"&RamainingAmount="+
                String.valueOf(tailorOrders.getRamainingAmount()-receivedAmount)+"&RecievedAmount="+String.valueOf(receivedAmount+tailorOrders.getRecievedAmount());
*/
        StringRequest req = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                  //  Toast.makeText(TailorOrder_Detail_Activity.this, "Response = "+response, Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(TailorOrder_Detail_Activity.this,MainHome_Activity.class);
                    startActivity(intent);
                    finish();
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(TailorOrder_Detail_Activity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                progressDialog.setTitle(error.toString());
                String message = null;

            }
        })
        {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("order", orderData.toString());
                params.put("OrderReceivedAmountLog",OrderReceivedAmountLog.toString());



                return params;
            }
        };

        req.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(this).addToRequestque(req);

    }

    @Override
    public void changeStatus(OrderSuit orderSuit,Integer index) {
        orderSuitOuter=orderSuit;
        outerOrderSuitIndex=index;



        subOrderStatusDialog=new Dialog(this);
        subOrderStatusDialog.setContentView(R.layout.suborderstatus_dialog_view);

        subOrderRcv=subOrderStatusDialog.findViewById(R.id.subOrderRcv);
        subOrderRcv.setLayoutManager(new LinearLayoutManager(subOrderStatusDialog.getContext()));


        subOrderStatusAdapter=new SubOrderStatusAdapter(TailorOrder_Detail_Activity.this, (ArrayList<String>) subOrderStatuses.getStatus(),TailorOrder_Detail_Activity.this);
        subOrderRcv.setAdapter(subOrderStatusAdapter);

        subOrderStatusDialog.show();


    }

    @Override
    public void editOrder(OrderSuit orderSuit) {
        Intent intent=new Intent(TailorOrder_Detail_Activity.this,EditOrderItem_Activity.class);
        intent.putExtra("OrderItem",orderSuit);
        intent.putExtra("TailorOrder",tailorOrders);
        startActivity(intent);
    }

    @Override
    public void taskStatus(OrderSuit orderSuit) {

    }

    @Override
    public void deleteStatus(OrderSuit orderSuit, Integer index) {
        delSubOrder(orderSuit.getOrderSuitId(),orderSuit,index);
    }

    public void delSubOrder(Integer subOrderSuitId, final OrderSuit orderSuit, final int index) {

        final ProgressDialog progressDialog = new ProgressDialog(TailorOrder_Detail_Activity.this);

        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String url;


        url = StaticData.baseUrl +"TailorGetApi/DeleteSuborder?OrderSuitId="+subOrderSuitId;

        StringRequest req = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if(response.equalsIgnoreCase("\"No Such Order Exist\"")){
                  //      Toast.makeText(TailorOrder_Detail_Activity.this, ""+response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        return;
                    }

                    tailorOrders.getOrderSuit().remove(orderSuit);

                    try {
                        JSONObject orderPrices=new JSONObject(response);
                        orderbill.setText(orderPrices.get("totalPrice").toString());
                        orderReceivedAmount.setText(orderPrices.get("recievedAmount").toString());
                        orderRemainingAmount.setText(orderPrices.get("ramainingAmount").toString());

                        tailorOrders.setTotalPrice(Integer.parseInt(orderPrices.get("totalPrice").toString()));
                        tailorOrders.setRecievedAmount(Integer.parseInt(orderPrices.get("recievedAmount").toString()));
                        tailorOrders.setRamainingAmount(Integer.parseInt(orderPrices.get("ramainingAmount").toString()));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if(tailorOrders.getOrderSuit().size()<1){
                        Intent intent=new Intent(TailorOrder_Detail_Activity.this,MainHome_Activity.class);
                        startActivity(intent);
                        finish();
                    }
                    orderDetail_adapter.notifyDataSetChanged();


                    progressDialog.dismiss();
                } else {
                    Toast.makeText(TailorOrder_Detail_Activity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                progressDialog.setTitle(error.toString());
                String message = null;

            }
        });


        req.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(TailorOrder_Detail_Activity.this).addToRequestque(req);

    }
    public void statusChangeSubOrder(Integer subOrderSuitNo, final String status, OrderSuit orderSuit, final Integer index) {

        final ProgressDialog progressDialog = new ProgressDialog(TailorOrder_Detail_Activity.this);

        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String url;

        url = StaticData.baseUrl +"TailorGetApi/ChangeOrderSuitStatus?OrderSuitId="+subOrderSuitNo+"&OrderSuitStatus="+status;

        StringRequest req = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if(response.equalsIgnoreCase("\"No Such Order Exist\"")){
                    //    Toast.makeText(TailorOrder_Detail_Activity.this, ""+response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        return;
                    }

                    tailorOrders.getOrderSuit().get(index).setOrderSuitStatus(status);
                    orderDetail_adapter.notifyDataSetChanged();
                    subOrderStatusDialog.dismiss();

                    /////////////////////////////////////////
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(TailorOrder_Detail_Activity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                progressDialog.setTitle(error.toString());
                String message = null;

            }
        });

        req.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(TailorOrder_Detail_Activity.this).addToRequestque(req);

    }

    public void getStatusData(){

        final ProgressDialog progressDialog = new ProgressDialog(TailorOrder_Detail_Activity.this);

        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String url;

        url = StaticData.baseUrl +"TailorGetApi/ChangeSubOrderStatus";

        StringRequest req = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    if(response.equalsIgnoreCase("\"No Such Order Exist\"")){
                     //   Toast.makeText(TailorOrder_Detail_Activity.this, ""+response, Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        return;
                    }
                    Gson gson=new Gson();

                    subOrderStatuses=gson.fromJson(response,SubOrderStatus.class);


                    /////////////////////////////////////////
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(TailorOrder_Detail_Activity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                progressDialog.setTitle(error.toString());
                String message = null;

            }
        });

        req.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(TailorOrder_Detail_Activity.this).addToRequestque(req);

}

    @Override
    public void OnStatusClick(String subOrderStatus) {

        statusChangeSubOrder(orderSuitOuter.getOrderSuitId(),subOrderStatus,orderSuitOuter,outerOrderSuitIndex);
    }

    @Override
    public void onBackPressed() {
        Intent intent=new Intent(TailorOrder_Detail_Activity.this,MainHome_Activity.class);
        startActivity(intent);
        finish();
    }
}