package com.digiconvalley.tailordesk.Activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class AddCustomer extends AppCompatActivity {
    private EditText selectDate,customerName,customerNumber,customerEmail,customerAddress;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Button saveCustomerBtn;
    private ImageButton facePicture,bodyPicture;
    private ImageView faceImge,bodyImage,faceClose,bodyClose;
    private String activeImage,facePictureName,bodyPictureName;
    private Uri bodyPictureUri,facePictureUri,activeUri;
    private JSONObject customerDetailsObj,customerImagesObj,tailorDetails;
    private Spinner genderSpinner;
    private SharedPreferences pref ;
    private SharedPreferences.Editor editor;
    private TailorProfile tailorProfile;
    private ImageButton backBtn;
    private String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        customerName=findViewById(R.id.customerName);
        customerAddress=findViewById(R.id.customerAddress);
        customerEmail=findViewById(R.id.customerEmail);
        customerNumber=findViewById(R.id.customerNumber);
        selectDate=findViewById(R.id.Birthday);
        saveCustomerBtn=findViewById(R.id.finish_btn);

        backBtn=findViewById(R.id.back_button);

        genderSpinner=findViewById(R.id.genderSpinner);

        facePicture=findViewById(R.id.facePicture);
        bodyPicture=findViewById(R.id.bodyPicture);

        faceClose=findViewById(R.id.faceClose);
        faceImge=findViewById(R.id.faceImgView);

        bodyClose=findViewById(R.id.bodyClose);
        bodyImage=findViewById(R.id.bodyImgView);

        if(getIntent()!=null){
            customerNumber.setText(getIntent().getStringExtra("Number"));
            customerName.setText(getIntent().getStringExtra("Name"));
        }

        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        Gson gson=new Gson();
        tailorProfile= gson.fromJson(pref.getString("TailorBasicInfo",null),TailorProfile.class);

        selectDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view == selectDate) {
                    final Calendar c = Calendar.getInstance();
                    mYear = c.get(Calendar.YEAR);
                    mMonth = c.get(Calendar.MONTH);
                    mDay = c.get(Calendar.DAY_OF_MONTH);


                    final DatePickerDialog datePickerDialog = new DatePickerDialog(AddCustomer.this,R.style.DialogTheme,
                            new DatePickerDialog.OnDateSetListener() {

                                @Override
                                public void onDateSet(DatePicker view, int year,
                                                      int monthOfYear, int dayOfMonth) {


                                    selectDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                                }
                            }, mYear, mMonth, mDay);
                    c.add(Calendar.YEAR, 0);
                    datePickerDialog.getDatePicker().setMaxDate(c.getTimeInMillis());
                    datePickerDialog.show();
                }
            }
        });

        faceClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                facePicture.setVisibility(View.VISIBLE);
                faceImge.setVisibility(View.GONE);
                faceClose.setVisibility(View.GONE);
                facePictureUri=null;
                facePictureName=null;

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(AddCustomer.this,MainHome_Activity.class);
                startActivity(intent);
                finish();
            }
        });


        bodyClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bodyPicture.setVisibility(View.VISIBLE);
                bodyImage.setVisibility(View.GONE);
                bodyClose.setVisibility(View.GONE);
                bodyPictureUri=null;
                bodyPictureName=null;
            }
        });

        saveCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        facePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(AddCustomer.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {


                                new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/MikeLau/Pictures", "/sdcard/MikeLau/Pictures");
                                CroperinoFileUtil.verifyStoragePermissions(AddCustomer.this);
                                CroperinoFileUtil.setupDirectory(AddCustomer.this);

                                activeImage="facePicture";
                                activeUri=facePictureUri;
                                Croperino.prepareChooser(AddCustomer.this, "Select Image", ContextCompat.getColor(AddCustomer.this, android.R.color.background_dark));
                       /* Intent intent=new Intent(MainActivity.this,MapTraining.class);
                        startActivity(intent);
                        finish();*/

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {


                                AlertDialog builder = new AlertDialog.Builder(AddCustomer.this)
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

        bodyPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dexter.withContext(AddCustomer.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {


                                new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/MikeLau/Pictures", "/sdcard/MikeLau/Pictures");
                                CroperinoFileUtil.verifyStoragePermissions(AddCustomer.this);
                                CroperinoFileUtil.setupDirectory(AddCustomer.this);

                                activeImage="bodyPicture";
                                activeUri=bodyPictureUri;

                                Croperino.prepareChooser(AddCustomer.this, "Select Image", ContextCompat.getColor(AddCustomer.this, android.R.color.background_dark));

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {


                                AlertDialog builder = new AlertDialog.Builder(AddCustomer.this)
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

        saveCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(customerName.getText().toString().isEmpty()){
                    Toast.makeText(AddCustomer.this, "Kindly Enter Your Customer Name", Toast.LENGTH_SHORT).show();
                    return;
                }


                if(customerNumber.getText().toString().isEmpty()){
                    Toast.makeText(AddCustomer.this, "Kindly Enter Your Customer Number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(customerNumber.getText().toString().trim().length()<11){
                    Toast.makeText(AddCustomer.this, "Kindly Enter Complete Number", Toast.LENGTH_SHORT).show();
                 return;
                }
                if(!Pattern.matches("(03)+[0-6]{2}[0-9]{7}", customerNumber.getText().toString().trim())){
                    Toast.makeText(AddCustomer.this, "Please Enter Right Number Format", Toast.LENGTH_SHORT).show();
                    customerNumber.setError("Please Enter Right Format");
                    return;

                }

                if(!customerEmail.getText().toString().isEmpty()){
                      if(!customerEmail.getText().toString().trim().matches(emailPattern)){
                          Toast.makeText(AddCustomer.this, "Please Enter Right Email Format", Toast.LENGTH_SHORT).show();
                        customerEmail.setError("Please Enter Correct Format");
                        return;
                    }
                }



                if(genderSpinner.getSelectedItemPosition()==0){
                    Toast.makeText(AddCustomer.this, "Kindly select Gender", Toast.LENGTH_SHORT).show();
                    return;
                }

              /*  if(customerAddress.getText().toString().isEmpty()){
                    Toast.makeText(AddCustomer.this, "Kindly Enter Your Customer Address", Toast.LENGTH_SHORT).show();
                    return;
                }*/

              /*  if(facePictureUri==null){
                    Toast.makeText(AddCustomer.this, "Kindly Select Face Picture", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(bodyPictureUri==null){
                    Toast.makeText(AddCustomer.this, "Kindly Select Body Picture", Toast.LENGTH_SHORT).show();
                    return;
                }*/

                customerDetailsObj=new JSONObject();
                customerImagesObj=new JSONObject();
                tailorDetails=new JSONObject();

                try {
                    customerDetailsObj.put("CustomerName",customerName.getText().toString().trim());
                    customerDetailsObj.put("CustomerPhoneNo",customerNumber.getText().toString().trim());
                    customerDetailsObj.put("CustomerBodyPic",bodyPictureName);
                    customerDetailsObj.put("CustomerFacePic",facePictureName);
                    customerDetailsObj.put("CustomerGender",genderSpinner.getSelectedItem().toString());
                    customerDetailsObj.put("CustomerDateOfBirth",selectDate.getText().toString());
                    customerDetailsObj.put("CustomerAddress",customerAddress.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    if(facePictureUri!=null)
                    customerImagesObj.put("FaceImage",facePictureUri.getLastPathSegment());
                    else
                    customerImagesObj.put("FaceImage",0);
                    if(bodyPictureUri!=null)
                    customerImagesObj.put("BodyImage",bodyPictureUri.getLastPathSegment());
                    else
                    customerImagesObj.put("BodyImage",0);
                    customerImagesObj.put("Email",customerEmail.getText().toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                try {
                    tailorDetails.put("TailorShopId",tailorProfile.getTailorShopId());
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                createCustomer();

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


                    if (activeImage.equalsIgnoreCase("facePicture")) {
                        facePicture.setVisibility(View.GONE);
                        faceImge.setVisibility(View.VISIBLE);
                        faceClose.setVisibility(View.VISIBLE);

                        facePictureUri = Uri.fromFile(CroperinoFileUtil.getTempFile());
                        faceImge.setImageURI(facePictureUri);
                        //  Uri i = Uri.fromFile(CroperinoFileUtil.getTempFile());

                       /* logo_text.setText(tailorShopImageURI.getLastPathSegment());
                        tailorPicture = facePictureUri.getLastPathSegment().toString();*/
                  /*  profileImageName = lawyerProfileImageURI.getLastPathSegment().toString();
                    profilePicName.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                    //    ProfileImgUri = i;
                    profilePic.setImageURI(tailorShopImageURI);*/
                        //    Toast.makeText(getContext(), ""+i.getLastPathSegment().trim(), Toast.LENGTH_SHORT).show();
                        // x=x+2;

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), facePictureUri);
                            Bitmap lastBitmap = null;
                            lastBitmap = bitmap;
                            //encoding image to string
                            String image = getStringImage(lastBitmap);
                            facePictureName = image;
                            Log.d("image", image);


                            //passing the image to volley

                        } catch (IOException e) {
                            e.printStackTrace();

                        }

                    }
                    else if(activeImage.equalsIgnoreCase("bodyPicture")){

                        bodyPictureUri = Uri.fromFile(CroperinoFileUtil.getTempFile());

                        bodyPicture.setVisibility(View.GONE);
                        bodyImage.setVisibility(View.VISIBLE);
                        bodyClose.setVisibility(View.VISIBLE);

                        bodyImage.setImageURI(bodyPictureUri);
                        //  Uri i = Uri.fromFile(CroperinoFileUtil.getTempFile());



                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), bodyPictureUri);
                            Bitmap lastBitmap = null;
                            lastBitmap = bitmap;
                            //encoding image to string
                            String image = getStringImage(lastBitmap);
                            bodyPictureName = image;
                            Log.d("image", image);


                            //passing the image to volley

                        } catch (IOException e) {
                            e.printStackTrace();

                        }

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

    public void createCustomer() {


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        //    Toast.makeText(getContext(), "pic ", Toast.LENGTH_SHORT).show();
        StringRequest request = new StringRequest(Request.Method.POST, StaticData.baseUrl +"TailorPostApi/CreateCustomer", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    progressDialog.dismiss();
                    if (response.toString().equalsIgnoreCase("Invalid Data")) {
                        Toast.makeText(AddCustomer.this, "Something went Wrong try Again", Toast.LENGTH_SHORT).show();
                    } else {


                        Toast.makeText(AddCustomer.this, "Customer Added Successfully", Toast.LENGTH_LONG).show();

                        Intent intent=new Intent(AddCustomer.this,CustomerHomeActivity.class);
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
                Toast.makeText(AddCustomer.this, "Erroe " + error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                Log.e("error is ", "" + error);
            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Customer", customerDetailsObj.toString());
                params.put("CustomerImages", customerImagesObj.toString());
                params.put("CustomerRelate", tailorDetails.toString());

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(this).addToRequestque(request);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}