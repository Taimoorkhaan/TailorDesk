package com.digiconvalley.tailordesk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.Model.DrawerItems;
import com.digiconvalley.tailordesk.R;

import java.util.ArrayList;

public class DrawerrAdapter extends RecyclerView.Adapter<DrawerrAdapter.ViewHolder> {

   private Context context;
   private ArrayList<DrawerItems> drawerItems;
   private DrawerListener drawerListener;

    public DrawerrAdapter(Context context, ArrayList<DrawerItems> drawerItems, DrawerListener drawerListener) {
        this.context = context;
        this.drawerItems = drawerItems;
        this.drawerListener = drawerListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.drawer_rcv_view,parent, false);
        return new ViewHolder(view,drawerListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
         holder.onBindData(drawerItems.get(position),position);
    }

    @Override
    public int getItemCount() {
        return drawerItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView drawerText;
        private RelativeLayout main_rcv;
        private ImageView drawerImage;
        private DrawerrAdapter.DrawerListener drawerListener;

        public ViewHolder(@NonNull View itemView,DrawerListener callback) {
            super(itemView);
            drawerText=itemView.findViewById(R.id.rcv_drawer_text);
            main_rcv=itemView.findViewById(R.id.rcv_Main_layout);
            drawerImage=itemView.findViewById(R.id.drawerImage);

            drawerListener=callback;
        }

        public void onBindData(final DrawerItems drawerItems, final int index){

            drawerImage.setImageResource(drawerItems.getItemImage());
            drawerText.setText(drawerItems.getItemName());
            main_rcv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(drawerListener!=null){

                       drawerListener.itemClicked(drawerItems,index);
                    }
                }
            });

            drawerText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(drawerListener!=null){

                        drawerListener.itemClicked(drawerItems,index);
                    }
                }
            });
        }


    }
    public interface DrawerListener{
      public void itemClicked(DrawerItems drawerItems,int index);
    }
}
