package com.digiconvalley.tailordesk.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.Model.TailorOrders.TailorOrders;
import com.digiconvalley.tailordesk.Model.TailorProfileApi.CategoryProfile;
import com.digiconvalley.tailordesk.R;

import java.util.ArrayList;

public class ProfileCategory extends RecyclerView.Adapter<ProfileCategory.ViewHolder> {
    private Context context;
    private ArrayList<CategoryProfile> categoryProfiles;
    private CategoryEdit categoryEdit;

   /* public ProfileCategory(Context context, ArrayList<CategoryProfile> categoryProfiles) {
        this.context = context;
        this.categoryProfiles = categoryProfiles;
    }*/

    public ProfileCategory(Context context, ArrayList<CategoryProfile> categoryProfiles, CategoryEdit categoryEdit) {
        this.context = context;
        this.categoryProfiles = categoryProfiles;
        this.categoryEdit = categoryEdit;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater= LayoutInflater.from(parent.getContext());

        View view=layoutInflater.inflate(R.layout.category_rcv_view,parent, false);
        return new ProfileCategory.ViewHolder(view,categoryEdit);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.onBindData(categoryProfiles.get(position),position);
    }

    @Override
    public int getItemCount() {
        return categoryProfiles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView categoryView;
        private ProfileCategory.CategoryEdit categoryEdit;
        private LinearLayout removeCategory;

        public ViewHolder(@NonNull View itemView,CategoryEdit callBack) {
            super(itemView);
            categoryView=itemView.findViewById(R.id.profileCategoryOne_ed);
            removeCategory=itemView.findViewById(R.id.removecategory);
            categoryEdit=callBack;


            if(categoryEdit!=null){
                removeCategory.setVisibility(View.VISIBLE);
            }
        }

        public void onBindData(final CategoryProfile categoryProfile, final int index){
            if(categoryProfile.getChecked())
            categoryView.setText(categoryProfile.getCatagoryName());

            removeCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(categoryEdit!=null){
                        categoryEdit.removeCategory(categoryProfile,index);
                    }
                }
            });
        }
    }
    public interface CategoryEdit{
        public void removeCategory(CategoryProfile categoryProfile, int index);
    }
}
