package com.digiconvalley.tailordesk.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.Albums.AlbumDesigns;
import com.digiconvalley.tailordesk.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class FullScreen_AlbumPicture_Activity extends AppCompatActivity {
    private ImageView fullScreenImage;
    private AlbumDesigns albumDesigns;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen__album_picture_);

        fullScreenImage=findViewById(R.id.fullScreenPicture);

        if(getIntent()!=null && getIntent().hasExtra("Design")){
            albumDesigns= (AlbumDesigns) getIntent().getSerializableExtra("Design");
            Picasso.get().load(StaticData.baseUrlImages +albumDesigns.getAlbumPic()).fit().centerCrop()

                    .placeholder(R.drawable.personimage)
                    .error(R.drawable.personimage)
                    .into(fullScreenImage, new Callback() {
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


    }
}