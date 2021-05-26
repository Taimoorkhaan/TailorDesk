package com.digiconvalley.tailordesk.DrawerActivities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digiconvalley.tailordesk.Activities.EditTailor_Profile_Activity;
import com.digiconvalley.tailordesk.Activities.SetupDetail02;
import com.digiconvalley.tailordesk.Adapter.CategoryAdapter;
import com.digiconvalley.tailordesk.Adapter.ProfileCategory;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.CatagoryMain;
import com.digiconvalley.tailordesk.Model.TailorProfileApi.CategoryProfile;
import com.digiconvalley.tailordesk.Model.TailorProfileApi.TailorProfileGet;
import com.digiconvalley.tailordesk.Model.TailorProfileApi.TailorProfileMain;
import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.Singleton.SingletonPattren;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Profile extends AppCompatActivity {
    EditText profileTName,profileSName,profileTNumber,profileTEmail,profileTCountry,profileTCity,profileTAddress,profileTPin;
    private ImageView backBtn;
    private TextView editProfile;
    private RecyclerView rcv_Categories;
    private ProfileCategory profileCategory;
    private TailorProfileMain tailorProfileMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        backBtn=findViewById(R.id.back_button_profile);
        editProfile=findViewById(R.id.editProfileOption);
        rcv_Categories=findViewById(R.id.categories);

        profileTName=findViewById(R.id.profilePersonName_ed);
        profileSName=findViewById(R.id.profileShopName_ed);
        profileTNumber=findViewById(R.id.profilePhoneNumber_ed);
        profileTEmail=findViewById(R.id.profileEmail_ed);
        profileTCountry=findViewById(R.id.profileCountryAddress_ed);
        profileTCity=findViewById(R.id.profileCityAddress_ed);
        profileTAddress=findViewById(R.id.profileAreaAddress_ed);
        profileTPin=findViewById(R.id.profilePinLocation_ed);


        rcv_Categories.setLayoutManager(new LinearLayoutManager(this));

        getTailorProfile();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(tailorProfileMain!=null) {
                    Intent intent = new Intent(Profile.this, EditTailor_Profile_Activity.class);
                   intent.putExtra("TailorProfile",tailorProfileMain);
                    startActivity(intent);
                }
            }
        });
    }
    public void getTailorProfile() {

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String url;


        url = StaticData.baseUrl +"TailorGetApi/TailorProfile?TailorId="+StaticData.tailorProfile.getTailorID();

        StringRequest req = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Gson gson = new Gson();

                    tailorProfileMain=gson.fromJson(response,TailorProfileMain.class);


                    for(CategoryProfile categoryProfile:tailorProfileMain.getCategory())
                        categoryProfile.setChecked(true);
                    profileCategory=new ProfileCategory(Profile.this, (ArrayList<CategoryProfile>) tailorProfileMain.getCategory(),null);
                    rcv_Categories.setAdapter(profileCategory);

                //    profileTName,profileSName,profileTNumber,profileTEmail,profileTCountry,profileTCity,profileTAddress,profileTPin
                    profileTName.setText(tailorProfileMain.getTailorData().getTailorName());
                    profileSName.setText(tailorProfileMain.getTailorData().getTailorshopData().getTailorShopName());
                    profileTNumber.setText(tailorProfileMain.getTailorData().getTailorPhoneNo());
                    if(tailorProfileMain.getTailorData().getTailorEmail()!=null)
                    profileTEmail.setText(tailorProfileMain.getTailorData().getTailorEmail());

                    profileTCountry.setText(tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().getShopAddressCountryName());
                    profileTCity.setText(tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().getShopAddressCityName());
                    profileTAddress.setText(tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().getAddress());
                    profileTPin.setText(tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().getShopPinLocation());



                    progressDialog.dismiss();
                } else {
                    Toast.makeText(Profile.this, "Server Error", Toast.LENGTH_SHORT).show();
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
        SingletonPattren.getmInstance(Profile.this).addToRequestque(req);

    }
}