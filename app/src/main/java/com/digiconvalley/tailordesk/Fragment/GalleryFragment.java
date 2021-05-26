package com.digiconvalley.tailordesk.Fragment;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.digiconvalley.tailordesk.Activities.MainHome_Activity;
import com.digiconvalley.tailordesk.Activities.SetupDetail02;
import com.digiconvalley.tailordesk.Activities.TailorAlbumPictures_Activity;
import com.digiconvalley.tailordesk.Adapter.CategoryAdapter;
import com.digiconvalley.tailordesk.Adapter.TailorAlbumsAdapter;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Activities.MakeAlbum_Activity;
import com.digiconvalley.tailordesk.Model.Albums.TailorAlbums;
import com.digiconvalley.tailordesk.Model.CatagoryMain;
import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.Singleton.SingletonPattren;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class GalleryFragment extends Fragment implements TailorAlbumsAdapter.Albumss {

    private Dialog album_Dialog,deleteWarningDialog;
    private TextView createAlbumBtn;
    private TailorAlbums tailorAlbums;
    private LinearLayout noAlbumsLayout;
    private RecyclerView tailorAlbumsRcv;
    private TailorAlbumsAdapter tailorAlbumsAdapter;
    private ArrayList<TailorAlbums> tailorAlbumsArrayList;
    private Boolean isDeleteMode=false;


    public GalleryFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        createAlbumBtn=view.findViewById(R.id.createAlbum);
        noAlbumsLayout=view.findViewById(R.id.noAlbumsLayout);
        tailorAlbumsRcv=view.findViewById(R.id.tailor_album_rcv);


        album_Dialog=new Dialog(getContext());
        getTailorAlbums(StaticData.tailorProfile.getTailorID());


        createAlbumBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView createAlbum;
                final EditText albumName;

                album_Dialog.setContentView(R.layout.dialog_create_album);

                createAlbum=album_Dialog.findViewById(R.id.createAlbum);
                albumName=album_Dialog.findViewById(R.id.albumName);

                createAlbum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(albumName.getText().toString().trim().isEmpty()){
                            Toast.makeText(getContext(), "Please Enter Album Name", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Intent intent=new Intent(getContext(), MakeAlbum_Activity.class);
                        intent.putExtra("AlbumName",albumName.getText().toString().trim());
                        startActivity(intent);
                    }
                });

                album_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                album_Dialog.show();

                MainHome_Activity.toolBarImg.setVisibility(View.GONE);
                MainHome_Activity.toolBarText.setVisibility(View.VISIBLE);
                MainHome_Activity.toolBarText.setText("Album");

                album_Dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {

                        MainHome_Activity.toolBarImg.setVisibility(View.VISIBLE);
                        MainHome_Activity.toolBarText.setVisibility(View.GONE);
                        MainHome_Activity.toolBarText.setText("Albums");
                    }
                });

            }
        });
    }
    public void getTailorAlbums(int tailorId) {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        String url;


        url = StaticData.baseUrl +"TailorGetApi/GetAlbums?TailorId="+tailorId;

        StringRequest req = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    Gson gson = new Gson();



                        Type listType = new TypeToken<List<TailorAlbums>>() {
                        }.getType();

                       tailorAlbumsArrayList = gson.fromJson(response,listType);
                       if(tailorAlbumsArrayList!=null && tailorAlbumsArrayList.size()>0){
//                           Toast.makeText(getContext(), "I am here"+tailorAlbumsArrayList.size(), Toast.LENGTH_SHORT).show();
                           noAlbumsLayout.setVisibility(View.GONE);
                           tailorAlbumsRcv.setVisibility(View.VISIBLE);
                           tailorAlbumsRcv.setLayoutManager(new GridLayoutManager(getContext(),2));
                           tailorAlbumsAdapter= new TailorAlbumsAdapter(getContext(),tailorAlbumsArrayList,GalleryFragment.this);
                          tailorAlbumsRcv.setAdapter(tailorAlbumsAdapter);

                           MainHome_Activity.toolBarImg.setVisibility(View.GONE);
                           MainHome_Activity.toolBarText.setVisibility(View.VISIBLE);
                           MainHome_Activity.toolBarText.setText("Albums");
                       }


                    progressDialog.dismiss();
                    }

                 else {
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
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
        SingletonPattren.getmInstance(getContext()).addToRequestque(req);

    }

    @Override
    public void albumClicked(TailorAlbums tailorAlbums, Integer index) {
        Intent intent=new Intent(getContext(), TailorAlbumPictures_Activity.class);
        intent.putExtra("TailorAlbumPictures",tailorAlbums);
        startActivity(intent);
}

    @Override
    public void deleteAlbum(final TailorAlbums tailorAlbums, final Integer index) {
        Toast.makeText(getContext(), "Are you Sure to Delete this Album It Contains "+tailorAlbums.getAlbumDesignsMains().get(0).getAlbumDesign().size(), Toast.LENGTH_SHORT).show();

        Button confirmBtn,cancelBtn;
        TextView dialogText;

         deleteWarningDialog=new Dialog(getContext());
         deleteWarningDialog.setContentView(R.layout.warning_dialog_view);

         confirmBtn=deleteWarningDialog.findViewById(R.id.confmDel);
         cancelBtn=deleteWarningDialog.findViewById(R.id.cancelbtn);
         dialogText=deleteWarningDialog.findViewById(R.id.dialogText);

         dialogText.setText("This Album Contains "+tailorAlbums.getAlbumDesignsMains().get(0).getAlbumDesign().size()+" Designs");


         confirmBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {

                 deleteAlbumm(tailorAlbums.getAlbumDesignsMains().get(0).getAlbumId(),index);
                 deleteWarningDialog.dismiss();
             }
         });

         cancelBtn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 deleteWarningDialog.dismiss();
             }
         });

         deleteWarningDialog.show();
         deleteWarningDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.gallery_fragment_menu, menu);
      //  super.onCreateOptionsMenu(menu, inflater);

    }


    @Override
    public void registerForContextMenu(@NonNull View view) {
        super.registerForContextMenu(view);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.addAlbumMenu) {

            TextView createAlbum;
            final EditText albumName;

            album_Dialog.setContentView(R.layout.dialog_create_album);

            createAlbum = album_Dialog.findViewById(R.id.createAlbum);
            albumName = album_Dialog.findViewById(R.id.albumName);

            createAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (albumName.getText().toString().trim().isEmpty()) {
                        Toast.makeText(getContext(), "Please Enter Album Name", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if(tailorAlbumsArrayList!=null) {
                        for (TailorAlbums tailorAlbums : tailorAlbumsArrayList) {
                            if (albumName.getText().toString().trim().equalsIgnoreCase(tailorAlbums.getAlbumDesignsMains().get(0).getAlbumName())) {
                                Toast.makeText(getContext(), "This Name Album Already Created Please Change Album Name", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                    Intent intent = new Intent(getContext(), MakeAlbum_Activity.class);
                    intent.putExtra("AlbumName", albumName.getText().toString().trim());
                    startActivity(intent);
                    album_Dialog.dismiss();
                }
            });

            album_Dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            if(!isDeleteMode)
            album_Dialog.show();
        }

        if(item.getItemId() == R.id.delete_album){

            if(!isDeleteMode)
                isDeleteMode=true;
            else
                isDeleteMode=false;

            tailorAlbumsAdapter= new TailorAlbumsAdapter(getContext(),tailorAlbumsArrayList,GalleryFragment.this,isDeleteMode);
            tailorAlbumsRcv.setAdapter(tailorAlbumsAdapter);
        }
        return super.onOptionsItemSelected(item);
    }
    public void deleteAlbumm(int albumID, final int index) {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());

        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        progressDialog.setCancelable(false);
        String url;


        url = StaticData.baseUrl +"TailorGetApi/delSigleAlbum?AlbumId="+albumID;

        StringRequest req = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {

                    Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                    String res="Success";
                    if(response.equalsIgnoreCase("\"Success\"")) {
                        if (tailorAlbumsArrayList != null && tailorAlbumsArrayList.size() > 1 && tailorAlbumsAdapter != null) {

                            tailorAlbumsArrayList.remove(index);
                            noAlbumsLayout.setVisibility(View.GONE);
                            tailorAlbumsRcv.setVisibility(View.VISIBLE);
                            tailorAlbumsAdapter.notifyDataSetChanged();
                        }
                        else{
                            tailorAlbumsArrayList.remove(index);
                            noAlbumsLayout.setVisibility(View.VISIBLE);
                            tailorAlbumsRcv.setVisibility(View.GONE);
                            tailorAlbumsAdapter.notifyDataSetChanged();
                        }
                    }



                    progressDialog.dismiss();
                } else {
                    Toast.makeText(getContext(), "Server Error", Toast.LENGTH_SHORT).show();
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

        req.setRetryPolicy(new DefaultRetryPolicy(5000000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        SingletonPattren.getmInstance(getContext()).addToRequestque(req);

    }

    @Override
    public void onStart() {
        super.onStart();
        MainHome_Activity.toolBarImg.setVisibility(View.VISIBLE);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)MainHome_Activity.toolBarImg.getLayoutParams();
        params.addRule(RelativeLayout.ALIGN_END);


        MainHome_Activity.toolBarImg.setLayoutParams(params);



        MainHome_Activity.toolBarText.setVisibility(View.GONE);
        MainHome_Activity.toolBarText.setText("Albums");
    }
}