package com.digiconvalley.tailordesk.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.Activities.PastDua_OrderDetails_Activity;
import com.digiconvalley.tailordesk.Activities.TailorOrder_Detail_Activity;
import com.digiconvalley.tailordesk.Adapter.TailorAlbumsAdapter;
import com.digiconvalley.tailordesk.Adapter.TailorOrdersAdapter;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.TailorOrders.TailorOrders;
import com.digiconvalley.tailordesk.Model.TailorProfile;
import com.digiconvalley.tailordesk.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class PastDueFragment extends Fragment implements TailorOrdersAdapter.OrderStatus {
    private RecyclerView tailorOrders_Rcv;
    private LinearLayout pastRcvLayout;

    private TailorProfile tailorProfile;
    private ArrayList<TailorOrders> tailorOrders,tailorOrders2;
    public static TailorOrdersAdapter tailorOrdersAdapter;
    private ImageView threeDot;

    private SearchView searchView;
    private TextView pastDueText;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_past_due, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tailorOrders_Rcv=view.findViewById(R.id.tailor_customer_order);
        searchView=view.findViewById(R.id.searchView);
        pastRcvLayout=view.findViewById(R.id.pastRcvLayout);
        pastDueText=view.findViewById(R.id.pastLayoutText);


        tailorOrders_Rcv.setLayoutManager(new LinearLayoutManager(getContext()));
        tailorOrdersAdapter = new TailorOrdersAdapter(getContext(), StaticData.tailorPastDuaOrders, PastDueFragment.this, true);
        tailorOrders_Rcv.setAdapter(tailorOrdersAdapter);

     /*   if(StaticData.tailorPastDuaOrders.size()<1){
            pastRcvLayout.setVisibility(View.GONE);
            pastDueText.setVisibility(View.VISIBLE);
            pastDueText.setText("NO ORDER IN LIST");
        }
        else {
            pastRcvLayout.setVisibility(View.VISIBLE);
            pastDueText.setVisibility(View.GONE);
            tailorOrders_Rcv.setLayoutManager(new LinearLayoutManager(getContext()));
            tailorOrdersAdapter = new TailorOrdersAdapter(getContext(), StaticData.tailorPastDuaOrders, PastDueFragment.this, true);
            tailorOrders_Rcv.setAdapter(tailorOrdersAdapter);
        }*/

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(StaticData.tailorPastDuaOrders!=null)
                    searchOrders(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(StaticData.tailorPastDuaOrders!=null)
                    searchOrders(newText);
                return false;
            }
        });
    }
    public void searchOrders(String data){
        tailorOrders2=new ArrayList<>();

        if(StaticData.tailorPastDuaOrders!=null) {
            for (TailorOrders tailorOrders : StaticData.tailorPastDuaOrders) {
                if(tailorOrders.getOrderSuit().size()>0) {
                    if (tailorOrders.getOrderSuit().get(0).getCustomerName().toLowerCase().contains(data.toLowerCase())) {
                        tailorOrders2.add(tailorOrders);
                    }
                }
            }

            tailorOrdersAdapter = new TailorOrdersAdapter(getContext(), tailorOrders2, PastDueFragment.this, true);
            tailorOrders_Rcv.setAdapter(tailorOrdersAdapter);
        }
    }

    @Override
    public void changeStatus(TailorOrders tailorOrders, int index) {

    }

    @Override
    public void orderClick(TailorOrders tailorOrders) {

        Intent intent=new Intent(getContext(), PastDua_OrderDetails_Activity.class);
        //  intent.putExtra("OrderDetails",tailorOrders.getOrderSuit());
        intent.putExtra("OrderDetails", tailorOrders);

        startActivity(intent);
    }
}