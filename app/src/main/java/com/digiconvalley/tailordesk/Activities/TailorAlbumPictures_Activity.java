package com.digiconvalley.tailordesk.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.digiconvalley.tailordesk.Activities.AlbumActivities.Edit_AlbumPictures_Activity;
import com.digiconvalley.tailordesk.Adapter.TailorAlbumPicturesAdapter;
import com.digiconvalley.tailordesk.Model.Albums.AlbumDesigns;
import com.digiconvalley.tailordesk.Model.Albums.TailorAlbums;
import com.digiconvalley.tailordesk.R;

import java.util.ArrayList;

public class TailorAlbumPictures_Activity extends AppCompatActivity implements TailorAlbumPicturesAdapter.Albums{

    private TailorAlbums tailorAlbums;
    private RecyclerView allDesignsRcv;
    private TailorAlbumPicturesAdapter albumPicturesAdapter;
    private ImageView backBtn;
    private TextView editAlbum,centertext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_album_designs);

        allDesignsRcv=findViewById(R.id.allDesignRcv);
        backBtn=findViewById(R.id.back_button_);
        editAlbum=findViewById(R.id.editAlbum);
        centertext=findViewById(R.id.centerText);

        if(getIntent()!=null && getIntent().hasExtra("TailorAlbumPictures")){
            tailorAlbums= (TailorAlbums) getIntent().getSerializableExtra("TailorAlbumPictures");

            allDesignsRcv.setLayoutManager(new GridLayoutManager(this,2));

            albumPicturesAdapter=new TailorAlbumPicturesAdapter(TailorAlbumPictures_Activity.this,
                    (ArrayList<AlbumDesigns>) tailorAlbums.getAlbumDesignsMains().get(0).getAlbumDesign(),TailorAlbumPictures_Activity.this,false);

            allDesignsRcv.setAdapter(albumPicturesAdapter);

        }



        editAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(TailorAlbumPictures_Activity.this, Edit_AlbumPictures_Activity.class);
                intent.putExtra("TailorAlbumPictures",tailorAlbums);
                startActivity(intent);

            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
              /*  Toast.makeText(TailorAlbumPictures_Activity.this, "You Click me", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(TailorAlbumPictures_Activity.this,MainHome_Activity.class);
                intent.putExtra("Fragment","GalleryFragment");
                startActivity(intent);*/
                finish();
            }
        });
    }

    @Override
    public void pictureClicked(AlbumDesigns albumDesigns, Integer index) {
        Intent intent=new Intent(TailorAlbumPictures_Activity.this,FullScreen_AlbumPicture_Activity.class);
        intent.putExtra("Design",albumDesigns);
        startActivity(intent);
    }

    @Override
    public void deleteDesign(AlbumDesigns albumDesigns, Integer index) {

    }

    @Override
    public void addNewDesign(AlbumDesigns albumDesigns, Integer index) {

    }
}