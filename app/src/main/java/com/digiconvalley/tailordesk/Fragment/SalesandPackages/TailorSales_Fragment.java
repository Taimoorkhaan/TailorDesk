package com.digiconvalley.tailordesk.Fragment.SalesandPackages;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;

import com.digiconvalley.tailordesk.Adapter.ReportAdapter;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.SalesandPackages.MainSalesOrder;
import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.ViewModels.MainTailorSaleOrderVM;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class TailorSales_Fragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ReportAdapter reportAdapter;
    private TextView price;

    MainTailorSaleOrderVM tailorSaleOrderVM;
    List<MainSalesOrder> mainSalesOrders;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_tailor__sales_, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager = view.findViewById(R.id.view_pager);
        tabLayout = view.findViewById(R.id.tab_layout);
        price = view.findViewById(R.id.price);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait.......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        setObserver();


    }

    private void setObserver() {
        tailorSaleOrderVM = new ViewModelProvider(getActivity()).get(MainTailorSaleOrderVM.class);

        tailorSaleOrderVM.getTailorSaleOrderLiveData(StaticData.tailorShopId).observe(getActivity(), new Observer<List<MainSalesOrder>>() {
            @Override
            public void onChanged(List<MainSalesOrder> mainSalesOrder) {
                progressDialog.dismiss();
                if (mainSalesOrder != null) {
                    mainSalesOrders =  mainSalesOrder;

                    setViewPager();
                }
            }
        });
    }

    private void setViewPager() {
        reportAdapter = new ReportAdapter(getChildFragmentManager());
        reportAdapter.addFragment(new FragmentNewOrder(), "New Orders (17)", mainSalesOrders);
        reportAdapter.addFragment(new FragmentDeliveryOrder(), "Delivered Orders", mainSalesOrders);

        viewPager.setAdapter(reportAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }
}
