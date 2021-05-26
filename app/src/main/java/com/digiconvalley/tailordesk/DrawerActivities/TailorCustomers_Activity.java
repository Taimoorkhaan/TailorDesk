package com.digiconvalley.tailordesk.DrawerActivities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digiconvalley.tailordesk.Activities.AddCustomer;
import com.digiconvalley.tailordesk.Adapter.AllCustomerAdapter;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.TailorCustomers;
import com.digiconvalley.tailordesk.Model.TailorProfile;
import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.Singleton.SingletonPattren;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TailorCustomers_Activity extends AppCompatActivity implements AllCustomerAdapter.CustomerList{
    private ImageView plus_icon;
    private SharedPreferences pref ;
    private SharedPreferences.Editor editor;
    private TailorProfile tailorProfile;
    private ImageView backBtn;

    private RecyclerView rcvAllCustomers;
    private AllCustomerAdapter allCustomerAdapter;
    private ArrayList<TailorCustomers> tailorCustomers=new ArrayList<>();
    private ArrayList<TailorCustomers> tailorCustomersArrayList=new ArrayList<>();
    private EditText searchBox;
    private LinearLayout customerListView;
    private TextView noCustomer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_customers_);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        Gson gson=new Gson();
        tailorProfile= gson.fromJson(pref.getString("TailorBasicInfo",null),TailorProfile.class);


        backBtn=findViewById(R.id.back_button_customers);
        plus_icon=findViewById(R.id.plus_btn);
        customerListView=findViewById(R.id.customerlist);
        rcvAllCustomers=findViewById(R.id.rcv_Customers);
        searchBox=findViewById(R.id.searchBox);

        noCustomer=findViewById(R.id.nocustomerText);

        rcvAllCustomers.setLayoutManager(new LinearLayoutManager(this));
        getAllCustomers(tailorProfile.getTailorShopId());


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();

            }
        });
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

        plus_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view);
            }
        });

    }
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        final MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu, popup.getMenu());
        popup.show();

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                if(menuItem.getItemId()==R.id.add_new_conatct){
                    Intent intent=new Intent(TailorCustomers_Activity.this, AddCustomer.class);
                    startActivity(intent);

                }

                if(menuItem.getItemId()==R.id.select_phone_contact){
                   /* Intent intent=new Intent(CustomerHomeActivity.this,AllCustomersActivity.class);
                    intent.putExtra("TailorShopId",tailorProfile.getTailorShopId());
                    startActivity(intent);*/

                    Dexter.withContext(TailorCustomers_Activity.this)
                            .withPermission(Manifest.permission.READ_CONTACTS)
                            .withListener(new PermissionListener() {
                                @Override
                                public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                    Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                                    startActivityForResult(contactPickerIntent,1);
                                }

                                @Override
                                public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {


                                    AlertDialog builder = new AlertDialog.Builder(TailorCustomers_Activity.this)
                                            .setMessage("Permisssion Required")
                                            .setNegativeButton("no", null)
                                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent();
                                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                    intent.setData(Uri.fromParts("Package", getPackageName(), null));
                                                }
                                            })
                                            .show();

                                }

                                @Override
                                public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                    permissionToken.continuePermissionRequest();
                                }

                          /*  @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }*/
                            })
                            .check();

                }


                return false;
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 1){
            if(resultCode== Activity.RESULT_OK){

                Uri uri = data.getData();
                ContentResolver contentResolver = getContentResolver();
                Cursor contentCursor = contentResolver.query(uri, null, null,null, null);

                if(contentCursor.moveToFirst()){
                    String id = contentCursor.getString(contentCursor.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                    String hasPhone =
                            contentCursor.getString(contentCursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                    if (hasPhone.equalsIgnoreCase("1"))
                    {
                        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = "+ id,null, null);
                        phones.moveToFirst();
                        String contactNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));


                        Intent intent=new Intent(TailorCustomers_Activity.this,AddCustomer.class);
                        intent.putExtra("Number",contactNumber);
                        intent.putExtra("Name",name);
                        startActivity(intent);

                    }
                }
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public void searchFilter(CharSequence charSequence){
        tailorCustomers.clear();
        for(TailorCustomers tailorCustomerss:tailorCustomersArrayList){
            if(tailorCustomerss.getCustomerName().toLowerCase().contains(charSequence.toString().toLowerCase())){
                tailorCustomers.add(tailorCustomerss);
                allCustomerAdapter.notifyDataSetChanged();
            }
        }
    }

    public void getAllCustomers(String shopId) {


        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String url;


        url = StaticData.baseUrl +"TailorGetApi/GetCustomer?TailorShopId="+shopId;

        StringRequest req = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Gson gson = new Gson();


                    Type listType = new TypeToken<List<TailorCustomers>>() {
                    }.getType();

                    tailorCustomers = gson.fromJson(response, listType);
                    tailorCustomersArrayList.addAll(tailorCustomers);

                    if(tailorCustomers.size()>0){
                        customerListView.setVisibility(View.VISIBLE);
                        noCustomer.setVisibility(View.GONE);
                    }
                    allCustomerAdapter=new AllCustomerAdapter(TailorCustomers_Activity.this,tailorCustomers,TailorCustomers_Activity.this);
                    rcvAllCustomers.setAdapter(allCustomerAdapter);

                    progressDialog.dismiss();
                } else {
                    Toast.makeText(TailorCustomers_Activity.this, "Server Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(TailorCustomers_Activity.this, "Size is "+error.toString(), Toast.LENGTH_SHORT).show();
                //   progressDialog.dismiss();
                progressDialog.setTitle(error.toString());
                String message = null;

            }
        });


        req.setRetryPolicy(new DefaultRetryPolicy(50000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(TailorCustomers_Activity.this).addToRequestque(req);

    }

    @Override
    public void customerClick(TailorCustomers customers) {

       // Toast.makeText(this, "You Select "+customers.getCustomerName(), Toast.LENGTH_SHORT).show();
      /*  Intent intent=new Intent(TailorCustomers_Activity.this,SelectCategoryActivity.class);
        StaticData.customerid=customers.getCustomerId().toString();
        StaticData.tailorShopId=tailorProfile.getTailorShopId().toString();
        startActivity(intent);*/


    }
}