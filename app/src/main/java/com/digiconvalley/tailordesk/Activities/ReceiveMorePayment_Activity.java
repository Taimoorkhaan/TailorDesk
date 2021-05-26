package com.digiconvalley.tailordesk.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.TailorOrders.OrderReceivedAmountLog;
import com.digiconvalley.tailordesk.Model.TailorOrders.TailorOrders;
import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.Singleton.SingletonPattren;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ReceiveMorePayment_Activity extends AppCompatActivity {
    private Button receiveAmountBtn;
    private EditText receiveAmountEdt;
    private ImageView backBtn;
    private TailorOrders tailorOrders;
    private JSONObject orderData,OrderReceivedAmountLog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_more_payment_);

        receiveAmountBtn=findViewById(R.id.receiveAmount);
        receiveAmountEdt=findViewById(R.id.add_amount);
        backBtn=findViewById(R.id.addAmountBack);

        if(getIntent()!=null && getIntent().hasExtra("OrderDetails")) {
            tailorOrders = (TailorOrders) getIntent().getSerializableExtra("OrderDetails");
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });

        receiveAmountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(receiveAmountEdt.getText().toString().isEmpty()){
                    Toast.makeText(ReceiveMorePayment_Activity.this, "Kindly Enter Amount ....", Toast.LENGTH_SHORT).show();
                 return;
                }

                if(Integer.parseInt(receiveAmountEdt.getText().toString())>tailorOrders.getRamainingAmount()){
                    Toast.makeText(ReceiveMorePayment_Activity.this, "Kindly Enter Less or Equal Amount then "+tailorOrders.getRamainingAmount(), Toast.LENGTH_SHORT).show();
                    return;
                }

                updateOrderBill();
              /*  Intent intent=new Intent(ReceiveMorePayment_Activity.this,TailorOrder_Detail_Activity.class);
                intent.putExtra("OrderDetails",tailorOrders);
                intent.putExtra("ReceivedAmount",Integer.parseInt(receiveAmountEdt.getText().toString().trim()));
                startActivity(intent);*/

            }
        });


    }
    public void updateOrderBill(){

        orderData=new JSONObject();
        OrderReceivedAmountLog=new JSONObject();

        try {

            orderData.put("OrderId",tailorOrders.getOrderId());
            orderData.put("RamainingAmount",String.valueOf(tailorOrders.getRamainingAmount()-Integer.parseInt(receiveAmountEdt.getText().toString())));
            orderData.put("RecievedAmount",String.valueOf(Integer.parseInt(receiveAmountEdt.getText().toString())+tailorOrders.getRecievedAmount()));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            OrderReceivedAmountLog.put("Amount",String.valueOf(Integer.parseInt(receiveAmountEdt.getText().toString())));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String url;

        url= StaticData.baseUrl+"TailorPostApi/UpdatePrice";

        StringRequest req = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response != null) {

                    com.digiconvalley.tailordesk.Model.TailorOrders.OrderReceivedAmountLog orderReceivedAmountLog=new OrderReceivedAmountLog();
                    orderReceivedAmountLog.setAmount(Integer.parseInt(receiveAmountEdt.getText().toString()));
                    orderReceivedAmountLog.setDate(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
                    tailorOrders.getOrderReceivedAmounts().add(orderReceivedAmountLog);
                   /* tailorOrders.setRecievedAmount(Integer.parseInt(receiveAmountEdt.getText().toString())+tailorOrders.getRecievedAmount());*/

                    Intent intent=new Intent(ReceiveMorePayment_Activity.this,TailorOrder_Detail_Activity.class);
                    intent.putExtra("OrderDetails",tailorOrders);
                    intent.putExtra("ReceivedAmount",Integer.parseInt(receiveAmountEdt.getText().toString().trim()));
                    startActivity(intent);
                    finish();

                    progressDialog.dismiss();
                } else {
                    Toast.makeText(ReceiveMorePayment_Activity.this, "Server Error", Toast.LENGTH_SHORT).show();
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
}