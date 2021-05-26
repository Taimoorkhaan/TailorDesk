package com.digiconvalley.tailordesk.Adapter.SalesandPackages;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.digiconvalley.tailordesk.Fragment.SalesandPackages.FragmentDeliveryOrder;
import com.digiconvalley.tailordesk.Fragment.SalesandPackages.FragmentNewOrder;


public class SalesPagerAdapter extends FragmentStateAdapter {
    public SalesPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
       switch (position){
           case 0:
               return new FragmentNewOrder();
           default:
               return new FragmentDeliveryOrder();

       }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
