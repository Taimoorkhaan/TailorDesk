package com.digiconvalley.tailordesk.Activities.SalesAndPackages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;

import com.digiconvalley.tailordesk.Adapter.SalesandPackages.SubPackageAdapter;
import com.digiconvalley.tailordesk.Adapter.SalesandPackages.packagesAdapter;
import com.digiconvalley.tailordesk.Api.ApiClient;
import com.digiconvalley.tailordesk.Model.SalesandPackages.MainPackageData;
import com.digiconvalley.tailordesk.Model.SalesandPackages.PackagesModel;
import com.digiconvalley.tailordesk.Model.SalesandPackages.ShopPackageModel;
import com.digiconvalley.tailordesk.Model.SalesandPackages.SubPackageModel;
import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.ViewModels.MainTailorMonthlyPackageVM;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TailorShopPackages_Activity extends AppCompatActivity {

    RecyclerView recyclerview;
    List<PackagesModel> list = new ArrayList<>();
    packagesAdapter adapter;
    ImageView backPackage;
    SwitchCompat switchCompat;
    ProgressDialog progressDialog;

    //TODO: SubPackage
    SubPackageAdapter subPackageAdapter;
    List<SubPackageModel> subPackageList = new ArrayList<>();

    MainTailorMonthlyPackageVM tailorMonthlyPackageVM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_shop_packages_);

        recyclerview = findViewById(R.id.recyclerview);
        backPackage = findViewById(R.id.back_button_Packages);
        switchCompat = findViewById(R.id.switch_compat);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait.......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        setObserver();

        backPackage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setObserver() {
        tailorMonthlyPackageVM = new ViewModelProvider(this).get(MainTailorMonthlyPackageVM.class);
        tailorMonthlyPackageVM.getTailorMonthlyPackageLiveData().observe(this, new Observer<MainPackageData>() {
            @Override
            public void onChanged(MainPackageData mainPackageData) {
                if (mainPackageData != null) {

                    list.clear();
                    list.addAll(mainPackageData.getMonthlyPackages());

                    switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                            if (isChecked) {
                                list.clear();
                                list.addAll(mainPackageData.getYearlyPackages());
                                adapter.notifyDataSetChanged();
                            } else {
                                list.clear();
                                list.addAll(mainPackageData.getMonthlyPackages());
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                    progressDialog.dismiss();

                    setMonthlyPackage();
                }
            }
        });
    }

    private void setMonthlyPackage() {
        adapter = new packagesAdapter(this,
                list, new packagesAdapter.SubPackage() {
            @Override
            public void subPackageDetails(List<SubPackageModel> subPackageModel, RecyclerView recyclerView) {
                subPackageList.addAll(subPackageModel);
                subPackageAdapter = new SubPackageAdapter(TailorShopPackages_Activity.this, subPackageList);
                recyclerView.setLayoutManager(new LinearLayoutManager(TailorShopPackages_Activity.this));
                recyclerView.setAdapter(subPackageAdapter);
            }

            @Override
            public void choosePackage(String packageId, String tailorId, PackagesModel packagesModel, int position) {
                sendChangeRequest(packageId, tailorId, packagesModel, position);
            }
        });
        recyclerview.setAdapter(adapter);
    }

    private void sendChangeRequest(String packageId, String tailorId, PackagesModel packagesModel, int position) {
        ApiClient.getInstance().getShopPackage(packageId, tailorId, new Callback<ShopPackageModel>() {
            @Override
            public void onResponse(Call<ShopPackageModel> call, Response<ShopPackageModel> response) {

                if (response.body() != null) {

                    list.get(position).setCurrentPackage(true);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ShopPackageModel> call, Throwable t) {

            }
        });
    }
}