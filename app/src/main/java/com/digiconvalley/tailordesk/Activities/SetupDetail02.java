package com.digiconvalley.tailordesk.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digiconvalley.tailordesk.Adapter.CategoryAdapter;
import com.digiconvalley.tailordesk.Model.CatagoryMain;
import com.digiconvalley.tailordesk.Model.TailorBasicProfile;
import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.Singleton.SingletonPattren;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SetupDetail02 extends AppCompatActivity  implements CategoryAdapter.CategoryCheck{

    Button Next_Btn;
    ImageButton back_button;
    EditText searchCategory_Name;
    ImageButton Add_Btn;
    ImageButton Remove_Category;
    RecyclerView recyclerView;
    ArrayList<String> arraylist;
    CategoryAdapter adapter;
    private TailorBasicProfile tailorBasicProfile;
    private ArrayList<CatagoryMain> catagoryMainArrayList = new ArrayList<>();
    private LinearLayout catView;
    private ArrayList<View> catViews=new ArrayList<>();
    private ArrayList<Integer> integerArrayList=new ArrayList<>();
    private ArrayList<CatagoryMain> catagoryMains=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_detail02);

        recyclerView = findViewById(R.id.recyclerView);
        searchCategory_Name = findViewById(R.id.serachcategory);
        back_button = findViewById(R.id.back_button);
        Next_Btn = findViewById(R.id.next_btn);
        catView=findViewById(R.id.catView);


        if (getIntent() != null) {
            tailorBasicProfile = (TailorBasicProfile) getIntent().getSerializableExtra("tailorBasicInfo");
        }

        getcatagory();
        Next_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(CatagoryMain catagoryMain:catagoryMainArrayList){
                    if(catagoryMain.getChecked()){
                        integerArrayList.add(catagoryMain.getCatagoryId());
                    }
                }
                if(integerArrayList.size()>0){
                    tailorBasicProfile.setIntegerArrayList(integerArrayList);
                }
                else{
                    Toast.makeText(SetupDetail02.this, "Kindly select Atleast one ", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(SetupDetail02.this, SetupDetail03.class);
                intent.putExtra("tailorBasicInfo", tailorBasicProfile);
                startActivity(intent);
            }
        });
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });


        searchCategory_Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                searchCategory(1,i1,charSequence);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                  adapter.notifyDataSetChanged();
            }
        });



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }

    public void getcatagory() {

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

                        catagoryMains.addAll(catagoryMainArrayList);
                        adapter = new CategoryAdapter(SetupDetail02.this,catagoryMainArrayList,SetupDetail02.this);
                        recyclerView.setAdapter(adapter);

                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(SetupDetail02.this, "Server Error", Toast.LENGTH_SHORT).show();
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
            SingletonPattren.getmInstance(SetupDetail02.this).addToRequestque(req);

        }

        public void searchCategory(int i,int i1,CharSequence sequence){
              catagoryMains.clear();
              for(CatagoryMain catagoryMain:catagoryMainArrayList){
                  if(catagoryMain.getCatagoryName().toLowerCase().contains(sequence.toString().toLowerCase())){
                      catagoryMains.add(catagoryMain);
                     // adapter.notifyDataSetChanged();
                      adapter = new CategoryAdapter(SetupDetail02.this,catagoryMains,SetupDetail02.this);
                      recyclerView.setAdapter(adapter);
                  }
              }
        }

    @Override
    public void CategoryCicked(CatagoryMain catagoryMain, Boolean value) {
        for(CatagoryMain catagoryMain1:catagoryMainArrayList){
            if(catagoryMain.getCatagoryId().equals(catagoryMain1.getCatagoryId())){
               catagoryMain1.setChecked(value);
              recyclerView.post(new Runnable() {
                  @Override
                  public void run() {
                 adapter.notifyDataSetChanged();
                  }
              });
               break;
            }
        }
    }
}
