package com.digiconvalley.tailordesk.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;


public class PagerAdapter extends FragmentPagerAdapter {

    private int numoftabs;
    private ArrayList<Fragment> mFragmentList =new ArrayList<>();
    private ArrayList<String> mFragmentTitleList =new ArrayList<>();

    public PagerAdapter(@NonNull FragmentManager fm) {
        super(fm);


    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
      return mFragmentList.get(position);
    }

    public void addFragment(Fragment fragment, String title) {
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
