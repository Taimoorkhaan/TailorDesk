package com.digiconvalley.tailordesk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.Model.Albums.Album;
import com.digiconvalley.tailordesk.Model.TailorCustomers;
import com.digiconvalley.tailordesk.R;

import java.util.ArrayList;

public class AlbumAdapter extends RecyclerView.Adapter<AlbumAdapter.ViewHolder> {
    private Context context;
    private ArrayList<Album> albumArrayList;
    private AlbumList albumList;

    public AlbumAdapter(Context context) {
        this.context = context;
    }

    public AlbumAdapter(Context context, ArrayList<Album> albumArrayList, AlbumList albumList) {
        this.context = context;
        this.albumArrayList = albumArrayList;
        this.albumList = albumList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.rcv_album_desgin,parent, false);
        return new ViewHolder(view,albumList);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
         holder.onBindData(albumArrayList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return albumArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private AlbumList albumList;
        private ImageView addPicture,mainPictureHook;
        private CardView mainHook;

        public ViewHolder(@NonNull View itemView,AlbumAdapter.AlbumList callback) {
            super(itemView);

            addPicture=itemView.findViewById(R.id.addAlbum);
            mainPictureHook=itemView.findViewById(R.id.mainhook);

            albumList=callback;
        }
        public void onBindData(final Album album, final int index){
            if(album.getAlbumImage()!=null){
                mainPictureHook.setVisibility(View.VISIBLE);
                mainPictureHook.setImageURI(album.getAlbumImage());
                addPicture.setVisibility(View.GONE);
            }
            addPicture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(albumList!=null){
                        albumList.addAlbum(album,index);
                    }
                }
            });
        }
    }

    public interface AlbumList{
        public void addAlbum(Album album,int index);
    }
}
