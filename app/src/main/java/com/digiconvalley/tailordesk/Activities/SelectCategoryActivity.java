package com.digiconvalley.tailordesk.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digiconvalley.tailordesk.Adapter.ServiceAdaptor;
import com.digiconvalley.tailordesk.Model.CatagoryMain;
import com.digiconvalley.tailordesk.Model.Service;
import com.digiconvalley.tailordesk.Model.TailorCustomers;
import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.Singleton.SingletonPattren;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SelectCategoryActivity extends AppCompatActivity implements ServiceAdaptor.ServicesList {

    private ArrayList<CatagoryMain> catagoryMainArrayList = new ArrayList<>();
    private ArrayList<Integer> integerArrayList=new ArrayList<>();
    private ArrayList<CatagoryMain> catagoryMains=new ArrayList<>();
    private ArrayList<Service> serviceArrayList=new ArrayList<>();
    private ArrayList<Service> serviceArrayList2=new ArrayList<>();
    private Spinner categorySpinner;
    private ServiceAdaptor serviceAdaptor;
    private RecyclerView rcv_Servies;
    private ImageButton backBtn;
    private LinearLayout innerLayout;
    private EditText serachcategory;
    private ArrayList<String> spinnerList=new ArrayList<>();
    private int activeIndex;
    private TailorCustomers tailorCustomers;
    private TextView toolbarText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category2);

        categorySpinner=findViewById(R.id.category);
        rcv_Servies=findViewById(R.id.rcv_service);
        backBtn=findViewById(R.id.back_button);
        innerLayout=findViewById(R.id.innerLayout);
        serachcategory=findViewById(R.id.serachcategory);
        toolbarText=findViewById(R.id.title);


        spinnerList.add("Choose Category");
        spinnerList.add("SHOW ALL ITEMS");


        if(getIntent()!=null && getIntent().hasExtra("Customer")){

            tailorCustomers= (TailorCustomers) getIntent().getSerializableExtra("Customer");
            StaticData.customerid=tailorCustomers.getCustomerId().toString();

        }

        rcv_Servies.setLayoutManager(new LinearLayoutManager(this));
        getcategory();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        serachcategory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                searchServices(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public void getcategory() {

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String url;


        url = StaticData.baseUrl +"TailorGetApi/AllServices";

        StringRequest req = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Gson gson = new Gson();


                    Type listType = new TypeToken<List<CatagoryMain>>() {
                    }.getType();

                    catagoryMainArrayList = gson.fromJson(response, listType);

                    for(CatagoryMain catagoryMain:catagoryMainArrayList)

                        spinnerList.add(catagoryMain.getCatagoryName());

                    ArrayAdapter<String> spinnerArrayAdapter1 = new ArrayAdapter<String>(SelectCategoryActivity.this,
                            R.layout.spinner_design,R.id.spinnerText, spinnerList);
                    // spinnerArrayAdapter1.setDropDownViewResource(R.layout.spinner_design); // The drop down view
                    categorySpinner.setAdapter(spinnerArrayAdapter1);




                    categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            serviceArrayList.clear();
                            serviceArrayList2.clear();
                            if(i>0)
                                toolbarText.setText("Select Item");
                            else
                                toolbarText.setText("Select category");
                            if(i>1) {
                                innerLayout.setVisibility(View.VISIBLE);
                                activeIndex=i-2;
                                if (catagoryMainArrayList.get(i-2).getServices().size() > 0) {
                                    serviceArrayList.clear();
                                    serviceArrayList.addAll(catagoryMainArrayList.get(i-2).getServices());
                                    serviceAdaptor = new ServiceAdaptor(SelectCategoryActivity.this, serviceArrayList,SelectCategoryActivity.this);
                                    rcv_Servies.setAdapter(serviceAdaptor);
                                }
                            }
                            else if(i==1){
                                innerLayout.setVisibility(View.VISIBLE);
                                activeIndex=-2;
                                for(CatagoryMain catagoryMain:catagoryMainArrayList){
                                    for(Service service:catagoryMain.getServices()){
                                        serviceArrayList2.add(service);
                                    }
                                }

                                serviceArrayList.addAll(serviceArrayList2);
                                if (serviceArrayList.size() > 0) {
                                    serviceAdaptor = new ServiceAdaptor(SelectCategoryActivity.this, serviceArrayList2,SelectCategoryActivity.this);
                                    rcv_Servies.setAdapter(serviceAdaptor);
                                }
                            }
                            else {
                                activeIndex=-1;
                                innerLayout.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    catagoryMains.addAll(catagoryMainArrayList);
                   /* adapter = new CategoryAdapter(SetupDetail02.this,catagoryMainArrayList,SetupDetail02.this);
                    recyclerView.setAdapter(adapter);*/

                      /*  for(CatagoryMain catagoryMain:catagoryMainArrayList){
                            CheckBox catName;
                            final View subView = LayoutInflater.from(SetupDetail02.this).inflate(R.layout.cat_view, null);
                            catName=subView.findViewById(R.id.catName);
                            catName.setText(catagoryMain.getCatagoryName());
                            catView.addView(subView);
                            catViews.add(subView);
                        }*/

                    Toast.makeText(SelectCategoryActivity.this, "" + catagoryMainArrayList.size(), Toast.LENGTH_SHORT).show();

                    progressDialog.dismiss();
                } else {
                    Toast.makeText(SelectCategoryActivity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(SelectCategoryActivity.this, "Size is "+error.toString(), Toast.LENGTH_SHORT).show();
                //   progressDialog.dismiss();
                progressDialog.setTitle(error.toString());
                String message = null;

            }
        });


        req.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(SelectCategoryActivity.this).addToRequestque(req);

    }

    public void searchServices(CharSequence sequence) {
        serviceArrayList2.clear();
        if (activeIndex == -2) {
            for (Service service : serviceArrayList) {
                if (service.getServiceName().toLowerCase().contains(sequence.toString().toLowerCase())) {
                    serviceArrayList2.add(service);
                    serviceAdaptor.notifyDataSetChanged();

                }
            }
        } else {
            serviceArrayList.clear();
            for (Service service : catagoryMainArrayList.get(activeIndex).getServices()) {
                if (service.getServiceName().toLowerCase().contains(sequence.toString().toLowerCase())) {
                    serviceArrayList.add(service);
                    serviceAdaptor.notifyDataSetChanged();

                }
            }
        }
    }

    @Override
    public void ServiceOnClick(Service service) {
        Intent intent=new Intent(SelectCategoryActivity.this,CreateOrder_Activity.class);

        intent.putExtra("SelectedService",service);
        intent.putExtra("Customer",tailorCustomers);
        startActivity(intent);
    }
}