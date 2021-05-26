package com.digiconvalley.tailordesk.Maps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.digiconvalley.tailordesk.Activities.EditTailor_Profile_Activity;
import com.digiconvalley.tailordesk.Activities.SetupDetail03;
import com.digiconvalley.tailordesk.Model.TailorBasicProfile;
import com.digiconvalley.tailordesk.Model.TailorProfileApi.TailorProfileGet;
import com.digiconvalley.tailordesk.Model.TailorProfileApi.TailorProfileMain;
import com.digiconvalley.tailordesk.R;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class TailorLocation_Activity extends AppCompatActivity implements OnMapReadyCallback {
    private TextView locationName;
    private Button saveLocationBtn;
    private GoogleMap mMap;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private Location lastLocation;
    private LocationCallback locationCallback;
    private Button btnFind;
    private View mapView;

    private final float defaultZoom = 15;
    private Geocoder geocoder;
    private List<Address> address;
    String addres;
    private Double longtite, latitude;
    private Button btn_Confrm;
    private ImageView backBtn;
    private int Index;
    private TailorBasicProfile tailorBasicProfile;
    private ImageButton currentLocationBtn;
    private TailorProfileMain tailorProfileMain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tailor_location_);

        locationName = findViewById(R.id.location_name);
        saveLocationBtn = findViewById(R.id.confirm);
        currentLocationBtn = findViewById(R.id.current_location);
        backBtn = findViewById(R.id.back_button);

        Check();

        if (getIntent() != null) {
            tailorBasicProfile = (TailorBasicProfile) getIntent().getSerializableExtra("tailorBasicInfo");
        }

        if(getIntent()!=null && getIntent().hasExtra("TailorProfile")){
            tailorProfileMain= (TailorProfileMain) getIntent().getSerializableExtra("TailorProfile");
        }

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        assert supportMapFragment != null;
        supportMapFragment.getMapAsync(this);
        mapView = supportMapFragment.getView();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(TailorLocation_Activity.this);

        saveLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tailorBasicProfile!=null) {
                    if (latitude != null && longtite != null) {
                        Intent intent = new Intent(TailorLocation_Activity.this, SetupDetail03.class);
                        tailorBasicProfile.setLatitude(latitude);
                        tailorBasicProfile.setLongtite(longtite);
                        tailorBasicProfile.setAddress(addres);
                        intent.putExtra("tailorBasicInfo", tailorBasicProfile);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(TailorLocation_Activity.this, "Kindly Select your Location..", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if (tailorProfileMain.getTailorData().getTailorshopData().getShopAddress() != null) {
                        if (latitude != null && longtite != null) {

                            Intent intent = new Intent(TailorLocation_Activity.this, EditTailor_Profile_Activity.class);
                            tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().setShopPinLocation(addres);
                            tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().setXcoordinate(latitude);
                            tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().setYcoordinate(longtite);
                            intent.putExtra("TailorProfile", tailorProfileMain);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(TailorLocation_Activity.this, "Kindly Select your Location..", Toast.LENGTH_SHORT).show();
                        }
                     //   Toast.makeText(TailorLocation_Activity.this, "I am here ", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        currentLocationBtn.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("MissingPermission")
            @Override
            public void onClick(View view) {

                if (mMap != null) {

                    getDeviceLocation();
                    mMap.setMyLocationEnabled(true);

                }

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tailorBasicProfile != null) {
                    if (latitude != null && longtite != null) {
                        Intent intent = new Intent(TailorLocation_Activity.this, SetupDetail03.class);
                        tailorBasicProfile.setLatitude(latitude);
                        tailorBasicProfile.setLongtite(longtite);
                        tailorBasicProfile.setAddress(addres);
                        intent.putExtra("tailorBasicInfo", tailorBasicProfile);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(TailorLocation_Activity.this, "Kindly Select your Location..", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    if (tailorProfileMain.getTailorData().getTailorshopData().getShopAddress() != null) {
                        if (latitude != null && longtite != null) {

                            Intent intent = new Intent(TailorLocation_Activity.this, EditTailor_Profile_Activity.class);
                            tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().setShopPinLocation(addres);
                            tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().setXcoordinate(latitude);
                            tailorProfileMain.getTailorData().getTailorshopData().getShopAddress().setYcoordinate(longtite);
                            intent.putExtra("TailorProfile", tailorProfileMain);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(TailorLocation_Activity.this, "Kindly Select your Location..", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.mapstyle));

            if (!success) {

            }
        } catch (Resources.NotFoundException e) {

        }
        //   Toast.makeText(this, "On Map REady", Toast.LENGTH_SHORT).show();
        // Add a marker in Sydney and move the camera
        //    mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(false);


      /*  if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 40, 180);
        }*/

        //   if (mapView != null && mapView.findViewById(Integer.parseInt("1")) != null) {
          /*  View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) locationButton.getLayoutParams();
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 40, 290);*/

        //Check is gps in enable or not

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(50000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        SettingsClient settingsClient = LocationServices.getSettingsClient(TailorLocation_Activity.this);
        Task<LocationSettingsResponse> task = settingsClient.checkLocationSettings(builder.build());

        task.addOnSuccessListener(new OnSuccessListener<LocationSettingsResponse>() {
            @Override
            public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                getDeviceLocation();
                //      Toast.makeText(OfficeMapPinActivity.this, "I am in Tast on Success", Toast.LENGTH_SHORT).show();
            }
        });

        task.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (e instanceof ResolvableApiException) {
                    //  Toast.makeText(OfficeMapPinActivity.this, "I am in failure listener", Toast.LENGTH_SHORT).show();
                    ResolvableApiException resolvableApiException = (ResolvableApiException) e;
                    try {

                        resolvableApiException.startResolutionForResult(TailorLocation_Activity.this, 50);
                    } catch (IntentSender.SendIntentException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });


        mMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
            @Override
            public void onCameraIdle() {
                CameraPosition cameraPosition = mMap.getCameraPosition();
                LatLng currentCenter = cameraPosition.target;

                geocoder = new Geocoder(TailorLocation_Activity.this, Locale.getDefault());
                try {
                    longtite = currentCenter.longitude;
                    latitude = currentCenter.latitude;

                    address = geocoder.getFromLocation(currentCenter.latitude, currentCenter.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                    if (address != null && address.size() > 0) {
                        addres = address.get(0).getAddressLine(0);
                        //  Toast.makeText(OfficeMapPinActivity.this, "Loaction"+addres, Toast.LENGTH_SHORT).show();
                        locationName.setText(addres);
                    }
                } catch (IOException e) {
                    Toast.makeText(TailorLocation_Activity.this, "Exception", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 50) {
            if (resultCode == RESULT_OK) {
                //   Toast.makeText(this, "I am in on Activity result", Toast.LENGTH_SHORT).show();
                getDeviceLocation();
            }
        }
    }

    private void getDeviceLocation() {

        //  Toast.makeText(this, "I am here GEtDevice Loaoctom", Toast.LENGTH_SHORT).show();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        Dexter.withContext(TailorLocation_Activity.this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        //  Toast.makeText(OfficeMapPinActivity.this, "Ok", Toast.LENGTH_SHORT).show();

                        if (ActivityCompat.checkSelfPermission(TailorLocation_Activity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(TailorLocation_Activity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                            @SuppressLint("MissingPermission")
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                if (task.isSuccessful()) {
                                    lastLocation = task.getResult();
                                    if (lastLocation != null) {
                                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), defaultZoom));

                                    } else {
                                        final LocationRequest locationRequest = LocationRequest.create();
                                        locationRequest.setInterval(10000);
                                        locationRequest.setFastestInterval(5000);
                                        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                                        locationCallback = new LocationCallback() {
                                            @Override
                                            public void onLocationResult(LocationResult locationResult) {
                                                super.onLocationResult(locationResult);

                                                if (locationRequest == null)
                                                    return;
                                                lastLocation = locationResult.getLastLocation();
                                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), defaultZoom));
                               /* geocoder = new Geocoder(OfficeMapPinActivity.this, Locale.getDefault());
                                try {
                                    address = geocoder.getFromLocation(lastLocation.getLatitude(), lastLocation.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                                    if (address != null && address.size() > 0) {
                                        String addres = address.get(0).getAddressLine(0);
                                        locationName.setText(addres);
                                        Toast.makeText(OfficeMapPinActivity.this, "Loaction"+addres, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (IOException e) {
                                    Toast.makeText(OfficeMapPinActivity.this, "Exception", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                }*/
                                                fusedLocationProviderClient.removeLocationUpdates(locationCallback);
                                            }
                                        };
                                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null);
                                    }
                                } else {
                                    // Toast.makeText(OfficeMapPinActivity.this, "Unable to get Location", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        //  Toast.makeText(OfficeMapPinActivity.this, "Ok not", Toast.LENGTH_SHORT).show();

                        AlertDialog builder=new AlertDialog.Builder(TailorLocation_Activity.this)
                                .setMessage("Permisssion Required")
                                .setNegativeButton("no",null)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        intent.setData(Uri.fromParts("Package",getPackageName(),null));
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
    public void Check(){
        Dexter.withContext(TailorLocation_Activity.this)
                .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        //  Toast.makeText(OfficeMapPinActivity.this, "Ok", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        //  Toast.makeText(OfficeMapPinActivity.this, "Ok not", Toast.LENGTH_SHORT).show();

                        AlertDialog builder=new AlertDialog.Builder(TailorLocation_Activity.this)
                                .setMessage("Permisssion Required")
                                .setNegativeButton("no",null)
                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent intent=new Intent();
                                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                        intent.setData(Uri.fromParts("Package",getPackageName(),null));
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
}