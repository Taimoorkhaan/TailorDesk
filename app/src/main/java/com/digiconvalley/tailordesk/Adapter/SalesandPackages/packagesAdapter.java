package com.digiconvalley.tailordesk.Adapter.SalesandPackages;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.SalesandPackages.PackagesModel;
import com.digiconvalley.tailordesk.Model.SalesandPackages.SubPackageModel;
import com.digiconvalley.tailordesk.R;

import java.util.List;
import java.util.Objects;

public class packagesAdapter extends RecyclerView.Adapter<packagesAdapter.viewHolder> {

    Context context;
    List<PackagesModel> arrayList;
    SubPackage subPackage;

    public packagesAdapter(Context context, List<PackagesModel> arrayList, SubPackage subPackage) {
        this.context = context;
        this.arrayList = arrayList;
        this.subPackage = subPackage;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.packages_horizontal_view, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        if (arrayList.get(position).getPackageName().equals("Primeum")) {
            holder.best_pick.setVisibility(View.VISIBLE);
        }
        holder.premium.setText(arrayList.get(position).getPackageName());
        holder.message.setText(arrayList.get(position).getPackageDescription());
        holder.text_price.setText("$ " + arrayList.get(position).getPackageAmount());

        if (arrayList.get(position).getCurrentPackage())
            holder.choose.setText("Current");
        else
            holder.choose.setText("Choose");
        holder.choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_confirm);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialog.setCancelable(false);
                dialog.show();
            }
        });

        subPackage.subPackageDetails(arrayList.get(position).getSubPackage(), holder.recyclerView);

        holder.choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tailorShopId = StaticData.tailorShopId;
                Toast.makeText(context, "Id is " + StaticData.tailorShopId, Toast.LENGTH_SHORT).show();
                subPackage.choosePackage(String.valueOf(arrayList.get(position).getPackageId()), tailorShopId, arrayList.get(position), position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView best_pick, premium, message, text_price, choose;
        RecyclerView recyclerView;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            best_pick = itemView.findViewById(R.id.best_pick);
            premium = itemView.findViewById(R.id.premium);
            message = itemView.findViewById(R.id.message);
            text_price = itemView.findViewById(R.id.text_price);
            choose = itemView.findViewById(R.id.choose);
            recyclerView = itemView.findViewById(R.id.recycler_package);

            message.setMovementMethod(new ScrollingMovementMethod());

        }
    }

    public interface SubPackage {
        void subPackageDetails(List<SubPackageModel> subPackageModel, RecyclerView recyclerView);

        void choosePackage(String packageId, String tailorId, PackagesModel packagesModel, int position);

    }
}
