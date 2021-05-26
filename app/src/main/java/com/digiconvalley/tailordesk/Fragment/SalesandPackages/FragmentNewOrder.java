package com.digiconvalley.tailordesk.Fragment.SalesandPackages;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.digiconvalley.tailordesk.Adapter.SalesandPackages.NewOrderAdapter;
import com.digiconvalley.tailordesk.Model.SalesandPackages.MainSalesOrder;
import com.digiconvalley.tailordesk.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentNewOrder extends Fragment {

    RecyclerView recyclerView;
    List<MainSalesOrder> list = new ArrayList<>();
    NewOrderAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_order, null);

        if (getArguments() != null) {
            list = (List<MainSalesOrder>) getArguments().getSerializable("MyOrder");
           // Log.e("MainData", list.get(0).getOrderNo());
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        adapter = new NewOrderAdapter(getContext(), list);
        recyclerView.setAdapter(adapter);

        return view;
    }
}
