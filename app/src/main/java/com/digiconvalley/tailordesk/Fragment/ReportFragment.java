package com.digiconvalley.tailordesk.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.digiconvalley.tailordesk.Activities.MainHome_Activity;
import com.digiconvalley.tailordesk.Adapter.PagerAdapter;
import com.digiconvalley.tailordesk.Fragment.SalesandPackages.TailorDuePayment_Fragment;
import com.digiconvalley.tailordesk.Fragment.SalesandPackages.TailorSales_Fragment;
import com.digiconvalley.tailordesk.Fragment.SalesandPackages.TailorShopReport_Fragment;
import com.digiconvalley.tailordesk.R;
import com.google.android.material.tabs.TabLayout;

public class ReportFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;

    public ReportFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_report, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout=view.findViewById(R.id.tab_layout);
        viewPager=view.findViewById(R.id.view_pager);

        pagerAdapter = new PagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new TailorShopReport_Fragment(), "DashBoard");
        pagerAdapter.addFragment(new TailorSales_Fragment(), "Sales");
        pagerAdapter.addFragment(new TailorDuePayment_Fragment(), "Due Payments");
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void onStart() {
        super.onStart();
        MainHome_Activity.toolBarImg.setVisibility(View.GONE);
        MainHome_Activity.toolBarText.setVisibility(View.VISIBLE);
        MainHome_Activity.toolBarText.setText("Report");
    }
}