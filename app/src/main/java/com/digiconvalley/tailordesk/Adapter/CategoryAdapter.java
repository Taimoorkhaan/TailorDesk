package com.digiconvalley.tailordesk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.digiconvalley.tailordesk.Model.CatagoryMain;
import com.digiconvalley.tailordesk.R;

import java.util.ArrayList;

public class CategoryAdapter  extends RecyclerView.Adapter<CategoryAdapter.viewHolder> {

     private Context context;
     private ArrayList<CatagoryMain> catagoryMainArrayList;
     private CategoryCheck categoryCheck;

    public CategoryAdapter(Context context, ArrayList<CatagoryMain> catagoryMainArrayList, CategoryCheck categoryCheck) {
        this.context = context;
        this.catagoryMainArrayList = catagoryMainArrayList;
        this.categoryCheck = categoryCheck;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.cat_view,parent, false);
        return new viewHolder(view,categoryCheck);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, final int position) {
       holder.bindData(catagoryMainArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return catagoryMainArrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder{
         private CheckBox cat_checkBox;
         private CategoryAdapter.CategoryCheck categoryCheck;
        public viewHolder(@NonNull View itemView,CategoryCheck callBack) {
            super(itemView);

            cat_checkBox=itemView.findViewById(R.id.catName);
            this.categoryCheck=callBack;
        }

        public void bindData(final CatagoryMain catagoryMain){
            cat_checkBox.setText(catagoryMain.getCatagoryName());

            cat_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                    if(categoryCheck!=null){
                        categoryCheck.CategoryCicked(catagoryMain,b);
                    }
                }
            });

            if(catagoryMain.getChecked()){
                cat_checkBox.setChecked(true);
            }
            else{
                catagoryMain.setChecked(false);
            }
        }
    }

    public interface CategoryCheck{

        public void CategoryCicked(CatagoryMain catagoryMain,Boolean value);


    }
}
