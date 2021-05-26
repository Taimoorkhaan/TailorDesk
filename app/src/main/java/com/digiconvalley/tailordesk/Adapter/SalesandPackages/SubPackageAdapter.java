package com.digiconvalley.tailordesk.Adapter.SalesandPackages;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.Model.SalesandPackages.SubPackageModel;
import com.digiconvalley.tailordesk.R;

import java.util.List;

public class SubPackageAdapter extends RecyclerView.Adapter<SubPackageAdapter.ViewHolder> {
    Context context;
    List<SubPackageModel> subPackageModels;

    public SubPackageAdapter(Context context, List<SubPackageModel> subPackageModels) {
        this.context = context;
        this.subPackageModels = subPackageModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.subpackage_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.details.setText(subPackageModels.get(position).getSubPackageName());

    }

    @Override
    public int getItemCount() {
        return subPackageModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView details;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            details = itemView.findViewById(R.id.details);
        }
    }
}
