package com.digiconvalley.tailordesk.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digiconvalley.tailordesk.Adapter.CheckOutAdaptor;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.CreateOrder;
import com.digiconvalley.tailordesk.Model.MeasurementDetail;
import com.digiconvalley.tailordesk.Model.ServicePart;
import com.digiconvalley.tailordesk.Model.TailorOrders.TailorOrders;
import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.Singleton.SingletonPattren;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class CheckOutActivity extends AppCompatActivity implements CheckOutAdaptor.Checkout {
    private RecyclerView rcv_CheckOut;
    private CheckOutAdaptor checkOutAdaptor;
    private TextView addAmount, amountRecevied, remainingBalance, totalBill, deliveryDate,
            addDiscountAmount, discountAmount, balance, toolBarTitle;

    private int bill = 0, remainBalance = 0, remainBalanceD = 0, order_no;
    private Button confirmBtn;
    private JSONArray orderSuit;
    private JSONArray SuitMeasurement = new JSONArray();
    private JSONArray SuitImageArr;
    private JSONObject orderObjj, receivedAmountLog;
    private int max = 50000;
    private int min = 100;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String delivery_Date, discountPrice, remainingBill;
    private ImageView back_btn;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        rcv_CheckOut = findViewById(R.id.rcv_checkout);
        addAmount = findViewById(R.id.addAmount);
        amountRecevied = findViewById(R.id.amount);
        remainingBalance = findViewById(R.id.remainingBalance);
        totalBill = findViewById(R.id.totalbill);
        confirmBtn = findViewById(R.id.Confirm_Order);
        deliveryDate = findViewById(R.id.delivery_date);
        back_btn = findViewById(R.id.back_button);
        addDiscountAmount = findViewById(R.id.addDiscountAmount);
        discountAmount = findViewById(R.id.discountamount);
        toolBarTitle = findViewById(R.id.title);
        balance = findViewById(R.id.balance);


        toolBarTitle.setText("Order# " + StaticData.orderNo);

        for (CreateOrder createOrder : StaticData.createOrders) {
            bill = bill + Integer.parseInt(createOrder.getStitchingPrice());
            remainBalance = bill;
        }


        totalBill.setText("" + bill);
        remainingBalance.setText("" + bill);

        if (getIntent() != null && getIntent().hasExtra("Amount")) {

            delivery_Date = getIntent().getStringExtra("DeliveryDate");
            discountPrice = getIntent().getStringExtra("DiscountPrice");

            if (delivery_Date != null)
                deliveryDate.setText(delivery_Date);

            amountRecevied.setText(getIntent().getStringExtra("Amount"));
            remainBalance = bill - Integer.parseInt((getIntent().getStringExtra("Amount")));
            remainBalanceD = remainBalance;
            if (discountPrice != null) {
                discountAmount.setText(discountPrice);
                remainBalance = remainBalance - Integer.parseInt(discountPrice);
                remainBalanceD = remainBalance;
            }
            remainingBill = String.valueOf(remainBalance);

            if (remainingBill.substring(0, 1).equalsIgnoreCase("-")) {
                remainingBalance.setText("" + String.valueOf(remainBalance).substring(1, remainingBill.length()));
                remainingBalance.setTextColor(getResources().getColor(R.color.green));
                balance.setText("Balance");
                balance.setTextColor(getResources().getColor(R.color.green));
            } else {
                remainingBalance.setText("" + remainBalance);
                remainingBalance.setTextColor(getResources().getColor(R.color.red));
                balance.setText("Balance Due");
                balance.setTextColor(getResources().getColor(R.color.red));
            }
        }


        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        checkOutAdaptor = new CheckOutAdaptor(this, StaticData.createOrders, this);

        rcv_CheckOut.setLayoutManager(new LinearLayoutManager(this));
        rcv_CheckOut.setAdapter(checkOutAdaptor);

        amountRecevied.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CheckOutActivity.this, AddAmount_Activity.class);
                intent.putExtra("Bill", bill);
                intent.putExtra("RemainingAmount", remainBalance);
                if (!deliveryDate.getText().toString().trim().isEmpty())
                    intent.putExtra("DeliveryDate", deliveryDate.getText().toString().trim());
                if (!discountAmount.getText().toString().equalsIgnoreCase("0"))
                    intent.putExtra("DiscountPrice", discountAmount.getText().toString().trim());

                startActivity(intent);
                finish();

            }
        });


        deliveryDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                final DatePickerDialog datePickerDialog = new DatePickerDialog(CheckOutActivity.this, R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                monthOfYear++;

                                String day, month;
                                day = String.valueOf(dayOfMonth);
                                month = String.valueOf(monthOfYear);

                                if ((day.length() < 2))
                                    day = "0" + day;

                                if (month.length() < 2)
                                    month = "0" + month;

                                deliveryDate.setText(day + "/" + month + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                c.add(Calendar.YEAR, 0);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.show();
            }
        });


        addDiscountAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDiscount();
            }
        });

        discountAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addDiscount();
            }
        });

        addAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(CheckOutActivity.this, AddAmount_Activity.class);
                intent.putExtra("Bill", bill);
                intent.putExtra("RemainingAmount", remainBalance);
                if (!deliveryDate.getText().toString().trim().isEmpty())
                    intent.putExtra("DeliveryDate", deliveryDate.getText().toString().trim());
                if (!discountAmount.getText().toString().equalsIgnoreCase("0"))
                    intent.putExtra("DiscountPrice", discountAmount.getText().toString().trim());
                startActivity(intent);
                finish();

            }
        });

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateOrder();
            }
        });
    }

    @Override
    public void removeService(CreateOrder createOrder) {
        Toast.makeText(this, createOrder.getServiceName() + " Removed", Toast.LENGTH_SHORT).show();
        StaticData.createOrders.remove(createOrder);
        checkOutAdaptor.notifyDataSetChanged();
        bill = 0;
        for (CreateOrder createOrder1 : StaticData.createOrders) {
            bill = bill + Integer.parseInt(createOrder1.getStitchingPrice());
        }
        totalBill.setText("" + bill);
        remainingBalance.setText("" + bill);
    }

    @Override
    public void editService(CreateOrder createOrder, int index) {

        Intent intent = new Intent(CheckOutActivity.this, CreateOrder_Activity.class);
        intent.putExtra("EditOrder", createOrder);
        intent.putExtra("Position", index);
        startActivity(intent);
        finish();

        bill = 0;
        for (CreateOrder createOrder1 : StaticData.createOrders) {
            bill = bill + Integer.parseInt(createOrder1.getStitchingPrice());
        }
        totalBill.setText("" + bill);
        remainingBalance.setText("" + bill);
    }

    /* @Override
     public void editService(CreateOrder createOrder int postion) {
         Toast.makeText(this, "Edit = "+createOrder.getServiceName(), Toast.LENGTH_SHORT).show();

         Intent intent=new Intent(CheckOutActivity.this,CreateOrder_Activity.class);
         intent.putExtra("EditOrder",createOrder);
         intent.putExtra("Position",position);
         startActivity(intent);
     }*/
    public void CreateOrder() {

        if (StaticData.createOrders.size() < 0)
            return;

        if (deliveryDate.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Kindly Select Delivery Date ", Toast.LENGTH_SHORT).show();
            return;
        }


        receivedAmountLog = new JSONObject();
        SuitMeasurement = new JSONArray();
        SuitImageArr = new JSONArray();
        orderSuit = new JSONArray();

        for (CreateOrder createOrder : StaticData.createOrders) {
            JSONObject orderObj = new JSONObject();
            try {

                orderObj.put("CustomerId", createOrder.getCustomerId());
                orderObj.put("ServiceId", createOrder.getServiceId());
                orderObj.put("OrderSuitNo", StaticData.createOrders.size());
                orderObj.put("OrderSuitName", createOrder.getServiceName());
                orderObj.put("OrderSuitPic1", createOrder.getCloth01ImageName());
                orderObj.put("OrderSuitPic2", createOrder.getCloth02ImageName());
                orderObj.put("OrderPatternPic1", createOrder.getPattern01ImageName());
                orderObj.put("OrderPatternPic2", createOrder.getPattern02ImageName());

                orderObj.put("OrderSuitDesc", createOrder.getSpecialNote());
                orderObj.put("orderSuitStatus", "Order Pending");
                orderObj.put("OrderSuitPrice", createOrder.getStitchingPrice());

                if (createOrder.getNoOfPocket() != null) {
                    orderObj.put("NumberOfPocket", createOrder.getNoOfPocket());
                    orderObj.put("IndexOfPocket", createOrder.getNoPocketIndex());
                }

                orderObj.put("PocketType", createOrder.getPocketMode());
                orderObj.put("Audio", "");

                if (createOrder.getNoOfPleats() != null) {
                    orderObj.put("Pleats", createOrder.getNoOfPleats());
                    orderObj.put("IndexOfPleats", createOrder.getNoPleatsIndex());
                }

                orderObj.put("ItemNumber", createOrder.getItemNo());
                orderObj.put("OrderSuitType", createOrder.getStitchingMode());
                orderObj.put("UrgentStatus", createOrder.getUrgentNeed());
                orderObj.put("CollarType", createOrder.getCollarType());
                orderObj.put("IndexOfCollar", createOrder.getCollarIndex());


                if (!createOrder.getDevlieryDate().isEmpty())
                    orderObj.put("DeliveryDate", createOrder.getDevlieryDate().trim());
                delivery_Date = createOrder.getDevlieryDate();

            } catch (JSONException e) {
                e.printStackTrace();
            }

            orderSuit.put(orderObj);


            for (ServicePart servicePart : createOrder.getNoMeasurement()) {
                JSONObject measurementObj = new JSONObject();
                if (servicePart.getMeasurementValue() != null) {
                    try {
                        measurementObj.put("ServicePartId", servicePart.getServicePartId());
                        measurementObj.put("SuitMeasurementValue", servicePart.getMeasurementValue());
                        measurementObj.put("ItemNumber", createOrder.getItemNo());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    SuitMeasurement.put(measurementObj);
                }
            }

            JSONObject suitImage = new JSONObject();

            try {

                suitImage.put("ClothPic1Name", createOrder.getCloth01Name());
                suitImage.put("ClothPic2Name", createOrder.getCloth02Name());
                suitImage.put("PatternPic1Name", createOrder.getPattern01Name());
                suitImage.put("PatternPic2Name", createOrder.getPattern02Name());

                suitImage.put("ItemNumber", createOrder.getItemNo());
                suitImage.put("OrderSuitStatus", "Order Pending");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            SuitImageArr.put(suitImage);
        }
        orderObjj = new JSONObject();
        try {
            String orderDescription = null;
            StringBuilder stringBuilder = new StringBuilder();

            orderObjj.put("TailorShopId", StaticData.tailorShopId);
            orderObjj.put("OrderNo", StaticData.orderNo);
            for (CreateOrder createOrder : StaticData.createOrders) {

                orderDescription = String.valueOf(stringBuilder.append(createOrder.getServiceName()));
                orderDescription = String.valueOf(stringBuilder.append(" ( " + createOrder.getItemNo() + " )"));
                orderDescription = String.valueOf(stringBuilder.append(","));
            }

            orderObjj.put("OrderDesc", orderDescription);
            orderObjj.put("TotalSuits", StaticData.createOrders.size());
            orderObjj.put("TotalPrice", bill);
            if (remainingBalance.getText().toString().substring(0, 1).equalsIgnoreCase("-"))
                orderObjj.put("RamainingAmount", Integer.parseInt(remainingBalance.getText().toString().substring(1)));
            else
                orderObjj.put("RamainingAmount", Integer.parseInt(remainingBalance.getText().toString()));

            orderObjj.put("RecievedAmount", Integer.parseInt(amountRecevied.getText().toString()));
            orderObjj.put("OrderStatus", "Order Pending");

            final Calendar c = Calendar.getInstance();

            int mYear = c.get(Calendar.YEAR);
            int mMonth = c.get(Calendar.MONTH);
            int mDay = c.get(Calendar.DAY_OF_MONTH);

            mMonth++;
            String day, month;
            day = String.valueOf(mDay);
            month = String.valueOf(mMonth);

            if ((day.length() < 2))
                day = "0" + day;

            if (month.length() < 2)
                month = "0" + month;

            orderObjj.put("OrderDate", "" + day + "/" + month + "/" + mYear);
            orderObjj.put("OrderDeliveryDate", deliveryDate.getText().toString().trim());
            orderObjj.put("TotalPerson", "1");
            orderObjj.put("DiscountAmount", discountAmount.getText().toString().trim());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            receivedAmountLog.put("Amount", Integer.parseInt(amountRecevied.getText().toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, StaticData.baseUrl + "TailorPostApi/CreateOrder", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                if (response != null) {
                    progressDialog.dismiss();

                    if (response.toString().equalsIgnoreCase("Invalid Data")) {
                        Toast.makeText(CheckOutActivity.this, "Something went Wrong try Again", Toast.LENGTH_SHORT).show();
                    } else {


                        Intent intent = new Intent(CheckOutActivity.this, OrderThankyouActivity.class);
                        intent.putExtra("TotalBill", String.valueOf(bill));
                        intent.putExtra("ReceivedAmount", amountRecevied.getText().toString());
                        intent.putExtra("RemainingAmount", String.valueOf(remainBalance));
                        intent.putExtra("Discount", String.valueOf(discountAmount.getText()));

                        intent.putExtra("Order_no", StaticData.orderNo);
                        startActivity(intent);
                        finish();

                    }
                } else {
                    progressDialog.dismiss();
                    Log.e("Your Array Response", "Data Null");

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(CheckOutActivity.this, "Error " + error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                Log.e("error is ", "" + error);
            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("OrderSuit", orderSuit.toString());
                params.put("SuitMeasurement", SuitMeasurement.toString());
                params.put("SuitImage", SuitImageArr.toString());
                params.put("Order", orderObjj.toString());
                params.put("orderReceivedAmountLog", receivedAmountLog.toString());

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(this).addToRequestque(request);

    }

    public void addDiscount() {

        Button addAmountbtn, cancelbtn;
        final EditText addAmount;

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.discount_layout);

        addAmountbtn = dialog.findViewById(R.id.addbtnDiscount);
        cancelbtn = dialog.findViewById(R.id.cancelbtnDiscount);
        addAmount = dialog.findViewById(R.id.add_amount_discount);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        addAmountbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

              /*  if(Integer.parseInt(addAmount.getText().toString())>Integer.parseInt(remainingBalance.getText().toString())){
                    Toast.makeText(CheckOutActivity.this, "Kindly Enter Equal or Less then Total Bill", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                if (!addAmount.getText().toString().isEmpty())
                    discountAmount.setText(addAmount.getText().toString().trim());

                if (!amountRecevied.getText().toString().equalsIgnoreCase("0")) {
                    remainBalance = Integer.parseInt(totalBill.getText().toString()) - (Integer.parseInt(amountRecevied.getText().toString()) +
                            Integer.parseInt(discountAmount.getText().toString()));
                } else {
                    remainBalance = Integer.parseInt(discountAmount.getText().toString());
                }

               /* if(!String.valueOf(remainBalance).contains("-"))
                    remainBalance = remainBalanceD-Integer.parseInt(discountAmount.getText().toString());
                else
                    remainBalance = remainBalanceD;*/


                //  remainBalanceD=remainBalance;
                remainingBill = String.valueOf(remainBalance);


                if (remainingBill.substring(0, 1).equalsIgnoreCase("-")) {
                    remainingBalance.setText("" + String.valueOf(remainBalance).substring(1, remainingBill.length()));
                    remainingBalance.setTextColor(getResources().getColor(R.color.green));
                    balance.setText("Balance");
                    balance.setTextColor(getResources().getColor(R.color.green));
                } else {
                    remainingBalance.setText("" + remainBalance);
                    remainingBalance.setTextColor(getResources().getColor(R.color.red));
                    balance.setText("Balance Due");
                    balance.setTextColor(getResources().getColor(R.color.red));
                }


                dialog.dismiss();
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}