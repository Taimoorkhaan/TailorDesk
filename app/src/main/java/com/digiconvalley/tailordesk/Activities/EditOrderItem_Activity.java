package com.digiconvalley.tailordesk.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digiconvalley.tailordesk.Adapter.CategoryAdapter;
import com.digiconvalley.tailordesk.Adapter.MeasurementAdaptor;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.CatagoryMain;
import com.digiconvalley.tailordesk.Model.CreateOrder;
import com.digiconvalley.tailordesk.Model.Service;
import com.digiconvalley.tailordesk.Model.ServicePart;
import com.digiconvalley.tailordesk.Model.TailorCustomers;
import com.digiconvalley.tailordesk.Model.TailorOrders.OrderSuit;
import com.digiconvalley.tailordesk.Model.TailorOrders.SuitMeasurement;
import com.digiconvalley.tailordesk.Model.TailorOrders.TailorOrders;
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
import com.mikelau.croperino.Croperino;
import com.mikelau.croperino.CroperinoConfig;
import com.mikelau.croperino.CroperinoFileUtil;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EditOrderItem_Activity extends AppCompatActivity implements MeasurementAdaptor.MeasurementFun {

    private Service service;
    private TailorOrders tailorOrders;
    private EditText stitchingPrice;
    private RadioGroup pocketRadioGroup, stitchingRadioGroup;
    private RadioButton alternationRadioBtn, stitchingRadioBtn, crossPocketBtn, straightPocketBtn;

    private Spinner pleatsSpinner, pocketSpinner;
    private EditText stitchingDetails, stitchingCost;
    private ImageView patternImage1, patternImage2, patternImageRemove1, patternImageRemove2, audioImage;
    private ImageButton patternButton1, patternButton2, clothButton1, clothButton2, addmeasurmentBtn, backArrow;
    private ImageView clothImage1, clothImage2, clothImageRemove1, clothImageRemove2, deliveryDateIcon, test;
    private Button saveData;
    private TextView deliveryDateText, toolbarText;

    private String clothImage1Name, clothImage2Name, patternImage1Name, patternImage2Name, activeImage;
    private Uri clothImage1Uri = null, clothImage2Uri = null, patternImage1Uri = null, patternImage2Uri = null, activeUri;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String AudioSavePathInDevice = null;
    private File audiofile = null;
    private MediaRecorder mediaRecorder;

    private String RandomAudioFileName = "TailorDeskRecording";
    public static final int RequestPermissionCode = 1;
    private SwitchCompat markUrgent;
    private TailorCustomers customers;
    private JSONArray orderSuit;
    private JSONArray SuitMeasurement;
    private JSONArray SuitImageArr;
    private CreateOrder createOrderEidt;
    private String orderStatus;
    private int editIndex, orderId;
    private Dialog dialog;
    private LinearLayout measurementLayout;
    private ScrollView createOrderLayout;
    private Boolean currentScreenMode = false;
    private RecyclerView rcv_measurement;
    private MeasurementAdaptor measurementAdaptor;
    private ArrayList<ServicePart> serviceParts;
    private TextView measurementText, serviceNameMeasurement, serviceName;

    private JSONObject orderObj, suitImage, orderObjj;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private TailorProfile tailorProfile;
    private ArrayList<ServicePart> servicePartts = new ArrayList<>();

    private Boolean alternationRadioBtnStatus = false, stitchingRadioBtnStatus = false, crossPocketBtnStatus = false, straightPocketBtnStatus = false;
    private OrderSuit orderItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order_item_);

        Gson gson = new Gson();

        pref = getSharedPreferences("MyPref", 0);
        editor = pref.edit();
        tailorProfile = gson.fromJson(pref.getString("TailorBasicInfo", null), TailorProfile.class);

        serviceName = findViewById(R.id.serviceName);
        measurementText = findViewById(R.id.pant_measurement);

        measurementLayout = findViewById(R.id.MeasurementLayout);
        createOrderLayout = findViewById(R.id.mainLayout);
        rcv_measurement = findViewById(R.id.rcv_Measurement);
        toolbarText = findViewById(R.id.title);
        serviceNameMeasurement = findViewById(R.id.serviceName2);
        measurementText = findViewById(R.id.pant_measurement);
        backArrow=findViewById(R.id.back_button);

        stitchingCost = findViewById(R.id.cost);
        deliveryDateText = findViewById(R.id.delivery_date);
        deliveryDateIcon = findViewById(R.id.deliveryDateIcon);
        saveData = findViewById(R.id.finish_btn);

        stitchingPrice = findViewById(R.id.cost);

        stitchingDetails = findViewById(R.id.stitichingDetails);
        stitchingRadioBtn = findViewById(R.id.newStitching2);
        alternationRadioBtn = findViewById(R.id.AlternationBtn2);


        clothImage1 = findViewById(R.id.clothImage1);
        clothImage2 = findViewById(R.id.clothImage2);

        clothImageRemove1 = findViewById(R.id.clothImage1Remove);
        clothImageRemove2 = findViewById(R.id.clothImage2Remove);

        clothButton1 = findViewById(R.id.clothImage1Button);
        clothButton2 = findViewById(R.id.clothImage2Button);

        patternImage1 = findViewById(R.id.patternImage1);
        patternImage2 = findViewById(R.id.patternImage2);

        patternImageRemove1 = findViewById(R.id.patternImage1Remove);
        patternImageRemove2 = findViewById(R.id.patternImage2Remove);

        patternButton1 = findViewById(R.id.patternImage1button);
        patternButton2 = findViewById(R.id.patternImage2button);

        crossPocketBtn = findViewById(R.id.crossPockectBtn2);
        straightPocketBtn = findViewById(R.id.straightPocketBtn2);

        audioImage = findViewById(R.id.audio_img);
        addmeasurmentBtn = findViewById(R.id.add_btn);

        pocketSpinner = findViewById(R.id.number_of_pocket_spinner);
        pleatsSpinner = findViewById(R.id.select_pleats);

        markUrgent = findViewById(R.id.markUrgent);

        if (getIntent() != null && getIntent().hasExtra("OrderItem")) {

            orderItem = (OrderSuit) getIntent().getSerializableExtra("OrderItem");
            tailorOrders = (TailorOrders) getIntent().getSerializableExtra("TailorOrder");
            setItemData(orderItem);
        }

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!currentScreenMode){
                    onBackPressed();
                }
                else {

                    measurementText.setText(serviceParts.toString());
                    toolbarText.setText("Add Pant Item");
                    createOrderLayout.setVisibility(View.VISIBLE);
                    measurementLayout.setVisibility(View.GONE);
                    currentScreenMode = false;
                }
            }
        });

        deliveryDateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                final DatePickerDialog datePickerDialog = new DatePickerDialog(EditOrderItem_Activity.this, R.style.DialogTheme,
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

                               //deliveryDateText.setText(day + "/" + month + "/" + year);
                               deliveryDateText.setText(year + "-" + month + "-" + day);

                            }
                        }, mYear, mMonth, mDay);
                c.add(Calendar.YEAR, 0);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.show();
            }
        });

        alternationRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (stitchingRadioBtn.isChecked()) {
                    stitchingRadioBtn.setChecked(false);
                    alternationRadioBtn.setChecked(true);
                    alternationRadioBtnStatus = true;
                    stitchingRadioBtnStatus = false;
                } else if (!alternationRadioBtnStatus) {
                    alternationRadioBtn.setChecked(true);
                    alternationRadioBtnStatus = true;
                } else {
                    alternationRadioBtn.setChecked(false);
                    alternationRadioBtnStatus = false;
                }
            }
        });

        stitchingRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (alternationRadioBtn.isChecked()) {
                    stitchingRadioBtn.setChecked(true);
                    alternationRadioBtn.setChecked(false);
                    stitchingRadioBtnStatus = true;
                    alternationRadioBtnStatus = false;
                } else if (!stitchingRadioBtnStatus) {
                    stitchingRadioBtn.setChecked(true);
                    stitchingRadioBtnStatus = true;
                } else {
                    stitchingRadioBtn.setChecked(false);
                    stitchingRadioBtnStatus = false;
                }

            }
        });


        crossPocketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (straightPocketBtn.isChecked()) {
                    straightPocketBtn.setChecked(false);
                    crossPocketBtn.setChecked(true);
                    crossPocketBtnStatus = true;
                    straightPocketBtnStatus = false;
                } else if (!crossPocketBtnStatus) {
                    crossPocketBtn.setChecked(true);
                    crossPocketBtnStatus = true;
                } else {
                    crossPocketBtn.setChecked(false);
                    crossPocketBtnStatus = false;
                }
            }
        });

        straightPocketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (crossPocketBtn.isChecked()) {
                    straightPocketBtn.setChecked(true);
                    crossPocketBtn.setChecked(false);
                    straightPocketBtnStatus = true;
                    crossPocketBtnStatus = false;
                } else if (!straightPocketBtnStatus) {
                    straightPocketBtn.setChecked(true);
                    straightPocketBtnStatus = true;
                } else {
                    straightPocketBtn.setChecked(false);
                    straightPocketBtnStatus = false;
                }

            }
        });

        addmeasurmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toolbarText.setText("Add Measurement");
                currentScreenMode = true;
                createOrderLayout.setVisibility(View.GONE);
                measurementLayout.setVisibility(View.VISIBLE);

                serviceNameMeasurement.setText(orderItem.getOrderSuitName());

           if(measurementAdaptor==null){
               getcatagory();
           }


            }
        });

        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                upDateData(orderItem);
            }
        });


        clothButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(EditOrderItem_Activity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {


                                new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/MikeLau/Pictures", "/sdcard/MikeLau/Pictures");
                                CroperinoFileUtil.verifyStoragePermissions(EditOrderItem_Activity.this);
                                CroperinoFileUtil.setupDirectory(EditOrderItem_Activity.this);


                                activeImage = "cloth1";
                                activeUri = clothImage1Uri;
                                Croperino.prepareChooser(EditOrderItem_Activity.this, "Select Image", ContextCompat.getColor(EditOrderItem_Activity.this, android.R.color.background_dark));


                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {


                                AlertDialog builder = new AlertDialog.Builder(EditOrderItem_Activity.this)
                                        .setMessage("Permisssion Required")
                                        .setNegativeButton("no", null)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent();
                                                intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
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


        clothImageRemove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                orderItem.setOrderSuitPic1(null);
                clothButton1.setVisibility(View.VISIBLE);
                clothImage1.setVisibility(View.GONE);
                clothImageRemove1.setVisibility(View.GONE);
                clothImage1Uri = null;
                clothImage1Name = null;
            }
        });


        clothButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(EditOrderItem_Activity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {


                                new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/MikeLau/Pictures", "/sdcard/MikeLau/Pictures");
                                CroperinoFileUtil.verifyStoragePermissions(EditOrderItem_Activity.this);
                                CroperinoFileUtil.setupDirectory(EditOrderItem_Activity.this);

                                activeImage = "cloth2";
                                activeUri = clothImage2Uri;
                                Croperino.prepareChooser(EditOrderItem_Activity.this, "Select Image", ContextCompat.getColor(EditOrderItem_Activity.this, android.R.color.background_dark));

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {


                                AlertDialog builder = new AlertDialog.Builder(EditOrderItem_Activity.this)
                                        .setMessage("Permisssion Required")
                                        .setNegativeButton("no", null)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent();
                                                intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
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

        clothImageRemove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                orderItem.setOrderSuitpic2(null);
                clothButton2.setVisibility(View.VISIBLE);
                clothImage2.setVisibility(View.GONE);
                clothImageRemove2.setVisibility(View.GONE);
                clothImage2Uri = null;
                clothImage2Name = null;

            }
        });


        patternButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(EditOrderItem_Activity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/MikeLau/Pictures", "/sdcard/MikeLau/Pictures");
                                CroperinoFileUtil.verifyStoragePermissions(EditOrderItem_Activity.this);
                                CroperinoFileUtil.setupDirectory(EditOrderItem_Activity.this);

                                activeImage = "pattern1";
                                activeUri = patternImage1Uri;
                                Croperino.prepareChooser(EditOrderItem_Activity.this, "Select Image", ContextCompat.getColor(EditOrderItem_Activity.this, android.R.color.background_dark));

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {


                                AlertDialog builder = new AlertDialog.Builder(EditOrderItem_Activity.this)
                                        .setMessage("Permisssion Required")
                                        .setNegativeButton("no", null)
                                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent intent = new Intent();
                                                intent.setAction(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
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

        patternImageRemove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderItem.setOrderPatternPic1(null);
                patternButton1.setVisibility(View.VISIBLE);
                patternImageRemove1.setVisibility(View.GONE);
                patternImage1.setVisibility(View.GONE);
                patternImage1Name = null;
                patternImage1Uri = null;

            }
        });


        patternButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(EditOrderItem_Activity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {

                                new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/MikeLau/Pictures", "/sdcard/MikeLau/Pictures");
                                CroperinoFileUtil.verifyStoragePermissions(EditOrderItem_Activity.this);
                                CroperinoFileUtil.setupDirectory(EditOrderItem_Activity.this);

                                activeImage = "pattern2";
                                activeUri = patternImage2Uri;
                                Croperino.prepareChooser(EditOrderItem_Activity.this, "Select Image", ContextCompat.getColor(EditOrderItem_Activity.this, android.R.color.background_dark));
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {


                                AlertDialog builder = new AlertDialog.Builder(EditOrderItem_Activity.this)
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

        patternImageRemove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                orderItem.setOrderPatternPic2(null);
                patternButton2.setVisibility(View.VISIBLE);
                patternImageRemove2.setVisibility(View.GONE);
                patternImage2.setVisibility(View.GONE);
                patternImage2Name = null;
                patternImage2Uri = null;

            }
        });


    }

    public void setItemData(OrderSuit orderItem) {

        serviceName.setText(orderItem.getOrderSuitName());
        stitchingCost.setText(orderItem.getOrderSuitPrice());
        if(!orderItem.getDeliveryDate().equalsIgnoreCase("0001-01-01T00:00:00"))
        deliveryDateText.setText(orderItem.getDeliveryDate().substring(0, 10));
        stitchingDetails.setText(orderItem.getOrderSuitDesc());

        markUrgent.setChecked(orderItem.getUrgentStatus());

        if(orderItem.getSuitMeasurement()!=null)
        measurementText.setText(orderItem.getSuitMeasurement().toString());

        if(orderItem.getIndexOfPleats()!=null)
        pleatsSpinner.setSelection(orderItem.getIndexOfPleats());

        if(orderItem.getIndexOfPocket()!=null)
        pocketSpinner.setSelection(orderItem.getIndexOfPocket());

        if (orderItem.getOrderSuitPic1() != null) {
            clothButton1.setVisibility(View.GONE);
            clothImageRemove1.setVisibility(View.VISIBLE);
            clothImage1.setVisibility(View.VISIBLE);

            Picasso.get().load(StaticData.baseUrlImages + orderItem.getOrderSuitPic1()).fit().centerCrop()
                    .error(R.drawable.ic_solid)
                    .into(clothImage1, new Callback() {
                        @Override
                        public void onSuccess() {


                            // progressBarMain.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            // progressBarMain.setVisibility(View.VISIBLE);
                        }
                    });
        }

        if (orderItem.getOrderSuitpic2() != null) {
            clothButton2.setVisibility(View.GONE);
            clothImageRemove2.setVisibility(View.VISIBLE);
            clothImage2.setVisibility(View.VISIBLE);

            Picasso.get().load(StaticData.baseUrlImages + orderItem.getOrderSuitpic2()).fit().centerCrop()
                    .into(clothImage2, new Callback() {
                        @Override
                        public void onSuccess() {
                            // progressBarMain.setVisibility(View.GONE);
                     /*   clothButton2.setVisibility(View.GONE);
                        clothImageRemove2.setVisibility(View.VISIBLE);
                        clothImage2.setVisibility(View.GONE);*/
                        }

                        @Override
                        public void onError(Exception e) {
                            // progressBarMain.setVisibility(View.VISIBLE);
                        }
                    });
        }

        if (orderItem.getOrderPatternPic1() != null) {
            patternImage1.setVisibility(View.VISIBLE);
            patternImageRemove1.setVisibility(View.VISIBLE);
            patternButton1.setVisibility(View.GONE);

            Picasso.get().load(StaticData.baseUrlImages + orderItem.getOrderPatternPic1()).fit().centerCrop()
                    .into(patternImage1, new Callback() {
                        @Override
                        public void onSuccess() {
                            // progressBarMain.setVisibility(View.GONE);
                      /*  patternImage1.setVisibility(View.VISIBLE);
                        patternImageRemove1.setVisibility(View.VISIBLE);
                        patternButton1.setVisibility(View.GONE);*/
                        }

                        @Override
                        public void onError(Exception e) {
                            // progressBarMain.setVisibility(View.VISIBLE);
                        }
                    });
        }

        if (orderItem.getOrderPatternPic2() != null) {
            patternButton2.setVisibility(View.GONE);
            patternImageRemove2.setVisibility(View.VISIBLE);
            patternImage2.setVisibility(View.VISIBLE);

            Picasso.get().load(StaticData.baseUrlImages + orderItem.getOrderPatternPic2()).fit().centerCrop()
                    .into(patternImage2);
        }

        if (orderItem.getOrderSuitType() != null) {
            if (orderItem.getOrderSuitType().equalsIgnoreCase("New Stitching")) {
                stitchingRadioBtn.setChecked(true);
                stitchingRadioBtnStatus = true;
            }
        }

        if (orderItem.getOrderSuitType() != null) {
            if (orderItem.getOrderSuitType().equalsIgnoreCase("Alternation")) {
                alternationRadioBtn.setChecked(true);
                alternationRadioBtnStatus = true;
            }
        }

        if (orderItem.getPocketType() != null) {
            if (orderItem.getPocketType().equalsIgnoreCase("Cross Pocket")) {
                crossPocketBtn.setChecked(true);
                crossPocketBtnStatus = true;
            }
        }

        if (orderItem.getPocketType() != null) {
            if (orderItem.getPocketType().equalsIgnoreCase("Straight Pocket")) {
                straightPocketBtn.setChecked(true);
                straightPocketBtnStatus = true;
            }
        }


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
                    Croperino.runCropImage(CroperinoFileUtil.getTempFile(), this, true, 1, 1, R.color.gray, R.color.gray_variant);
                }
                break;
            case CroperinoConfig.REQUEST_CROP_PHOTO:
                if (resultCode == Activity.RESULT_OK) {

                    if (activeImage.equalsIgnoreCase("cloth1")) {

                        clothButton1.setVisibility(View.GONE);
                        clothImageRemove1.setVisibility(View.VISIBLE);
                        clothImage1.setVisibility(View.VISIBLE);

                        clothImage1Uri = Uri.fromFile(CroperinoFileUtil.getTempFile());
                        clothImage1.setImageURI(clothImage1Uri);

                        orderItem.setOrderSuitPic1(clothImage1Uri.getLastPathSegment());


                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), clothImage1Uri);
                            Bitmap lastBitmap = null;
                            lastBitmap = bitmap;
                            //encoding image to string
                            String image = getStringImage(lastBitmap);
                            clothImage1Name = image;
                            Log.d("image", image);


                            //passing the image to volley

                        } catch (IOException e) {
                            e.printStackTrace();

                        }

                    } else if (activeImage.equalsIgnoreCase("cloth2")) {

                        clothButton2.setVisibility(View.GONE);
                        clothImageRemove2.setVisibility(View.VISIBLE);
                        clothImage2.setVisibility(View.VISIBLE);
                        clothImage2.setVisibility(View.VISIBLE);

                        clothImage2Uri = Uri.fromFile(CroperinoFileUtil.getTempFile());
                        clothImage2.setImageURI(clothImage2Uri);

                        orderItem.setOrderSuitpic2(clothImage2Uri.getLastPathSegment());

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), clothImage2Uri);
                            Bitmap lastBitmap = null;
                            lastBitmap = bitmap;
                            //encoding image to string
                            String image = getStringImage(lastBitmap);
                            clothImage2Name = image;
                            Log.d("image", image);


                            //passing the image to volley

                        } catch (IOException e) {
                            e.printStackTrace();

                        }

                    } else if (activeImage.equalsIgnoreCase("pattern1")) {

                        patternButton1.setVisibility(View.GONE);
                        patternImage1.setVisibility(View.VISIBLE);
                        patternImageRemove1.setVisibility(View.VISIBLE);

                        patternImage1Uri = Uri.fromFile(CroperinoFileUtil.getTempFile());
                        patternImage1.setImageURI(patternImage1Uri);

                        orderItem.setOrderPatternPic1(patternImage1Uri.getLastPathSegment());
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), patternImage1Uri);
                            Bitmap lastBitmap = null;
                            lastBitmap = bitmap;
                            //encoding image to string
                            String image = getStringImage(lastBitmap);
                            patternImage1Name = image;
                            Log.d("image", image);


                            //passing the image to volley

                        } catch (IOException e) {
                            e.printStackTrace();

                        }

                    } else if (activeImage.equalsIgnoreCase("pattern2")) {


                        patternButton2.setVisibility(View.GONE);
                        patternImage2.setVisibility(View.VISIBLE);
                        patternImageRemove2.setVisibility(View.VISIBLE);

                        patternImage2Uri = Uri.fromFile(CroperinoFileUtil.getTempFile());
                        patternImage2.setImageURI(patternImage2Uri);
                        orderItem.setOrderPatternPic2(patternImage2Uri.getLastPathSegment());

                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), patternImage2Uri);
                            Bitmap lastBitmap = null;
                            lastBitmap = bitmap;
                            //encoding image to string
                            String image = getStringImage(lastBitmap);
                            patternImage2Name = image;
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

    public void upDateData(OrderSuit orderItem) {

        SuitMeasurement = new JSONArray();
        SuitImageArr = new JSONArray();
        orderSuit = new JSONArray();


        orderObj = new JSONObject();
        try {
            //OrderId;

            orderObj.put("CustomerId", orderItem.getCustomerId());
            orderObj.put("ServiceId", orderItem.getServiceId());
            orderObj.put("OrderSuitName", orderItem.getOrderSuitName());
            orderObj.put("OrderId", tailorOrders.getOrderId());
            orderObj.put("OrderSuitId", orderItem.getOrderSuitId());
            orderObj.put("OrderSuitStatus", "Pending");
            ////////////////////////////////////////////////////////////

            if (orderItem.getOrderSuitPic1() != null) {
                if (clothImage1Uri != null) {
                    orderObj.put("OrderSuitPic1", clothImage1Name);
                } else {
                    orderObj.put("OrderSuitPic1", null);
                }
            } else {
                orderObj.put("OrderSuitPic1", "0");
            }

            if (orderItem.getOrderSuitpic2() != null) {
                if (clothImage2Uri != null) {
                    orderObj.put("OrderSuitPic2", clothImage2Name);
                } else {
                    orderObj.put("OrderSuitPic2", null);
                }
            } else {
                orderObj.put("OrderSuitPic2", "0");
            }

            if (orderItem.getOrderPatternPic1() != null) {
                if (patternImage1Uri != null) {
                    orderObj.put("OrderPatternPic1", patternImage1Name);
                } else {
                    orderObj.put("OrderPatternPic1", null);
                }
            } else {
                orderObj.put("OrderPatternPic1", "0");
            }

            if (orderItem.getOrderPatternPic2() != null) {
                if (patternImage2Uri != null) {
                    orderObj.put("OrderPatternPic2", patternImage2Name);
                } else {
                    orderObj.put("OrderPatternPic2", null);
                }
            } else {
                orderObj.put("OrderPatternPic2", "0");
            }

            if (markUrgent.isChecked()) {
                orderObj.put("UrgentStatus", true);

            } else {
                orderObj.put("UrgentStatus", false);
            }


            /////////////////////////////////////////////////////////


            orderObj.put("OrderSuitDesc", stitchingDetails.getText().toString());
            orderObj.put("OrderSuitPrice", stitchingPrice.getText().toString());


            if (pocketSpinner.getSelectedItemPosition() > 0) {

                orderObj.put("NumberOfPocket", pocketSpinner.getSelectedItem());
                orderObj.put("IndexOfPocket", pocketSpinner.getSelectedItemPosition());

            }

            //Changrs Requird variable name.....

            if (pleatsSpinner.getSelectedItemPosition() > 0) {
                orderObj.put("Pleats", pleatsSpinner.getSelectedItem());
                orderObj.put("IndexOfPleats", pleatsSpinner.getSelectedItemPosition());
            }

            if (stitchingRadioBtnStatus) {
                orderObj.put("OrderSuitType", stitchingRadioBtn.getText());
            } else if (alternationRadioBtnStatus) {
                orderObj.put("OrderSuitType", alternationRadioBtn.getText());

            }

            if (crossPocketBtnStatus) {
                orderObj.put("PocketType", crossPocketBtn.getText());
            } else if (straightPocketBtnStatus) {
                orderObj.put("PocketType", straightPocketBtn.getText());
            }


            orderObj.put("Audio", "");


            orderObj.put("ItemNumber", orderItem.getItemNumber());
            //    orderObj.put("OrderSuitType", createOrder.getStitchingMode());

            if (!deliveryDateText.getText().toString().isEmpty())
                orderObj.put("DeliveryDate", deliveryDateText.getText().toString());


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //  orderSuit.put(orderObj);

        if(serviceParts!=null) {

            for (ServicePart servicePart : serviceParts) {
                JSONObject measurementObj = new JSONObject();
                if (servicePart.getMeasurementValue() != null && !servicePart.getMeasurementValue().equalsIgnoreCase("")) {
                    try {
                        if (servicePart.getMeasurementid() != 0)
                            measurementObj.put("SuitMeasurementId", servicePart.getMeasurementid());
                        measurementObj.put("OrderSuitId", orderItem.getOrderSuitId());
                        measurementObj.put("ServicePartId", servicePart.getServicePartId());
                        measurementObj.put("SuitMeasurementValue", servicePart.getMeasurementValue());
                        measurementObj.put("ItemNumber", orderItem.getItemNumber());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    SuitMeasurement.put(measurementObj);
                }
            }
        }

        suitImage = new JSONObject();

        try {
            if (clothImage1Name != null) {

                suitImage.put("ClothPic1Name", clothImage1Uri.getLastPathSegment());
            } else {
                suitImage.put("ClothPic1Name", "0");
            }

            if (clothImage2Name != null) {
                suitImage.put("ClothPic2Name", clothImage2Uri.getLastPathSegment());
            } else {
                suitImage.put("ClothPic2Name", "0");
            }

            if (patternImage1Name != null) {
                suitImage.put("PatternPic1Name", patternImage1Uri.getLastPathSegment());
            } else {
                suitImage.put("PatternPic1Name", "0");
            }

            if (patternImage2Name != null) {
                suitImage.put("PatternPic2Name", patternImage2Uri.getLastPathSegment());
            } else {
                suitImage.put("PatternPic2Name", "0");
            }

            suitImage.put("ItemNumber", orderItem.getItemNumber());
            suitImage.put("OrderSuitStatus", "Pending");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        orderObjj = new JSONObject();
        try {
            String orderDescription = null;
            StringBuilder stringBuilder = new StringBuilder();

///Yeh set krna ha
            int totalprice = (tailorOrders.getTotalPrice() - Integer.parseInt(orderItem.getOrderSuitPrice())) + Integer.parseInt(
                    stitchingPrice.getText().toString());
            orderObjj.put("TotalPrice", (tailorOrders.getTotalPrice() - Integer.parseInt(orderItem.getOrderSuitPrice())) + Integer.parseInt(
                    stitchingPrice.getText().toString()));
            orderObjj.put("RamainingAmount", (totalprice - Integer.parseInt(tailorOrders.getRecievedAmount().toString())) - tailorOrders.getDiscountAmount());
            orderObjj.put("RecievedAmount", tailorOrders.getRecievedAmount());

            orderObjj.put("TailorShopId", tailorProfile.getTailorShopId());
            //orderObjj.put("TailorShopId",1);
            orderObjj.put("OrderId", tailorOrders.getOrderId());
            orderObjj.put("OrderStatus", "Pending");

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

/*
            orderObjj.put("OrderDate",""+day+"/"+month+"/"+mYear);
            orderObjj.put("OrderDeliveryDate",orderItem.getDeliveryDate());
            orderObjj.put("TotalPerson","1");
*/
            orderObjj.put("DiscountAmount", tailorOrders.getDiscountAmount());

        } catch (JSONException e) {
            e.printStackTrace();
        }


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, StaticData.baseUrl + "TailorPostApi/EditOrder", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                if (response != null) {
                    progressDialog.dismiss();

                    if (response.toString().equalsIgnoreCase("Invalid Data")) {
                        Toast.makeText(EditOrderItem_Activity.this, "Something went Wrong try Again", Toast.LENGTH_SHORT).show();
                    } else {
                        StaticData.createOrders.clear();
                        StaticData.customerid = null;
                        StaticData.tailorShopId = null;

                        Toast.makeText(EditOrderItem_Activity.this, "Order Place Successfully ..." + response, Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(EditOrderItem_Activity.this, MainHome_Activity.class);
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
                Toast.makeText(EditOrderItem_Activity.this, "Error " + error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                Log.e("error is ", "" + error);
            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("OrderSuit", orderObj.toString());
                params.put("SuitMeasurement", SuitMeasurement.toString());
                params.put("SuitImage", suitImage.toString());
                params.put("Order", orderObjj.toString());

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(this).addToRequestque(request);

    }

    @Override
    public void measurementValues(ServicePart servicePart, int Position) {

        serviceParts.get(Position).setMeasurementValue(servicePart.getMeasurementValue());
    }

    public void getcatagory() {

        final ProgressDialog progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String url;


        url = StaticData.baseUrl + "TailorGetApi/AllServices";

        StringRequest req = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Gson gson = new Gson();

                    serviceParts=new ArrayList<>();

                    Type listType = new TypeToken<List<CatagoryMain>>() {
                    }.getType();

                  ArrayList<CatagoryMain> catagoryMainArrayList = gson.fromJson(response, listType);

                  for(CatagoryMain catagoryMain:catagoryMainArrayList){
                        for(Service service:catagoryMain.getServices()){

                            if(orderItem.getServiceId().equals(service.getServiceId())){
                                serviceParts.addAll(service.getServicePart());
                                break;
                            }
                        }
                    }


                  if(orderItem.getSuitMeasurement()!=null && orderItem.getSuitMeasurement().size()>0){
                      for(ServicePart servicePart:serviceParts){
                           for(SuitMeasurement suitMeasurement:orderItem.getSuitMeasurement()){
                               if(suitMeasurement.getServicePartId().equals(servicePart.getServicePartId())){
                                   servicePart.setMeasurementValue(suitMeasurement.getSuitMeasurementValue());
                                   servicePart.setMeasurementid(suitMeasurement.getSuitMeasurementId());
                               }
                           }
                      }
                  }


                      rcv_measurement.setLayoutManager(new LinearLayoutManager(EditOrderItem_Activity.this));
                      measurementAdaptor = new MeasurementAdaptor(EditOrderItem_Activity.this, (ArrayList<ServicePart>) serviceParts, EditOrderItem_Activity.this);
                      rcv_measurement.setAdapter(measurementAdaptor);


                    progressDialog.dismiss();
                } else {
                    Toast.makeText(EditOrderItem_Activity.this, "Server Error", Toast.LENGTH_SHORT).show();
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
        SingletonPattren.getmInstance(EditOrderItem_Activity.this).addToRequestque(req);

    }

    @Override
    public void onBackPressed() {
        if(!currentScreenMode){
            super.onBackPressed();
        }
        else {

           // measurementText.setText(serviceParts.toString());
            measurementText.setText(showMeasurementData(serviceParts));
            //showMeasurementData(serviceParts);
            toolbarText.setText("Add Pant Item");
            createOrderLayout.setVisibility(View.VISIBLE);
            measurementLayout.setVisibility(View.GONE);
            currentScreenMode = false;
        }
    }

    public String showMeasurementData(ArrayList<ServicePart> serviceParts) {
        StringBuilder stringBuilder = new StringBuilder();
        String measurements = null;
        if (serviceParts.size() > 1) {
            for (ServicePart servicePart : serviceParts) {
                if (servicePart.getMeasurementValue() != null) {
                    measurements = String.valueOf(stringBuilder.append(servicePart.getServicePartName()));
                    measurements = String.valueOf(stringBuilder.append(" = " + servicePart.getMeasurementValue()));
                    measurements = String.valueOf(stringBuilder.append(" ,"));
                }
            }

        }
        return measurements.substring(0,measurements.length()-1);
     /*   else{
            if(serviceParts.get(0).getMeasurementValue()!=null)
            measurements = String.valueOf(stringBuilder.append(serviceParts.get(0).getServicePartName()));
            measurements = String.valueOf(stringBuilder.append(" = " + serviceParts.get(0).getMeasurementValue()));
         return measurements;
        }*/

    }
}