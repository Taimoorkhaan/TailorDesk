package com.digiconvalley.tailordesk.Fragment.SalesandPackages;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.digiconvalley.tailordesk.Adapter.SalesandPackages.DuePaymentAdapter;
import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.SalesandPackages.MainDuePayment;
import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.ViewModels.MainTailorDuePaymentVM;

import java.util.ArrayList;
import java.util.List;

public class TailorDuePayment_Fragment extends Fragment {

    private RecyclerView recyclerView;
//    private List<MainDuePayment> list = new ArrayList<>();
    private RecyclerView.Adapter adapter;

    MainTailorDuePaymentVM tailorDuePaymentVM;
    List<MainDuePayment> mainDuePayment;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tailordue_payment_fragment,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView =view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait.......");
        progressDialog.setCancelable(false);
        progressDialog.show();

        setObserver();

    }

    private void setObserver() {
        tailorDuePaymentVM = new ViewModelProvider(getActivity()).get(MainTailorDuePaymentVM.class);
        tailorDuePaymentVM.getTailorDuePaymentLiveData(StaticData.tailorShopId).observe(getActivity(), new Observer<List<MainDuePayment>>() {
            @Override
            public void onChanged(List<MainDuePayment> mainDuePayments) {
                progressDialog.dismiss();
                if (mainDuePayments !=null){
                    mainDuePayment = mainDuePayments;

                    setDuePayment();
                }
            }
        });

    }

    private void setDuePayment() {

        adapter= new DuePaymentAdapter(getContext(),mainDuePayment);
        recyclerView.setAdapter(adapter);
    }
}
