package com.digiconvalley.tailordesk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.Albums.Album;
import com.digiconvalley.tailordesk.Model.Albums.AlbumDesigns;
import com.digiconvalley.tailordesk.Model.Albums.TailorAlbums;
import com.digiconvalley.tailordesk.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TailorAlbumPicturesAdapter extends RecyclerView.Adapter<TailorAlbumPicturesAdapter.ViewHolder> {
    private Context context;
    private ArrayList<AlbumDesigns> albumDesigns;
    private Albums albums;
    private Boolean deleteAlbumStatus=false;

    public TailorAlbumPicturesAdapter(Context context, ArrayList<AlbumDesigns> albumDesigns) {
        this.context = context;
        this.albumDesigns = albumDesigns;
    }

    public TailorAlbumPicturesAdapter(Context context, ArrayList<AlbumDesigns> albumDesigns, Albums albums) {
        this.context = context;
        this.albumDesigns = albumDesigns;
        this.albums = albums;
    }

    public TailorAlbumPicturesAdapter(Context context, ArrayList<AlbumDesigns> albumDesigns, Albums albums, Boolean deleteAlbumStatus) {
        this.context = context;
        this.albumDesigns = albumDesigns;
        this.albums = albums;
        this.deleteAlbumStatus = deleteAlbumStatus;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.rcv_albums_desgin,parent, false);
        return new ViewHolder(view,albums);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        holder.OnBindData(albumDesigns.get(position),position);
    }

    @Override
    public int getItemCount() {
        return albumDesigns.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mainPictureHook,addDesgin;
        private TextView albumName,deleteAlbum;
        private LinearLayout mainLayout;
        private Albums albums;
        public ViewHolder(@NonNull View itemView, Albums callback) {
            super(itemView);

            mainPictureHook=itemView.findViewById(R.id.mainhook);
            albumName=itemView.findViewById(R.id.albumName);
            mainLayout=itemView.findViewById(R.id.mainLayoutt);
            deleteAlbum=itemView.findViewById(R.id.deleteAlbum);
            addDesgin=itemView.findViewById(R.id.addAlbum);

            deleteAlbum.setText("Delete Design");

            albums =callback;
        }

        public void OnBindData(final AlbumDesigns albumDesigns, final Integer index){

            albumName.setText("Design "+index);
            if(deleteAlbumStatus)
                deleteAlbum.setVisibility(View.VISIBLE);
            else
                deleteAlbum.setVisibility(View.GONE);

           /* if(albumDesigns.getNew()!=null){
                if(albumDesigns.getNew()){

                    if(albumDesigns.getDesignImage()!=null){
                        addDesgin.setVisibility(View.GONE);
                        deleteAlbum.setVisibility(View.VISIBLE);
                        mainPictureHook.setVisibility(View.VISIBLE);
                        mainPictureHook.setImageURI(albumDesigns.getDesignImage());

                    }
                    else{

                        mainPictureHook.setVisibility(View.GONE);
                        addDesgin.setVisibility(View.VISIBLE);
                        deleteAlbum.setVisibility(View.GONE);
                    }

                }
            }

*/

            if(albumDesigns.getNew()==null) {
               // if (!albumDesigns.getNew()) {
                    Picasso.get().load(StaticData.baseUrlImages + albumDesigns.getAlbumPic()).fit().centerCrop()
                            .placeholder(R.drawable.personimage)
                            .error(R.drawable.personimage)
                            .into(mainPictureHook, new Callback() {
                                @Override
                                public void onSuccess() {
                                    // progressBarMain.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError(Exception e) {
                                    // progressBarMain.setVisibility(View.VISIBLE);
                                }
                            });
                //}
            }
            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(albums!=null){
                        albums.pictureClicked(albumDesigns,index);
                    }

                    deleteAlbum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(albums!=null){
                                albums.deleteDesign(albumDesigns,index);
                            }
                        }
                    });

                    addDesgin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(albums!=null){
                                albums.addNewDesign(albumDesigns,index);
                            }
                        }
                    });
                }
            });
        }

    }
    public interface Albums{

        public void pictureClicked(AlbumDesigns albumDesigns, Integer index);
        public void deleteDesign(AlbumDesigns albumDesigns, Integer index);
        public void addNewDesign(AlbumDesigns albumDesigns, Integer index);
    }
}
