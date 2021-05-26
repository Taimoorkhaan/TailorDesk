package com.digiconvalley.tailordesk.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digiconvalley.tailordesk.Adapter.CategoryAdapter;
import com.digiconvalley.tailordesk.Adapter.ProfileCategory;
import com.digiconvalley.tailordesk.Adapter.ServiceAdaptor;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.DrawerActivities.Profile;
import com.digiconvalley.tailordesk.Maps.TailorLocation_Activity;
import com.digiconvalley.tailordesk.Model.CatagoryMain;
import com.digiconvalley.tailordesk.Model.Service;
import com.digiconvalley.tailordesk.Model.TailorProfile;
import com.digiconvalley.tailordesk.Model.TailorProfileApi.CategoryProfile;
import com.digiconvalley.tailordesk.Model.TailorProfileApi.TailorProfileGet;
import com.digiconvalley.tailordesk.Model.TailorProfileApi.TailorProfileMain;
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
import com.mikelau.croperino.Croperino;
import com.mikelau.croperino.CroperinoConfig;
import com.mikelau.croperino.CroperinoFileUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditTailor_Profile_Activity extends AppCompatActivity implements ProfileCategory.CategoryEdit, CategoryAdapter.CategoryCheck {

    private EditText profileTName,profileSName,profileTNumber,profileTEmail,profileTCountry,profileTCity,profileTAddress;
    private ImageView backBtn;
    private Button saveProfile;
    private TextView profileTPin,profileSLogo,addCategory;
    private ArrayList<CatagoryMain> catagoryMainArrayList = new ArrayList<>();

    private RecyclerView rcv_Categories;
    private ProfileCategory profileCategory;
    private TailorProfileMain tailorProfileMain;
    private String Rimage,tailorPicture;
    private Dialog dialog;
    RecyclerView categoryRcv;
    CategoryAdapter adapter;

    private Uri tailorShopImageURI;
    private JSONObject tailorBasicInfo,tailorShopAddress,tailorShop,tailorShopLogo;
    private JSONArray tailorCategories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile__avtivity);

        backBtn=findViewById(R.id.back_button_profile);
        rcv_Categories=findViewById(R.id.categories);

        addCategory=findViewById(R.id.addCategory);
        saveProfile=findViewById(R.id.saveProfile);
        profileSLogo=findViewById(R.id.tailorShopLogo);


        profileTName=findViewById(R.id.profilePersonName_ed);
        profileSName=findViewById(R.id.profileShopName_ed);
        profileTNumber=findViewById(R.id.profilePhoneNumber_ed);
        profileTEmail=findViewById(R.id.profileEmail_ed);
        profileTCountry=findViewById(R.id.profileCountryAddress_ed);
        profileTCity=findViewById(R.id.profileCityAddress_ed);
        profileTAddress=findViewById(R.id.profileAreaAddress_ed);
        profileTPin=findViewById(R.id.profilePinLocation_ed);

        rcv_Categories.setLayoutManager(new LinearLayoutManager(this));

        if(getIntent()!=null && getIntent().hasExtra("TailorProfile")){
            tailorProfileMain= (TailorProfileMain) getIntent().getSerializableExtra("TailorProfile");

            profileCategory=new ProfileCategory(EditTailor_Profile_Activity.this, (ArrayList<CategoryProfile>) tailorProfileMain.getCategory(),
                    EditTailor_Profile_Activity.this);
            rcv_Categories.setAdapter(profileCategory);

            profileTName.setText(tailorProfileMain.getTailorData().getTailorName());
            profileSName.setText(tailorProfileMain.getTailorData().getTailorshopData().getTailorShopName());
            profileTNumber.setText(tailorProfileMain.getTailorData().getTailorPhoneNo());
            if(tailorProfileMain.getTailorData().getTailorEmail()!=null)
                profileTEmail.setText(tailorProfileMain.getTailorData().getTailorEmail());

            profileTCountry.setText(tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().getShopAddressCountryName());
            profileTCity.setText(tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().getShopAddressCityName());
            profileTAddress.setText(tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().getAddress());
            profileTPin.setText(tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().getShopPinLocation());
            if(tailorProfileMain.getTailorData().getShopLogoImage()!=null)
                profileSLogo.setText(tailorProfileMain.getTailorData().getShopLogoImageName());

            if(tailorProfileMain.getTailorData().getTailorshopData().getTailorShopLogo()!=null){
                profileSLogo.setText(tailorProfileMain.getTailorData().getTailorshopData().getTailorShopLogo());
            }

            getcategory();
        }

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        profileTPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(profileTName.getText().toString().isEmpty()){
                    Toast.makeText(EditTailor_Profile_Activity.this, "Please Enter Your Name ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(profileSName.getText().toString().isEmpty()){
                    Toast.makeText(EditTailor_Profile_Activity.this, "Please Enter Your Name ", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(profileTCity.getText().toString().isEmpty()){
                    Toast.makeText(EditTailor_Profile_Activity.this, "Please Enter Your Name ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(profileTAddress.getText().toString().isEmpty()){
                    Toast.makeText(EditTailor_Profile_Activity.this, "Please Enter Your Name ", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(profileTPin.getText().toString().isEmpty()){
                    Toast.makeText(EditTailor_Profile_Activity.this, "Please Select Your Location ", Toast.LENGTH_SHORT).show();
                    return;
                }

                tailorProfileMain.getTailorData().setTailorName(profileTName.getText().toString().trim());
                tailorProfileMain.getTailorData().getTailorshopData().setTailorShopName(profileSName.getText().toString().trim());

                tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().setShopAddressCityName(profileTCity.getText().toString().trim());
                tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().setAddress(profileTAddress.getText().toString().trim());


                Intent intent=new Intent(EditTailor_Profile_Activity .this, TailorLocation_Activity.class);
                intent.putExtra("TailorProfile",tailorProfileMain);
                startActivity(intent);
                finish();
            }
        });

        profileSLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(EditTailor_Profile_Activity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/MikeLau/Pictures", "/sdcard/MikeLau/Pictures");
                                CroperinoFileUtil.verifyStoragePermissions(EditTailor_Profile_Activity.this);
                                CroperinoFileUtil.setupDirectory(EditTailor_Profile_Activity.this);

                                Croperino.prepareChooser(EditTailor_Profile_Activity.this, "Select Image", ContextCompat.getColor(EditTailor_Profile_Activity.this, android.R.color.background_dark));
                       /* Intent intent=new Intent(MainActivity.this,MapTraining.class);
                        startActivity(intent);
                        finish();*/

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {


                                AlertDialog builder = new AlertDialog.Builder(EditTailor_Profile_Activity.this)
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

                        })
                        .check();

            }
        });
        saveProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditTailor_Profile_Activity.this, "Profile Saved......", Toast.LENGTH_SHORT).show();
              /*  Intent intent=new Intent(EditTailor_Profile_Activity.this,MainHome_Activity.class);
                startActivity(intent);*/
                editTailorProfile();
            }
        });

        addCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Button btnOk;

                dialog=new Dialog(EditTailor_Profile_Activity.this);
                dialog.setContentView(R.layout.categories_dialog_view);

                categoryRcv=dialog.findViewById(R.id.selectCategory);
                btnOk=dialog.findViewById(R.id.selectCategorybtn);

                categoryRcv.setLayoutManager(new LinearLayoutManager(dialog.getContext()));
                adapter = new CategoryAdapter(EditTailor_Profile_Activity.this,tailorProfileMain.getCatagoryMainArrayList(),EditTailor_Profile_Activity.this);
                categoryRcv.setAdapter(adapter);

                btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        dialog.dismiss();
                        if(tailorProfileMain.getCatagoryMainArrayList().size()==0){
                            tailorProfileMain.getCategory().clear();
                            profileCategory.notifyDataSetChanged();
                            adapter.notifyDataSetChanged();
                        }
                        else {
                            tailorProfileMain.getCategory().clear();
                                for (CatagoryMain catagoryMain : tailorProfileMain.getCatagoryMainArrayList()) {
                                    if(catagoryMain.getChecked()){
                                        CategoryProfile categoryProfile=new CategoryProfile(catagoryMain.getCatagoryId(),catagoryMain.getCatagoryName());
                                        tailorProfileMain.getCategory().add(categoryProfile);
                                    }
                            }
                            profileCategory.notifyDataSetChanged();
                            adapter.notifyDataSetChanged();
                        }

                    }

                });

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                dialog.show();
            }
        });


    }

    @Override
    public void removeCategory(CategoryProfile categoryProfile, int index) {
        Toast.makeText(this, ""+categoryProfile.getCatagoryName(), Toast.LENGTH_SHORT).show();
        tailorProfileMain.getCategory().remove(index);
        profileCategory.notifyDataSetChanged();
        for(CatagoryMain catagoryMain:tailorProfileMain.getCatagoryMainArrayList()){
            if(categoryProfile.getCatagoryId().equals(catagoryMain.getCatagoryId())){
                catagoryMain.setChecked(false);
            }
        }
        if(adapter!=null)
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CroperinoConfig.REQUEST_TAKE_PHOTO:
                if (resultCode == Activity.RESULT_OK) {
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), this, true, 1, 1, R.color.gray, R.color.gray);
                }
                break;
            case CroperinoConfig.REQUEST_PICK_FILE:
                if (resultCode == Activity.RESULT_OK) {

                    CroperinoFileUtil.newGalleryFile(data, this);

                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), this, true, 1, 1, R.color.gray, R.color.gray_variant);
                }
                break;
            case CroperinoConfig.REQUEST_CROP_PHOTO:
                if (resultCode == Activity.RESULT_OK) {


                    tailorShopImageURI = Uri.fromFile(CroperinoFileUtil.getTempFile());


                    profileSLogo.setText(tailorShopImageURI.getLastPathSegment());
                    tailorProfileMain.getTailorData().setShopLogoImageName(tailorShopImageURI.getLastPathSegment());
                    tailorPicture = tailorShopImageURI.getLastPathSegment().toString();

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), tailorShopImageURI);
                        Bitmap lastBitmap = null;
                        lastBitmap = bitmap;
                        //encoding image to string
                        String image = getStringImage(lastBitmap);
                        Rimage = image;
                        tailorProfileMain.getTailorData().setShopLogoImage(image);
                        Log.d("image", image);


                        //passing the image to volley

                    } catch (IOException e) {
                        e.printStackTrace();

                    }

                }

                break;
        }
    }
    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }
    public void editTailorProfile(){


        if(profileTName.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter Your Name ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(profileSName.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter Your Name ", Toast.LENGTH_SHORT).show();
            return;
        }


        if(profileTCity.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter Your Name ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(profileTAddress.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Enter Your Name ", Toast.LENGTH_SHORT).show();
            return;
        }
        if(profileTPin.getText().toString().isEmpty()){
            Toast.makeText(this, "Please Select Your Location ", Toast.LENGTH_SHORT).show();
            return;
        }

        tailorProfileMain.getTailorData().setTailorName(profileTName.getText().toString().trim());
        tailorProfileMain.getTailorData().getTailorshopData().setTailorShopName(profileSName.getText().toString().trim());


        tailorBasicInfo=new JSONObject();
        tailorShopAddress=new JSONObject();
        tailorShop=new JSONObject();
        tailorShopLogo=new JSONObject();
        tailorCategories=new JSONArray();

        try {
            tailorBasicInfo.put("TailorID",StaticData.tailorProfile.getTailorID());
            tailorBasicInfo.put("TailorName",tailorProfileMain.getTailorData().getTailorName());
            tailorBasicInfo.put("TailorPhoneNo",tailorProfileMain.getTailorData().getTailorPhoneNo());
            tailorBasicInfo.put("TailorEmail",tailorProfileMain.getTailorData().getTailorEmail());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            tailorShop.put("TailorShopId",tailorProfileMain.getTailorData().getTailorshopData().getTailorShopId());
            tailorShop.put("TailorId",StaticData.tailorProfile.getTailorID());
            tailorShop.put("TailorShopName",tailorProfileMain.getTailorData().getTailorshopData().getTailorShopName());
            tailorShop.put("TailorShopPhoneNo",tailorProfileMain.getTailorData().getTailorPhoneNo());
            tailorShop.put("TailorShopLogo",tailorProfileMain.getTailorData().getShopLogoImage());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            tailorShopAddress.put("ShopAddressId",tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().getShopAddressId());
            tailorShopAddress.put("TailorShopId",tailorProfileMain.getTailorData().getTailorshopData().getTailorShopId());
            tailorShopAddress.put("Address",tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().getAddress());
            tailorShopAddress.put("Xcoordinate",tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().getXcoordinate());
            tailorShopAddress.put("Ycoordinate",tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().getYcoordinate());
            tailorShopAddress.put("ShopPinLocation",tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().getShopPinLocation());
            tailorShopAddress.put("ShopAddressCountryName",tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().getShopAddressCountryName());
            tailorShopAddress.put("ShopAddressCityName",tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().getShopAddressCityName());
        } catch (JSONException e) {
            e.printStackTrace();
        }



        for(CatagoryMain catagoryMain:catagoryMainArrayList){
            JSONObject categories=new JSONObject();
            try {
                if(catagoryMain.getChecked()) {
                    categories.put("CatagoryId", catagoryMain.getCatagoryId());
                    tailorCategories.put(categories);
                }
              /*  categories.put("TailorShopId",tailorProfileMain.getTailorData().getTailorshopData().getTailorShopId());
                categories.put("ShopServiceId",categoryEdit.getShopServiceId());*/
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        try {
            if(tailorProfileMain.getTailorData().getShopLogoImageName()!=null)
            tailorShopLogo.put("ShopLogo",tailorProfileMain.getTailorData().getShopLogoImageName());
            else
                tailorShopLogo.put("ShopLogo","0");

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, StaticData.baseUrl +"TailorPostApi/EditTailorProfile", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                if (response != null) {
                    progressDialog.dismiss();
                    if (response.toString().equalsIgnoreCase("Invalid Data")) {
                        Toast.makeText(EditTailor_Profile_Activity.this, "Something went Wrong try Again", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(EditTailor_Profile_Activity.this, "Profile updated Successfully"+response, Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(EditTailor_Profile_Activity.this,Profile.class);
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
                Toast.makeText(EditTailor_Profile_Activity.this, "Erroe " + error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                Log.e("error is ", "" + error);
            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Tailor", tailorBasicInfo.toString());
                params.put("TailorShop", tailorShop.toString());
                params.put("TailorShopLogo", tailorShopLogo.toString());
                params.put("Catagory", tailorCategories.toString());
                params.put("ShopAddress", tailorShopAddress.toString());


                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(this).addToRequestque(request);

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


                    if(tailorProfileMain.getCatagoryMainArrayList()==null) {
                        Type listType = new TypeToken<List<CatagoryMain>>() {
                        }.getType();

                        catagoryMainArrayList = gson.fromJson(response, listType);

                        for (CategoryProfile categoryProfile : tailorProfileMain.getCategory()) {
                            for (CatagoryMain catagoryMain : catagoryMainArrayList) {
                                if (categoryProfile.getCatagoryId().equals(catagoryMain.getCatagoryId())) {
                                    catagoryMain.setChecked(true);
                                }
                            }
                        }
                        tailorProfileMain.setCatagoryMainArrayList(catagoryMainArrayList);
                    }
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(EditTailor_Profile_Activity.this, "Server Error", Toast.LENGTH_SHORT).show();
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
        SingletonPattren.getmInstance(EditTailor_Profile_Activity.this).addToRequestque(req);

    }

    @Override
    public void CategoryCicked(CatagoryMain catagoryMain, Boolean value) {
        for(CatagoryMain catagoryMain1:tailorProfileMain.getCatagoryMainArrayList()){
            if(catagoryMain.getCatagoryId().equals(catagoryMain1.getCatagoryId())){
                catagoryMain1.setChecked(value);
                for(CategoryProfile categoryProfile:tailorProfileMain.getCategory()){
                    if(catagoryMain.getCatagoryId().equals(categoryProfile.getCatagoryId())){
                        categoryProfile.setChecked(value);
                    }
                }
                rcv_Categories.post(new Runnable() {
                    @Override
                    public void run() {
                        profileCategory.notifyDataSetChanged();
                    }
                });

                categoryRcv.post(new Runnable() {
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