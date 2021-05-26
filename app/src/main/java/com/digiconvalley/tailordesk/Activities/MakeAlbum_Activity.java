package com.digiconvalley.tailordesk.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digiconvalley.tailordesk.Adapter.AlbumAdapter;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.Albums.Album;
import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.Singleton.SingletonPattren;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MakeAlbum_Activity extends AppCompatActivity implements AlbumAdapter.AlbumList{
    private TextView albumName;
    private Button saveAlbum;
    private RecyclerView rcvAlbum;
    private AlbumAdapter albumAdapter;
    private ImageView addDesgin,backBtn;
    private ArrayList<Album> albums =new ArrayList<>();
    private int indexMain;
    private JSONObject albumObj,albumDirectoryObj;
    private JSONArray albumDesignNameArray,albumDesignArray;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_album_);

        albumName=findViewById(R.id.albumName);
        rcvAlbum=findViewById(R.id.album_rcv);
        addDesgin=findViewById(R.id.addDesgin);
        backBtn=findViewById(R.id.back_button);
        saveAlbum=findViewById(R.id.saveAlbum);

        albums.add(new Album());

        if(getIntent()!=null && getIntent().hasExtra("AlbumName")){
            albumName.setText(getIntent().getStringExtra("AlbumName"));

        }
        rcvAlbum.setLayoutManager(new GridLayoutManager(this,2));

        albumAdapter=new AlbumAdapter(this,albums,MakeAlbum_Activity.this);
        rcvAlbum.setAdapter(albumAdapter);

        saveAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(albums.size()==1){
                    if(albums.get(0).getAlbumImage()==null){
                        Toast.makeText(MakeAlbum_Activity.this, "Kindly Select Atleast one Desgin", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                createAlbum();
            }
        });
        addDesgin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                albums.add(new Album());
           albumAdapter.notifyDataSetChanged();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              onBackPressed();
              finish();
            }
        });

    }

    @Override
    public void addAlbum(Album album, int index) {
        indexMain=index;
        Dexter.withContext(MakeAlbum_Activity.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {


                        new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/MikeLau/Pictures", "/sdcard/MikeLau/Pictures");
                        CroperinoFileUtil.verifyStoragePermissions(MakeAlbum_Activity.this);
                        CroperinoFileUtil.setupDirectory(MakeAlbum_Activity.this);

                        Croperino.prepareChooser(MakeAlbum_Activity.this, "Select Image", ContextCompat.getColor(MakeAlbum_Activity.this, android.R.color.background_dark));


                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {


                        AlertDialog builder = new AlertDialog.Builder(MakeAlbum_Activity.this)
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


                    albums.get(indexMain).setAlbumImage( Uri.fromFile(CroperinoFileUtil.getTempFile()));
                    albums.get(indexMain).setAlbumImageName(Uri.fromFile(CroperinoFileUtil.getTempFile()).getLastPathSegment());

                    albumAdapter.notifyDataSetChanged();

                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),  Uri.fromFile(CroperinoFileUtil.getTempFile()));
                        Bitmap lastBitmap = null;
                        lastBitmap = bitmap;
                        //encoding image to string
                        String image = getStringImage(lastBitmap);
                        albums.get(indexMain).setAlbumImageDecoded(image);

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

    public void createAlbum(){


        albumObj=new JSONObject();
        albumDirectoryObj=new JSONObject();
        albumDesignNameArray=new JSONArray();
        albumDesignArray=new JSONArray();


        try {
          albumObj.put("AlbumName",albumName.getText().toString().trim());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
           albumDirectoryObj.put("TailorId",StaticData.tailorProfile.getTailorID());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
         for(Album album:albums){
             if(album.getAlbumImage()!=null){
                 JSONObject albumDesginPictures=new JSONObject();
                 albumDesginPictures.put("AlbumPic",album.getAlbumImageDecoded());
                 albumDesignArray.put(albumDesginPictures);
             }

         }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        try {

            for(Album album:albums){
                if(album.getAlbumImage()!=null){
                    JSONObject albumDesginPicturesName=new JSONObject();
                    albumDesginPicturesName.put("AlbumPicName",album.getAlbumImageName());
                    albumDesignNameArray.put(albumDesginPicturesName);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, StaticData.baseUrl +"TailorPostApi/CreateAlbum", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                if (response != null) {
                    progressDialog.dismiss();
                    if (response.toString().equalsIgnoreCase("Invalid Data")) {
                        Toast.makeText(MakeAlbum_Activity.this, "Something went Wrong try Again", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(MakeAlbum_Activity.this, "Album Created Successfully", Toast.LENGTH_LONG).show();

                        Intent intent=new Intent(MakeAlbum_Activity.this,MainHome_Activity.class);
                        intent.putExtra("Fragment","GalleryFragment");
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
                Toast.makeText(MakeAlbum_Activity.this, "Erroe " + error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                Log.e("error is ", "" + error);
            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("Album", albumObj.toString());
                params.put("AlbumDirectory", albumDirectoryObj.toString());
                params.put("AlbumDesign", albumDesignArray.toString());
                params.put("AlbumDesignName", albumDesignNameArray.toString());

                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(this).addToRequestque(request);

    }
}