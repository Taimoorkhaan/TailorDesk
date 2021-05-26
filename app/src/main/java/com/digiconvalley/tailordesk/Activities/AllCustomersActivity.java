package com.digiconvalley.tailordesk.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digiconvalley.tailordesk.Adapter.AllCustomerAdapter;
import com.digiconvalley.tailordesk.Adapter.CategoryAdapter;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.CatagoryMain;
import com.digiconvalley.tailordesk.Model.TailorCustomers;
import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.Singleton.SingletonPattren;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class AllCustomersActivity extends AppCompatActivity  {
    private RecyclerView rcvAllCustomers;
    private AllCustomerAdapter allCustomerAdapter;
    private ArrayList<TailorCustomers> tailorCustomers=new ArrayList<>();
    private ArrayList<TailorCustomers> tailorCustomersArrayList=new ArrayList<>();
    private EditText searchBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_customers);

        rcvAllCustomers=findViewById(R.id.rcv_Customers);
        searchBox=findViewById(R.id.searchBox);

        rcvAllCustomers.setLayoutManager(new LinearLayoutManager(this));

        if(getIntent()!=null){
            String shopId=getIntent().getStringExtra("TailorShopId");
            getAllCustomers(shopId);
        }

        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                   searchFilter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    public void getAllCustomers(String shopId) {

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String url;


        url = StaticData.baseUrl +"/TailorGetApi/GetCustomer?TailorShopId="+shopId;

        StringRequest req = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Gson gson = new Gson();


                    Type listType = new TypeToken<List<TailorCustomers>>() {
                    }.getType();

                    tailorCustomers = gson.fromJson(response, listType);
                    tailorCustomersArrayList.addAll(tailorCustomers);

                    progressDialog.dismiss();
                } else {
                    Toast.makeText(AllCustomersActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AllCustomersActivity.this, "Size is "+error.toString(), Toast.LENGTH_SHORT).show();
                //   progressDialog.dismiss();
                progressDialog.setTitle(error.toString());
                String message = null;

            }
        });


        req.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(AllCustomersActivity.this).addToRequestque(req);

    }
    public void searchFilter(CharSequence charSequence){
        tailorCustomers.clear();
        for(TailorCustomers tailorCustomerss:tailorCustomersArrayList){
            if(tailorCustomerss.getCustomerName().contains(charSequence)){
                 tailorCustomers.add(tailorCustomerss);
                 allCustomerAdapter.notifyDataSetChanged();
            }
        }
    }
}