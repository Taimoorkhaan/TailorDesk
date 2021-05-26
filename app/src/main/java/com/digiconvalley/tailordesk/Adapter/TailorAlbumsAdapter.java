package com.digiconvalley.tailordesk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.Albums.AlbumDesigns;
import com.digiconvalley.tailordesk.Model.Albums.TailorAlbums;
import com.digiconvalley.tailordesk.Model.TailorOrders.TailorOrders;
import com.digiconvalley.tailordesk.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TailorAlbumsAdapter extends RecyclerView.Adapter<TailorAlbumsAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TailorAlbums> albumDesigns;
    private Albumss albumss;
    private Boolean deleteStatus =false;

    public TailorAlbumsAdapter(Context context, ArrayList<TailorAlbums> albumDesigns) {
        this.context = context;
        this.albumDesigns = albumDesigns;
    }

    public TailorAlbumsAdapter(Context context, ArrayList<TailorAlbums> albumDesigns, Albumss albumss) {
        this.context = context;
        this.albumDesigns = albumDesigns;
        this.albumss = albumss;
    }

    public TailorAlbumsAdapter(Context context, ArrayList<TailorAlbums> albumDesigns, Albumss albumss, Boolean deleteStatus) {
        this.context = context;
        this.albumDesigns = albumDesigns;
        this.albumss = albumss;
        this.deleteStatus = deleteStatus;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.rcv_albums_desgin,parent, false);
        return new ViewHolder(view,albumss);
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

        private ImageView mainPictureHook;
        private TextView albumName,deleteAlbum;
        private Albumss albumss;
        private LinearLayout mainLayout;

        public ViewHolder(@NonNull View itemView,Albumss callback) {
            super(itemView);

            mainPictureHook=itemView.findViewById(R.id.mainhook);
            albumName=itemView.findViewById(R.id.albumName);
            mainLayout=itemView.findViewById(R.id.mainLayoutt);
            deleteAlbum=itemView.findViewById(R.id.deleteAlbum);

            albumss=callback;

        }
        public void OnBindData(final TailorAlbums albumDesigns, final int index){

            if(deleteStatus)
                deleteAlbum.setVisibility(View.VISIBLE);
            else
                deleteAlbum.setVisibility(View.GONE);

            albumName.setText(albumDesigns.getAlbumDesignsMains().get(0).getAlbumName());


            if(albumDesigns.getAlbumDesignsMains().get(0).getAlbumDesign().size()>0) {
                Picasso.get().load(StaticData.baseUrlImages + albumDesigns.getAlbumDesignsMains().get(0).getAlbumDesign().
                        get(0).getAlbumPic()).fit().centerCrop()

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
            }
            deleteAlbum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(albumss!=null){
                        albumss.deleteAlbum(albumDesigns,index);
                    }
                }
            });

            mainLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!deleteStatus) {
                        if (albumss != null) {
                            albumss.albumClicked(albumDesigns, index);
                        }
                    }
                }

            });
        }
    }
    public interface Albumss{
        public void albumClicked(TailorAlbums tailorAlbums,Integer index);
        public void deleteAlbum(TailorAlbums tailorAlbums,Integer index);
    }
}
