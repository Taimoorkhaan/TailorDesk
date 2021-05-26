package com.digiconvalley.tailordesk.Adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.digiconvalley.tailordesk.Model.SalesandPackages.MainSalesOrder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReportAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> mFragmentList = new ArrayList<>();
    private ArrayList<String> mFragmentTitleList = new ArrayList<>();

    public ReportAdapter(@NonNull FragmentManager fm) {
        super(fm);


    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    public void addFragment(Fragment fragment, String title, List<MainSalesOrder> mainSalesOrders) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("MyOrder", (Serializable) mainSalesOrders);
        fragment.setArguments(bundle);
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
