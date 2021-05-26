package com.digiconvalley.tailordesk.Activities.AlbumActivities;

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
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digiconvalley.tailordesk.Activities.EditTailor_Profile_Activity;
import com.digiconvalley.tailordesk.Activities.MainHome_Activity;
import com.digiconvalley.tailordesk.Activities.MakeAlbum_Activity;
import com.digiconvalley.tailordesk.Activities.TailorAlbumPictures_Activity;
import com.digiconvalley.tailordesk.Adapter.TailorAlbumEditPicturesAdapter;
import com.digiconvalley.tailordesk.Adapter.TailorAlbumPicturesAdapter;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.Albums.Album;
import com.digiconvalley.tailordesk.Model.Albums.AlbumDesigns;
import com.digiconvalley.tailordesk.Model.Albums.TailorAlbums;
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

public class Edit_AlbumPictures_Activity extends AppCompatActivity implements TailorAlbumEditPicturesAdapter.Albums{

    private TailorAlbums tailorAlbums;
    private RecyclerView allDesignsRcv;
    private TailorAlbumEditPicturesAdapter albumPicturesAdapter;
    private ImageView backBtn,addDesignBtn;
    private int indexMain,mainDataSize;
    private Button editBtn;
    private AlbumDesigns mainAlbumDesigns;
    private ArrayList<AlbumDesigns> albumDesignsDeleted=new ArrayList<>();
    private ArrayList<AlbumDesigns> albumDesignsNew=new ArrayList<>();
    private JSONArray albumDesignNameArray,albumDesignArray,delAlbumDesigns;
    private JSONObject album;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__album_pictices_);

        allDesignsRcv=findViewById(R.id.allDesignRcv);
        backBtn=findViewById(R.id.back_button);
        addDesignBtn=findViewById(R.id.addDesign);
        editBtn=findViewById(R.id.editAlbum);



        if(getIntent()!=null && getIntent().hasExtra("TailorAlbumPictures")){


            tailorAlbums= (TailorAlbums) getIntent().getSerializableExtra("TailorAlbumPictures");
            mainDataSize=tailorAlbums.getAlbumDesignsMains().get(0).getAlbumDesign().size();
            allDesignsRcv.setLayoutManager(new GridLayoutManager(this,2));

            if(mainDataSize>0) {
                editBtn.setEnabled(true);
                editBtn.setAlpha(0.6f);
            }
            albumPicturesAdapter=new TailorAlbumEditPicturesAdapter(Edit_AlbumPictures_Activity.this,
                    (ArrayList<AlbumDesigns>) tailorAlbums.getAlbumDesignsMains().get(0).getAlbumDesign(),Edit_AlbumPictures_Activity.this);

            allDesignsRcv.setAdapter(albumPicturesAdapter);

        }

        addDesignBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editBtn.setAlpha(1f);
                editBtn.setEnabled(true);
                AlbumDesigns albumDesigns=new AlbumDesigns(true);
                tailorAlbums.getAlbumDesignsMains().get(0).getAlbumDesign().add(albumDesigns);
                albumPicturesAdapter.notifyDataSetChanged();
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                onBackPressed();

            }
        });

        editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(albumDesignsDeleted.size()>0 || tailorAlbums.getAlbumDesignsMains().get(0).getAlbumDesign().size()>mainDataSize)
                EditAlbum();
                else
                    Toast.makeText(Edit_AlbumPictures_Activity.this, "Kindly Edit Design", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void pictureClicked(AlbumDesigns albumDesigns, Integer index) {

    }

    @Override
    public void deleteDesign(AlbumDesigns albumDesigns, Integer index) {
        Toast.makeText(this, "You Delete "+index +"no Design", Toast.LENGTH_SHORT).show();
        editBtn.setAlpha(1f);
       if(albumDesigns.getNew()==null)
        albumDesignsDeleted.add(albumDesigns);

        tailorAlbums.getAlbumDesignsMains().get(0).getAlbumDesign().remove(albumDesigns);

        albumPicturesAdapter.notifyDataSetChanged();

    }

    @Override
    public void addNewDesign(AlbumDesigns albumDesigns, Integer index) {
        indexMain=index;
        mainAlbumDesigns=albumDesigns;
        Dexter.withContext(Edit_AlbumPictures_Activity.this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {


                        new CroperinoConfig("IMG_" + System.currentTimeMillis() + ".jpg", "/MikeLau/Pictures", "/sdcard/MikeLau/Pictures");
                        CroperinoFileUtil.verifyStoragePermissions(Edit_AlbumPictures_Activity.this);
                        CroperinoFileUtil.setupDirectory(Edit_AlbumPictures_Activity.this);

                        Croperino.prepareChooser(Edit_AlbumPictures_Activity.this, "Select Image", ContextCompat.getColor(Edit_AlbumPictures_Activity.this, android.R.color.background_dark));


                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {


                        AlertDialog builder = new AlertDialog.Builder(Edit_AlbumPictures_Activity.this)
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


                    mainAlbumDesigns.setDesignImage(Uri.fromFile(CroperinoFileUtil.getTempFile()));
                    mainAlbumDesigns.setDesignImageName(Uri.fromFile(CroperinoFileUtil.getTempFile()).getLastPathSegment());



                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),  Uri.fromFile(CroperinoFileUtil.getTempFile()));
                        Bitmap lastBitmap = null;
                        lastBitmap = bitmap;
                        //encoding image to string
                        String image = getStringImage(lastBitmap);
                        mainAlbumDesigns.setDesignImagee(image);
                        tailorAlbums.getAlbumDesignsMains().get(0).getAlbumDesign().set(indexMain,mainAlbumDesigns);
                        albumPicturesAdapter.notifyDataSetChanged();

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

    public void EditAlbum(){

        delAlbumDesigns=new JSONArray();
        albumDesignNameArray=new JSONArray();
        albumDesignArray=new JSONArray();
        album=new JSONObject();


        try {
            album.put("AlbumId",tailorAlbums.getAlbumDesignsMains().get(0).getAlbumId());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        try {
            for(AlbumDesigns album:tailorAlbums.getAlbumDesignsMains().get(0).getAlbumDesign()){
               if(album.getNew()!=null) {
                   if (album.getNew()) {
                       JSONObject albumDesginPictures = new JSONObject();
                       albumDesginPictures.put("AlbumPic", album.getDesignImagee());
                       albumDesginPictures.put("AlbumId", tailorAlbums.getAlbumDesignsMains().get(0).getAlbumId());
                       albumDesignArray.put(albumDesginPictures);
                   }
               }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        try {

            for(AlbumDesigns album:tailorAlbums.getAlbumDesignsMains().get(0).getAlbumDesign()){
                if(album.getNew()!=null) {
                    if (album.getNew()) {
                        JSONObject albumDesginPicturesName = new JSONObject();
                        albumDesginPicturesName.put("AlbumPicNames", album.getDesignImageName());
                        albumDesignNameArray.put(albumDesginPicturesName);
                    }
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(AlbumDesigns albumDesigns:albumDesignsDeleted){
            JSONObject albumDel=new JSONObject();
            try {
                albumDel.put("AlbumDesignId",albumDesigns.getAlbumDesignId());
                delAlbumDesigns.put(albumDel);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        StringRequest request = new StringRequest(Request.Method.POST, StaticData.baseUrl +"TailorPostApi/EditAlbum", new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson=new Gson();
                if (response != null) {
                    progressDialog.dismiss();
                    if (response.toString().equalsIgnoreCase("Invalid Data")) {
                        Toast.makeText(Edit_AlbumPictures_Activity.this, "Something went Wrong try Again", Toast.LENGTH_SHORT).show();
                    } else {

                        Toast.makeText(Edit_AlbumPictures_Activity.this, "Album Created Successfully", Toast.LENGTH_LONG).show();

                        Intent intent=new Intent(Edit_AlbumPictures_Activity.this, MainHome_Activity.class);
                        intent.putExtra("Fragment","GalleryFragment");
                        startActivity(intent);

                    }
                } else {
                    progressDialog.dismiss();
                    Log.e("Your Array Response", "Data Null");

                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Edit_AlbumPictures_Activity.this, "Erroe " + error.toString(), Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                Log.e("error is ", "" + error);
            }
        }) {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("AlbumDesign", albumDesignArray.toString());
                params.put("Album", album.toString());
                params.put("AlbumImagesNames", albumDesignNameArray.toString());
                params.put("AlbumDesignDelete", delAlbumDesigns.toString());
                return params;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(500000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(this).addToRequestque(request);

    }
}