package com.digiconvalley.tailordesk.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digiconvalley.tailordesk.Maps.TailorLocation_Activity;
import com.digiconvalley.tailordesk.Model.TailorBasicProfile;
import com.digiconvalley.tailordesk.Model.TailorProfile;
import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.Singleton.SingletonPattren;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.google.gson.Gson;
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
import java.util.HashMap;
import java.util.Map;

public class SetupDetail03 extends AppCompatActivity {
    private ImageButton back_button;
    private ImageButton map_btn;
    //FusedLocationProviderClient fusedLocationProviderClient;
    private TextView map_text, Country;
    private TextView logo_text;
    private ImageButton logo_btn;
    private static final int PICK_IMAGE = 100;
    private Uri imageUri;
    private EditText Address;

    private Button finish_btn;
    private TailorBasicProfile tailorBasicProfile;
    private String Rimage,tailorPicture;

    private Uri tailorShopImageURI;
    private SharedPreferences pref ;
    private SharedPreferences.Editor editor;
    private TailorProfile tailorProfile;


    private JSONObject tailor,tailorShop,tailorShopLogo,shopAddress;
    private JSONArray ShopServices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_detail03);


        back_button = findViewById(R.id.back_button);
        map_btn = findViewById(R.id.map_btn);
        map_text = findViewById(R.id.map_text);
        logo_btn=findViewById(R.id.logo_btn);
        logo_text=findViewById(R.id.logo_text);
        Country=findViewById(R.id.country);

        Address=findViewById(R.id.area);
        finish_btn=findViewById(R.id.finish_btn);



        if(getIntent()!=null){
            tailorBasicProfile= (TailorBasicProfile) getIntent().getSerializableExtra("tailorBasicInfo");
            if(tailorBasicProfile.getAddress()!=null){
                map_text.setText(tailorBasicProfile.getAddress());
            }
            if(tailorBasicProfile.getAddressNear()!=null){
                Address.setText(tailorBasicProfile.getAddressNear());
            }
            if(tailorBasicProfile.getTailorShopLogoName()!=null && tailorBasicProfile.getTailorShopLogoUri()!=null){
                logo_text.setText(tailorBasicProfile.getTailorShopLogoUri());
                Rimage=tailorBasicProfile.getTailorShopLogoName();
            }
            if(tailorBasicProfile.getCountryName()!=null)
                Country.setText(tailorBasicProfile.getCountryName());


        }

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();


        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(SetupDetail03.this, TailorLocation_Activity.class);
                if(!Country.getText().toString().isEmpty())
                 tailorBasicProfile.setCountryName(Country.getText().toString());

                tailorBasicProfile.setCityName("Lahore");
                if(!Address.getText().toString().isEmpty())
                    tailorBasicProfile.setAddressNear(Address.getText().toString());

                if(tailorShopImageURI!=null) {
                    tailorBasicProfile.setTailorShopLogoUri(tailorShopImageURI.getLastPathSegment());
                    tailorBasicProfile.setTailorShopLogoName(Rimage);
                }
                intent.putExtra("tailorBasicInfo",tailorBasicProfile);
                startActivity(intent);
            }
        });
        finish_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Country.getText().toString().equals("")){
                    Country.setError("Please Enter Your Country Name");
                    Toast.makeText(SetupDetail03.this, "Kindly Enter Country Name", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if( Address.getText().toString().equals("")){
                    Address.setError("Please Enter Address!");
                    Toast.makeText(SetupDetail03.this, "Kindly select your Location", Toast.LENGTH_SHORT).show();
                    return;
                }

                else if(tailorBasicProfile.getLatitude()==null&&tailorBasicProfile.getLongtite()==null){
                    Toast.makeText(SetupDetail03.this, "Kindly select your Location", Toast.LENGTH_SHORT).show();
                return;
                }
            /*    else if(Rimage==null){
                    Toast.makeText(SetupDetail03.this, "Kindly select your ShopLogo", Toast.LENGTH_SHORT).show();
                 return;
                }*/


                else{
                    tailor=new JSONObject();
                    try {
                        tailor.put("TailorID",tailorBasicProfile.getTailorID());
                        tailor.put("TailorName",tailorBasicProfile.getPersonName());
                        tailor.put("TailorEmail",tailorBasicProfile.getTailorEmail());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    tailorShop=new JSONObject();

                    try {
                        tailorShop.put("TailorId",tailorBasicProfile.getTailorID());
                        tailorShop.put("TailorShopName",tailorBasicProfile.getShopName());
                        tailorShop.put("TailorShopPhoneNo",tailorBasicProfile.getTailorPhoneNo());
                        tailorShop.put("TailorShopLogo",Rimage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    tailorShopLogo=new JSONObject();

                    try {
                        if(tailorPicture!=null)
                        tailorShopLogo.put("ShopLogo",tailorPicture);
                        else
                            tailorShopLogo.put("ShopLogo",0);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    ShopServices=new JSONArray();
                    for(Integer integer:tailorBasicProfile.getIntegerArrayList()){
                        JSONObject jsonObject=new JSONObject();
                        try {
                            jsonObject.put("CatagoryId",integer);
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }
                        ShopServices.put(jsonObject);
                    }

                    shopAddress=new JSONObject();
                    try {
                        shopAddress.put("Address",tailorBasicProfile.getAddressNear());
                        shopAddress.put("ShopAddressCountryName",Country.getText().toString().trim());
                        shopAddress.put("ShopAddressCityName","Lahore");
                        shopAddress.put("ShopPinLocation",tailorBasicProfile.getAddress().trim());
                        shopAddress.put("Xcoordinate",tailorBasicProfile.getLatitude());
                        shopAddress.put("Ycoordinate",tailorBasicProfile.getLongtite());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    cearte();

                }
            }
        });
        logo_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withContext(SetupDetail03.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {


                                new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/MikeLau/Pictures", "/sdcard/MikeLau/Pictures");
                                CroperinoFileUtil.verifyStoragePermissions(SetupDetail03.this);
                                CroperinoFileUtil.setupDirectory(SetupDetail03.this);

                                Croperino.prepareChooser(SetupDetail03.this, "Select Image", ContextCompat.getColor(SetupDetail03.this, android.R.color.background_dark));
                       /* Intent intent=new Intent(MainActivity.this,MapTraining.class);
                        startActivity(intent);
                        finish();*/

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {


                                AlertDialog builder = new AlertDialog.Builder(SetupDetail03.this)
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
        });

      /*  fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        map_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(SetupDetail03.this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    ActivityCompat.requestPermissions(SetupDetail03.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
                }
            }
        });*/


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                finish();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    // Croperino.runCropImage(CroperinoFileUtil.getTempFile(), getActivity(), true, 1, 1, R.color.gray, R.color.gray_variant);
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), this, true, 1, 1, R.color.gray, R.color.gray_variant);
                }
                break;
            case CroperinoConfig.REQUEST_CROP_PHOTO:
                if (resultCode == Activity.RESULT_OK) {


                    tailorShopImageURI = Uri.fromFile(CroperinoFileUtil.getTempFile());
                    //  Uri i = Uri.fromFile(CroperinoFileUtil.getTempFile());

                    logo_text.setText(tailorShopImageURI.getLastPathSegment());
                    tailorPicture = tailorShopImageURI.getLastPathSegment().toString();
                  /*  profileImageName = lawyerProfileImageURI.getLastPathSegment().toString();
                    profilePicName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    //    ProfileImgUri = i;
                    profilePic.setImageURI(tailorShopImageURI);*/
                    //    Toast.makeText(getContext(), ""+i.getLastPathSegment().trim(), Toast.LENGTH_SHORT).show();
                    // x=x+2;

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), tailorShopImageURI);
                        Bitmap lastBitmap = null;
                        lastBitmap = bitmap;
                        //encoding image to string
                        String image = getStringImage(lastBitmap);
                        Rimage = image;
                        Log.d("image", image);


                        //passing the image to volley

                    } catch (IOException e) {
                        e.printStackTrace();

                    }

                }

                break;
        }
    }

    public void cearte() {


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, StaticData.baseUrl +"TailorPostApi/TailorShopCreate", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                if (response != null) {
                    progressDialog.dismiss();
                    if (response.toString().equalsIgnoreCase("Invalid Data")) {
                        Toast.makeText(SetupDetail03.this, "Something went Wrong try Again", Toast.LENGTH_SHORT).show();
                    } else {

                        tailorProfile= gson.fromJson(pref.getString("TailorBasicInfo",null), TailorProfile.class);
                         tailorProfile.setTailorShopId(response);
                        String tailorinfo = gson.toJson(tailorProfile);


                        editor.putString("TailorBasicInfo",tailorinfo);

                        editor.putString("Signup", "yes");
                        editor.commit();
                        editor.apply();
                        Log.e("Your Array Response", response);
                        Toast.makeText(SetupDetail03.this, "User Create Successfully", Toast.LENGTH_LONG).show();

                        Intent intent=new Intent(SetupDetail03.this,MainHome_Activity.class);
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
                Toast.makeText(SetupDetail03.this, "Erroe " + error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                Log.e("error is ", "" + error);
            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Tailor", tailor.toString());
                params.put("TailorShop", tailorShop.toString());
                params.put("TailorShopLogo", tailorShopLogo.toString());
                params.put("ShopServices", ShopServices.toString());
                params.put("ShopAddress", shopAddress.toString());


                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(this).addToRequestque(request);

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 70, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;

    }

}
