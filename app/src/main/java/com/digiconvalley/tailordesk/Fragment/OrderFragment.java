package com.digiconvalley.tailordesk.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
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
import com.digiconvalley.tailordesk.R;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class OrderFragment extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem itemactive,itempostdue,itemupcoming;


    PagerAdapter pagerAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_orders, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = view.findViewById(R.id.tablayout);
        itemactive = view.findViewById(R.id.tabactive);
        itempostdue = view.findViewById(R.id.tabpastdue);
        itemupcoming = view.findViewById(R.id.tabupcoming);


        viewPager = view.findViewById(R.id.tabpager);

        assert getFragmentManager() != null;
        pagerAdapter = new PagerAdapter(getChildFragmentManager());
        pagerAdapter.addFragment(new ActiveFragment(), "ACTIVE");
        pagerAdapter.addFragment(new PastDueFragment(), "Past DUE");
        pagerAdapter.addFragment(new UpComingFragment(), "UPCOMING");
        viewPager.setAdapter(pagerAdapter);

        tabLayout.setupWithViewPager(viewPager);
       /* tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition()==0 || tab.getPosition()==1 || tab.getPosition()==2){
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));*/
    }

    private Bitmap addWaterMark(Bitmap src) {
        int w = src.getWidth();
        int h = src.getHeight();
        Bitmap result = Bitmap.createBitmap(w, h, src.getConfig());
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(src, 0, 0, null);

        Bitmap waterMark = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.tailor_app_logo);
        canvas.drawBitmap(waterMark, 0, 0, null);

        return result;
    }

    @Override
    public void onStart() {
        super.onStart();
        MainHome_Activity.toolBarImg.setVisibility(View.VISIBLE);
        MainHome_Activity.toolBarText.setVisibility(View.GONE);

    }
}
