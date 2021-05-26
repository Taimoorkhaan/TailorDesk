package com.digiconvalley.tailordesk.Activities;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digiconvalley.tailordesk.Adapter.MeasurementAdaptor;
import com.digiconvalley.tailordesk.Model.CreateOrder;
import com.digiconvalley.tailordesk.Model.Service;
import com.digiconvalley.tailordesk.Model.ServicePart;
import com.digiconvalley.tailordesk.Model.TailorCustomers;
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
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class CreateOrder_Activity extends AppCompatActivity implements MeasurementAdaptor.MeasurementFun {
    private Service service;
    private EditText serviceName,stitchingPrice;
    private RadioGroup pocketRadioGroup,stitchingRadioGroup;
    private RadioButton alternationRadioBtn,stitchingRadioBtn,crossPocketBtn,straightPocketBtn;

    private Spinner pleatsSpinner,pocketSpinner,collarType;
    private EditText stitchingDetails,stitchingCost;
    private ImageView patternImage1,patternImage2,patternImageRemove1,patternImageRemove2,audioImage;
    private ImageButton patternButton1,patternButton2,clothButton1,clothButton2,addmeasurmentBtn,backArrow;
    private ImageView clothImage1,clothImage2,clothImageRemove1,clothImageRemove2,deliveryDateIcon,test;
    private Button saveData;
    private TextView deliveryDateText,toolbarText;

    private String clothImage1Name,clothImage2Name,patternImage1Name,patternImage2Name,activeImage;
    private Uri clothImage1Uri,clothImage2Uri,patternImage1Uri,patternImage2Uri,activeUri;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String AudioSavePathInDevice = null;
    private File audiofile = null;
    private MediaRecorder mediaRecorder ;
    private Random random ;
    private String RandomAudioFileName = "TailorDeskRecording";
    public static final int RequestPermissionCode = 1;
    private SwitchCompat markUrgent;
    private TailorCustomers customers;
    private JSONArray orderSuit;
    private JSONArray SuitMeasurement;
    private JSONArray SuitImageArr;
    private JSONObject orderObjj;
    private CreateOrder createOrderEidt;
    private String orderStatus;
    private int editIndex;
    private Dialog dialog;
    private LinearLayout measurementLayout;
    private ScrollView createOrderLayout;
    private Boolean currentScreenMode=false;
    private RecyclerView rcv_measurement;
    private MeasurementAdaptor measurementAdaptor;
    private ArrayList<ServicePart> serviceParts;
    private TextView measurementText,serviceNameMeasurement;
    private File dir;
    private Boolean alternationRadioBtnStatus=false,stitchingRadioBtnStatus=false,crossPocketBtnStatus=false,straightPocketBtnStatus=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order_);

        //hideKeyboard(this);

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );
        dialog=new Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        serviceName=findViewById(R.id.serviceName);
        stitchingCost=findViewById(R.id.cost);
        deliveryDateText=findViewById(R.id.delivery_date);
        deliveryDateIcon=findViewById(R.id.deliveryDateIcon);
        saveData=findViewById(R.id.finish_btn);

        stitchingDetails=findViewById(R.id.stitichingDetails);
        stitchingRadioBtn=findViewById(R.id.newStitching2);
        alternationRadioBtn=findViewById(R.id.AlternationBtn2);

        serviceNameMeasurement=findViewById(R.id.serviceName2);


        crossPocketBtn=findViewById(R.id.crossPockectBtn2);
        straightPocketBtn=findViewById(R.id.straightPocketBtn2);

        pocketSpinner=findViewById(R.id.number_of_pocket_spinner);
        pleatsSpinner=findViewById(R.id.select_pleats);

        stitchingRadioGroup=findViewById(R.id.stitichingMode);
      /*  pocketRadioGroup=findViewById(R.id.pocketMode);*/

        clothImage1=findViewById(R.id.clothImage1);
        clothImage2=findViewById(R.id.clothImage2);

        clothImageRemove1=findViewById(R.id.clothImage1Remove);
        clothImageRemove2=findViewById(R.id.clothImage2Remove);

        clothButton1=findViewById(R.id.clothImage1Button);
        clothButton2=findViewById(R.id.clothImage2Button);

        patternImage1=findViewById(R.id.patternImage1);
        patternImage2=findViewById(R.id.patternImage2);

        patternImageRemove1=findViewById(R.id.patternImage1Remove);
        patternImageRemove2=findViewById(R.id.patternImage2Remove);

        patternButton1=findViewById(R.id.patternImage1button);
        patternButton2=findViewById(R.id.patternImage2button);

        audioImage=findViewById(R.id.audio_img);
        addmeasurmentBtn=findViewById(R.id.add_btn);

        markUrgent=findViewById(R.id.markUrgent);
        measurementLayout=findViewById(R.id.MeasurementLayout);
        createOrderLayout=findViewById(R.id.mainLayout);

        backArrow=findViewById(R.id.back_button);
        rcv_measurement=findViewById(R.id.rcv_Measurement);
        toolbarText=findViewById(R.id.title);
        measurementText=findViewById(R.id.pant_measurement);
        collarType=findViewById(R.id.number_of_collar_spinner);



        if(getIntent()!=null && getIntent().hasExtra("SelectedService")){
          service= (Service) getIntent().getSerializableExtra("SelectedService");
          customers= (TailorCustomers) getIntent().getSerializableExtra("Customer");
            serviceName.setText(service.getServiceName());
            toolbarText.setText("Add "  +service.getServiceName()+" Item");
            orderStatus="NoEdit";
        }

        if(getIntent()!=null && getIntent().hasExtra("EditOrder")){

            editIndex=getIntent().getIntExtra("Position",0);

            createOrderEidt=StaticData.createOrders.get(editIndex);

            toolbarText.setText("Add "  +createOrderEidt.getServiceName()+" Item");
            serviceName.setText(createOrderEidt.getServiceName());
            stitchingCost.setText(createOrderEidt.getStitchingPrice());
           /* saveData.setText("Edit Order");*/

            deliveryDateText.setText(createOrderEidt.getDevlieryDate());
            stitchingDetails.setText(createOrderEidt.getSpecialNote());
            pleatsSpinner.setSelection(createOrderEidt.getNoPleatsIndex());
            pocketSpinner.setSelection(createOrderEidt.getNoPocketIndex());
            collarType.setSelection(createOrderEidt.getCollarIndex());


            if(createOrderEidt.getAlternationStatus()){
                alternationRadioBtn.setChecked(true);
                 alternationRadioBtnStatus=true;
            }

            if(createOrderEidt.getStraightPocketsStatus()){
                straightPocketBtn.setChecked(true);
                straightPocketBtnStatus=true;
            }

            if(createOrderEidt.getNewStitchingStatus()){
                stitchingRadioBtn.setChecked(true);
                stitchingRadioBtnStatus=true;
            }

            if(createOrderEidt.getCrossPocketStatus()){
                crossPocketBtn.setChecked(true);
                crossPocketBtnStatus=true;
            }


            if(createOrderEidt.getPattern01Uri()!=null){
                patternImage1Uri=Uri.parse(createOrderEidt.getPattern01Uri());
                patternImageRemove1.setVisibility(View.VISIBLE);
                patternImage1.setVisibility(View.VISIBLE);
                patternButton1.setVisibility(View.GONE);

                patternImage1Name=createOrderEidt.getPattern01Name();
                patternImage1.setImageURI(patternImage1Uri);
            }

            if(createOrderEidt.getPattern02Uri()!=null){
                patternImage2Uri=Uri.parse(createOrderEidt.getPattern02Uri());
                patternImageRemove2.setVisibility(View.VISIBLE);
                patternImage2.setVisibility(View.VISIBLE);
                patternButton2.setVisibility(View.GONE);
                patternImage2Name=createOrderEidt.getPattern02Name();

                patternImage2.setImageURI(patternImage2Uri);
            }

            if(createOrderEidt.getClothImageUri1()!=null){
                clothImage1Uri=Uri.parse(createOrderEidt.getClothImageUri1());
                clothImageRemove1.setVisibility(View.VISIBLE);
                clothImage1.setVisibility(View.VISIBLE);
                clothButton1.setVisibility(View.GONE);

                clothImage1Name=createOrderEidt.getCloth01Name();
                clothImage1.setImageURI(clothImage1Uri);
            }

            if(createOrderEidt.getClothImageUri2()!=null){
                clothImage2Uri=Uri.parse(createOrderEidt.getClothImageUri2());
                clothImageRemove2.setVisibility(View.VISIBLE);
                clothImage2.setVisibility(View.VISIBLE);
                clothButton2.setVisibility(View.GONE);
                clothImage2Name=createOrderEidt.getCloth02Name();

                clothImage2.setImageURI(clothImage2Uri);
            }


            String values="";
            for(ServicePart servicePart1:createOrderEidt.getNoMeasurement()){
                if(servicePart1.getMeasurementValue()!=null){
                    values=values+servicePart1.getServicePartName()+" : "+servicePart1.getMeasurementValue()+" ";
                }

            }

            if(!values.equalsIgnoreCase(""))
                measurementText.setText(values);

            orderStatus="Edit";

        }

        random = new Random();

//        serviceName.setEnabled(false);


        alternationRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(stitchingRadioBtn.isChecked()){
                    stitchingRadioBtn.setChecked(false);
                    alternationRadioBtn.setChecked(true);
                    alternationRadioBtnStatus=true;
                    stitchingRadioBtnStatus=false;
                }

                else if(!alternationRadioBtnStatus){
                    alternationRadioBtn.setChecked(true);
                    alternationRadioBtnStatus=true;
                }
                else
                {
                    alternationRadioBtn.setChecked(false);
                    alternationRadioBtnStatus=false;
                }
            }
        });

        stitchingRadioBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                if(alternationRadioBtn.isChecked()){
                    stitchingRadioBtn.setChecked(true);
                    alternationRadioBtn.setChecked(false);
                    stitchingRadioBtnStatus=true;
                    alternationRadioBtnStatus=false;
                }

                else if(!stitchingRadioBtnStatus){
                    stitchingRadioBtn.setChecked(true);
                    stitchingRadioBtnStatus=true;
                }
                else
                {
                    stitchingRadioBtn.setChecked(false);
                    stitchingRadioBtnStatus=false;
                }

            }
        });


        crossPocketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(straightPocketBtn.isChecked()){
                    straightPocketBtn.setChecked(false);
                    crossPocketBtn.setChecked(true);
                    crossPocketBtnStatus=true;
                    straightPocketBtnStatus=false;
                }

                else if(!crossPocketBtnStatus){
                    crossPocketBtn.setChecked(true);
                    crossPocketBtnStatus=true;
                }
                else
                {
                    crossPocketBtn.setChecked(false);
                    crossPocketBtnStatus=false;
                }
            }
        });

        straightPocketBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(crossPocketBtn.isChecked()){
                    straightPocketBtn.setChecked(true);
                    crossPocketBtn.setChecked(false);
                    straightPocketBtnStatus=true;
                    crossPocketBtnStatus=false;
                }

                else if(!straightPocketBtnStatus){
                    straightPocketBtn.setChecked(true);
                    straightPocketBtnStatus=true;
                }
                else
                {
                    straightPocketBtn.setChecked(false);
                    straightPocketBtnStatus=false;
                }

            }
        });

        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(currentScreenMode){

                    toolbarText.setText("Add "  +" Item");
                    createOrderLayout.setVisibility(View.VISIBLE);
                    measurementLayout.setVisibility(View.GONE);
                    currentScreenMode=false;
                    String values="";

                    if (orderStatus.equalsIgnoreCase("Edit")) {

                        for (ServicePart servicePart1 :createOrderEidt.getNoMeasurement()) {
                            if (servicePart1.getMeasurementValue()!=null) {
                                values = values + servicePart1.getServicePartName() + " : " + servicePart1.getMeasurementValue() + " ";
                            }

                        }
                    }
                    else{
                        for (ServicePart servicePart1 : service.getServicePart()) {
                            if (servicePart1.getMeasurementValue()!=null) {
                                values = values + servicePart1.getServicePartName() + " : " + servicePart1.getMeasurementValue() + " ";
                            }
                        }
                    }

                    if(!values.equalsIgnoreCase(""))
                    measurementText.setText(values);
                }
                else{
                    onBackPressed();
                    //Toast.makeText(CreateOrder_Activity.this, "End Screens", Toast.LENGTH_SHORT).show();
                }
            }
        });


        audioImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               show_Dialog();

            }
        });


        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(serviceName.getText().toString().isEmpty()){
                    Toast.makeText(CreateOrder_Activity.this, "Kindly Enter Service Name ", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(stitchingCost.getText().toString().isEmpty()){
                    Toast.makeText(CreateOrder_Activity.this, "Kindly Enter Stitching Price", Toast.LENGTH_SHORT).show();
                    return;
                }

              /*  if(deliveryDateText.getText().toString().isEmpty()){
                    Toast.makeText(CreateOrder_Activity.this, "Kindly Enter Delivery Date ", Toast.LENGTH_SHORT).show();
                    return;
                }*/

               /* if(stitchingDetails.getText().toString().isEmpty()){
                    Toast.makeText(CreateOrder_Activity.this, "Kindly Enter Special Note", Toast.LENGTH_SHORT).show();
                    return;
                }*/




                if (orderStatus.equalsIgnoreCase("NoEdit")) {



                    CreateOrder createOrder = new CreateOrder();
                    createOrder.setServiceImage(service.getServicePicture());

                    createOrder.setStitchingMode("");
                    createOrder.setPocketMode("");

                    createOrder.setAlternationStatus(false);
                    createOrder.setCrossPocketStatus(false);
                    createOrder.setNewStitchingStatus(false);
                    createOrder.setStraightPocketsStatus(false);


                    createOrder.setServiceName(serviceName.getText().toString().trim());
                    createOrder.setStitchingPrice(stitchingCost.getText().toString());
                    createOrder.setDevlieryDate(deliveryDateText.getText().toString());

                    if(pocketSpinner.getSelectedItemPosition()>0) {
                        createOrder.setNoOfPocket(pocketSpinner.getSelectedItem().toString());
                        createOrder.setNoPocketIndex(pocketSpinner.getSelectedItemPosition());
                    }

                    if(pleatsSpinner.getSelectedItemPosition()>0) {
                        createOrder.setNoOfPleats(pleatsSpinner.getSelectedItem().toString());
                        createOrder.setNoPleatsIndex(pleatsSpinner.getSelectedItemPosition());
                    }

                    if(collarType.getSelectedItemPosition()>0){
                        createOrder.setCollarType(collarType.getSelectedItem().toString());
                        createOrder.setCollarIndex(collarType.getSelectedItemPosition());
                    }
                    //createOrder.setNoOfPleats("1");
                    createOrder.setSpecialNote(stitchingDetails.getText().toString());

                    if (clothImage1Uri != null) {
                        createOrder.setCloth01ImageName(clothImage1Name);
                        createOrder.setCloth01Name(clothImage1Uri.getLastPathSegment());
                        createOrder.setClothImageUri1(clothImage1Uri.toString());
                    }

                    if (clothImage2Uri != null) {
                        createOrder.setCloth02ImageName(clothImage2Name);
                        createOrder.setCloth02Name(clothImage2Uri.getLastPathSegment());
                        createOrder.setClothImageUri2(clothImage2Uri.toString());
                    }

                    if (patternImage1Uri != null) {
                        createOrder.setPattern01ImageName(patternImage1Name);
                        createOrder.setPattern01Name(patternImage1Uri.getLastPathSegment());
                        createOrder.setPattern01Uri(patternImage1Uri.toString());
                    }

                    if (patternImage2Uri != null) {
                        createOrder.setPattern02ImageName(patternImage2Name);
                        createOrder.setPattern02Name(patternImage2Uri.getLastPathSegment());
                        createOrder.setPattern02Uri(patternImage2Uri.toString());
                    }

                    if (markUrgent.isChecked()) {
                        createOrder.setUrgentNeed(true);
                    } else {
                        createOrder.setUrgentNeed(false);
                    }

                   if(stitchingRadioBtnStatus){
                       createOrder.setStitchingMode("New Stitching");
                       createOrder.setNewStitchingStatus(true);
                   }
                   else if(alternationRadioBtnStatus) {
                       createOrder.setStitchingMode("Alternation");
                       createOrder.setAlternationStatus(true);
                   }

                   if(crossPocketBtnStatus){
                       createOrder.setPocketMode("Cross Pocket");
                       createOrder.setCrossPocketStatus(true);
                   }
                   else if(straightPocketBtnStatus){
                       createOrder.setPocketMode("Straight Pocket");
                       createOrder.setStraightPocketsStatus(true);
                   }


                    if (!orderStatus.equalsIgnoreCase("Edit")) {
                        createOrder.setCustomerId("" + StaticData.customerid);
                        createOrder.setServiceId("" + service.getServiceId());
                    }
                    int max = 50000;
                    int min = 100;

                    if (StaticData.n == 0) {
                        Random rand = new Random();
                        StaticData.n = (int) (Math.random() * max + min);
                        StaticData.orderNo =(int) (Math.random() * max + min);
                    } else {
                        StaticData.n++;
                    }

                    createOrder.setItemNo("Item# " + StaticData.n);
                    createOrder.setNoMeasurement((ArrayList<ServicePart>) service.getServicePart());
                    StaticData.createOrders.add(createOrder);
                    Intent intent = new Intent(CreateOrder_Activity.this, OrderCart_Activity.class);
                    // intent.putExtra("OrderBasket",StaticData.createOrders);
                    startActivity(intent);
                   finish();
                    // cearte(createOrder);
                }
                else if (orderStatus.equalsIgnoreCase("Edit")) {

                    createOrderEidt.setStitchingMode("");
                    createOrderEidt.setPocketMode("");

                    createOrderEidt.setAlternationStatus(false);
                    createOrderEidt.setCrossPocketStatus(false);
                    createOrderEidt.setNewStitchingStatus(false);
                    createOrderEidt.setStraightPocketsStatus(false);


                    createOrderEidt.setServiceName(serviceName.getText().toString());
                    createOrderEidt.setStitchingPrice(stitchingCost.getText().toString());
                    createOrderEidt.setDevlieryDate(deliveryDateText.getText().toString());

                    //Check this what selected value

                    createOrderEidt.setNoOfPocket(pocketSpinner.getSelectedItem().toString());
                    createOrderEidt.setNoPocketIndex(pocketSpinner.getSelectedItemPosition());

                    //createOrder.setNoOfPocket("2");
                    createOrderEidt.setNoOfPleats(pleatsSpinner.getSelectedItem().toString());
                    createOrderEidt.setNoPleatsIndex(pleatsSpinner.getSelectedItemPosition());


                    createOrderEidt.setCollarType(collarType.getSelectedItem().toString());
                    createOrderEidt.setCollarIndex(collarType.getSelectedItemPosition());



                    createOrderEidt.setSpecialNote(stitchingDetails.getText().toString());

                    if(stitchingRadioBtnStatus){
                        createOrderEidt.setStitchingMode("New Stitching");
                        createOrderEidt.setNewStitchingStatus(true);
                    }
                    else if(alternationRadioBtnStatus) {
                        createOrderEidt.setStitchingMode("Alternation");
                        createOrderEidt.setAlternationStatus(true);
                    }

                    if(crossPocketBtnStatus){
                        createOrderEidt.setPocketMode("Cross Pocket");
                        createOrderEidt.setCrossPocketStatus(true);
                    }
                    else if(straightPocketBtnStatus){
                        createOrderEidt.setPocketMode("Straight Pocket");
                        createOrderEidt.setStraightPocketsStatus(true);
                    }


                    if (clothImage1Uri != null) {
                        createOrderEidt.setCloth01ImageName(clothImage1Name);
                        createOrderEidt.setCloth01Name(clothImage1Uri.getLastPathSegment());
                        createOrderEidt.setClothImageUri1(clothImage1Uri.toString());
                    }
                    else{
                        createOrderEidt.setCloth01ImageName(null);
                        createOrderEidt.setCloth01Name(null);
                        createOrderEidt.setClothImageUri1(null);
                    }

                    if (clothImage2Uri != null) {
                        createOrderEidt.setCloth02ImageName(clothImage2Name);
                        createOrderEidt.setCloth02Name(clothImage2Uri.getLastPathSegment());
                        createOrderEidt.setClothImageUri2(clothImage2Uri.toString());
                    }
                    else{
                        createOrderEidt.setCloth02ImageName(null);
                        createOrderEidt.setCloth02Name(null);
                        createOrderEidt.setClothImageUri2(null);
                    }

                    if (patternImage1Uri != null) {
                        createOrderEidt.setPattern01ImageName(patternImage1Name);
                        createOrderEidt.setPattern01Name(patternImage1Uri.getLastPathSegment());
                        createOrderEidt.setPattern01Uri(patternImage1Uri.toString());
                    }
                    else{
                        createOrderEidt.setPattern01ImageName(null);
                        createOrderEidt.setPattern01Name(null);
                        createOrderEidt.setPattern01Uri(null);
                    }

                    if (patternImage2Uri != null) {
                        createOrderEidt.setPattern02ImageName(patternImage2Name);
                        createOrderEidt.setPattern02Name(patternImage2Uri.getLastPathSegment());
                        createOrderEidt.setPattern02Uri(patternImage2Uri.toString());
                    }
                    else{
                        createOrderEidt.setPattern02ImageName(null);
                        createOrderEidt.setPattern02Name(null);
                        createOrderEidt.setPattern02Uri(null);
                    }

                    if (markUrgent.isChecked()) {
                        createOrderEidt.setUrgentNeed(true);
                    } else {
                        createOrderEidt.setUrgentNeed(false);
                    }


                    Intent intent = new Intent(CreateOrder_Activity.this, OrderCart_Activity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });


        addmeasurmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 toolbarText.setText("Add "+ serviceName.getText().toString()  +" Measurement");
                 currentScreenMode=true;
                 createOrderLayout.setVisibility(View.GONE);
                 measurementLayout.setVisibility(View.VISIBLE);


                 serviceNameMeasurement.setText(serviceName.getText().toString().trim());




                 if(measurementAdaptor==null) {
                     rcv_measurement.setLayoutManager(new LinearLayoutManager(CreateOrder_Activity.this));
                     if (orderStatus.equalsIgnoreCase("Edit"))
                     measurementAdaptor = new MeasurementAdaptor(CreateOrder_Activity.this, (ArrayList<ServicePart>) createOrderEidt.getNoMeasurement(),CreateOrder_Activity.this);
                    else
                     measurementAdaptor = new MeasurementAdaptor(CreateOrder_Activity.this, (ArrayList<ServicePart>) service.getServicePart(),CreateOrder_Activity.this);
                     rcv_measurement.setAdapter(measurementAdaptor);
                 }
            }
        });

        clothButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(CreateOrder_Activity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {


                                new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/MikeLau/Pictures", "/sdcard/MikeLau/Pictures");
                                CroperinoFileUtil.verifyStoragePermissions(CreateOrder_Activity.this);
                                CroperinoFileUtil.setupDirectory(CreateOrder_Activity.this);

                                activeImage="cloth1";
                                activeUri=clothImage1Uri;
                                Croperino.prepareChooser(CreateOrder_Activity.this, "Select Image", ContextCompat.getColor(CreateOrder_Activity.this, android.R.color.background_dark));


                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {


                                AlertDialog builder = new AlertDialog.Builder(CreateOrder_Activity.this)
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


        clothImageRemove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clothButton1.setVisibility(View.VISIBLE);
                clothImage1.setVisibility(View.GONE);
                clothImageRemove1.setVisibility(View.GONE);
                clothImage1Uri=null;
                clothImage1Name=null;
            }
        });


        clothButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(CreateOrder_Activity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {


                                new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/MikeLau/Pictures", "/sdcard/MikeLau/Pictures");
                                CroperinoFileUtil.verifyStoragePermissions(CreateOrder_Activity.this);
                                CroperinoFileUtil.setupDirectory(CreateOrder_Activity.this);

                                activeImage="cloth2";
                                activeUri=clothImage2Uri;
                                Croperino.prepareChooser(CreateOrder_Activity.this, "Select Image", ContextCompat.getColor(CreateOrder_Activity.this, android.R.color.background_dark));

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {


                                AlertDialog builder = new AlertDialog.Builder(CreateOrder_Activity.this)
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

        clothImageRemove2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                clothButton2.setVisibility(View.VISIBLE);
                clothImage2.setVisibility(View.GONE);
                clothImageRemove2.setVisibility(View.GONE);
                clothImage2Uri=null;
                clothImage2Name=null;

            }
        });



        patternButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(CreateOrder_Activity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/MikeLau/Pictures", "/sdcard/MikeLau/Pictures");
                                CroperinoFileUtil.verifyStoragePermissions(CreateOrder_Activity.this);
                                CroperinoFileUtil.setupDirectory(CreateOrder_Activity.this);

                                activeImage="pattern1";
                                activeUri=patternImage1Uri;
                                Croperino.prepareChooser(CreateOrder_Activity.this, "Select Image", ContextCompat.getColor(CreateOrder_Activity.this, android.R.color.background_dark));

                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {


                                AlertDialog builder = new AlertDialog.Builder(CreateOrder_Activity.this)
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

        patternImageRemove1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                patternButton1.setVisibility(View.VISIBLE);
                patternImageRemove1.setVisibility(View.GONE);
                patternImage1.setVisibility(View.GONE);
                patternImage1Name=null;
                patternImage1Uri=null;

            }
        });


        patternButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Dexter.withContext(CreateOrder_Activity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {


                                new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/MikeLau/Pictures", "/sdcard/MikeLau/Pictures");
                                CroperinoFileUtil.verifyStoragePermissions(CreateOrder_Activity.this);
                                CroperinoFileUtil.setupDirectory(CreateOrder_Activity.this);

                                activeImage="pattern2";
                                activeUri=patternImage2Uri;
                                Croperino.prepareChooser(CreateOrder_Activity.this, "Select Image", ContextCompat.getColor(CreateOrder_Activity.this, android.R.color.background_dark));
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {


                                AlertDialog builder = new AlertDialog.Builder(CreateOrder_Activity.this)
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
                patternButton2.setVisibility(View.VISIBLE);
                patternImageRemove2.setVisibility(View.GONE);
                patternImage2.setVisibility(View.GONE);
                patternImage2Name=null;
                patternImage2Uri=null;

            }
        });


        deliveryDateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                final DatePickerDialog datePickerDialog = new DatePickerDialog(CreateOrder_Activity.this,R.style.DialogTheme,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {


                                monthOfYear++;

                                String day,month;
                                day=String.valueOf(dayOfMonth);
                                month=String.valueOf(monthOfYear);

                                if((day.length()<2))
                                    day="0"+day;

                                if(month.length()<2)
                                    month="0"+month;

                                deliveryDateText.setText(day + "/" +month + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                c.add(Calendar.YEAR, 0);
                datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());
                datePickerDialog.show();
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

                    }
                    else if(activeImage.equalsIgnoreCase("cloth2")){

                        clothButton2.setVisibility(View.GONE);
                        clothImageRemove2.setVisibility(View.VISIBLE);
                        clothImage2.setVisibility(View.VISIBLE);

                        clothImage2Uri = Uri.fromFile(CroperinoFileUtil.getTempFile());
                        clothImage2.setImageURI(clothImage2Uri);

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

                    }
                    else if(activeImage.equalsIgnoreCase("pattern1")){

                        patternButton1.setVisibility(View.GONE);
                        patternImage1.setVisibility(View.VISIBLE);
                        patternImageRemove1.setVisibility(View.VISIBLE);

                        patternImage1Uri = Uri.fromFile(CroperinoFileUtil.getTempFile());
                        patternImage1.setImageURI(patternImage1Uri);

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

                    }
                    else if(activeImage.equalsIgnoreCase("pattern2")){

                        patternButton2.setVisibility(View.GONE);
                        patternImage2.setVisibility(View.VISIBLE);
                        patternImageRemove2.setVisibility(View.VISIBLE);

                        patternImage2Uri = Uri.fromFile(CroperinoFileUtil.getTempFile());
                        patternImage2.setImageURI(patternImage2Uri);

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


    private void requestPermission() {
        ActivityCompat.requestPermissions(CreateOrder_Activity.this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length> 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(CreateOrder_Activity.this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(CreateOrder_Activity.this,"Permission Denied",Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    public boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    public void MediaRecorderReady(){
        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
       mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
       // mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
      //  mediaRecorder.setOutputFile(AudioSavePathInDevice);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mediaRecorder.setOutputFile(dir);
        }
    }

    public String CreateRandomAudioFileName(int string){
        StringBuilder stringBuilder = new StringBuilder( string );
        int i = 0 ;
        while(i < string ) {
            stringBuilder.append(RandomAudioFileName.
                    charAt(random.nextInt(RandomAudioFileName.length())));

            i++ ;
        }
        return stringBuilder.toString();
    }

    public void show_Dialog(){
        ImageView recordIcon;
        final TextView cancelText,saveText,timmer;

        Dialog dialog=new Dialog(this);
        dialog.setContentView(R.layout.recording_dialog);

        recordIcon=dialog.findViewById(R.id.recordicon);
        cancelText=dialog.findViewById(R.id.cancel);
        saveText=dialog.findViewById(R.id.save);
        timmer=dialog.findViewById(R.id.timmer);

        recordIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CreateOrder_Activity.this, "Record Icon", Toast.LENGTH_SHORT).show();
                if(checkPermission()) {

                  /*  AudioSavePathInDevice =
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/" +
                                    CreateRandomAudioFileName(5) + "AudioRecording.3gp";
*/
                     dir = Environment.getExternalStorageDirectory();
                    try {
                        audiofile = File.createTempFile("sound", ".3gp", dir);
                    } catch (IOException e) {
                      /*  Log.e(TAG, "external storage access error");*/
                        return;
                    }

                    MediaRecorderReady();

                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                        timmer.setText("00:00");
                    } catch (IllegalStateException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }


                    Toast.makeText(CreateOrder_Activity.this, "Recording started",
                            Toast.LENGTH_LONG).show();
                } else {
                    requestPermission();
                }

            }
        });

        cancelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CreateOrder_Activity.this, "Cancel Icon", Toast.LENGTH_SHORT).show();
                mediaRecorder.stop();
            }
        });

        saveText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CreateOrder_Activity.this, "Save Icon", Toast.LENGTH_SHORT).show();
            }
        });


        dialog.show();
    }


    @Override
    public void measurementValues(ServicePart servicePart, int Position) {
        if(!orderStatus.equalsIgnoreCase("Edit"))
        service.getServicePart().set(Position,servicePart);
        else
            createOrderEidt.getNoMeasurement().set(Position,servicePart);
    }

    @Override
    public void onBackPressed() {

        if(currentScreenMode){

            toolbarText.setText("Add Pant Item");
            createOrderLayout.setVisibility(View.VISIBLE);
            measurementLayout.setVisibility(View.GONE);
            currentScreenMode=false;
            String values="";

            if (orderStatus.equalsIgnoreCase("Edit")) {

                for (ServicePart servicePart1 :createOrderEidt.getNoMeasurement()) {
                    if (servicePart1.getMeasurementValue()!=null) {
                        values = values + servicePart1.getServicePartName() + " : " + servicePart1.getMeasurementValue() + " ";
                    }

                }
            }
            else{
                for (ServicePart servicePart1 : service.getServicePart()) {
                    if (servicePart1.getMeasurementValue()!=null) {
                        values = values + servicePart1.getServicePartName() + " : " + servicePart1.getMeasurementValue() + " ";
                    }
                }
            }

            if(!values.equalsIgnoreCase(""))
                measurementText.setText(values);
        }
        else{
            super.onBackPressed();

        }
    }
}
