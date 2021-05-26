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

import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.SalesandPackages.ReportModel;
import com.digiconvalley.tailordesk.R;
import com.digiconvalley.tailordesk.ViewModels.TailorReportVM;

import java.util.ArrayList;
import java.util.List;

public class TailorShopReport_Fragment extends Fragment {

    TextView total_order_received, total_sale_received, total_advance_received, not_assigned, cutting, stitching, completed;

    TailorReportVM tailorReportVM;
    List<ReportModel> reportModels = new ArrayList<>();
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tailororder_report_fragment, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        completed = view.findViewById(R.id.completed);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please Wait.......");
        progressDialog.setCancelable(true);
        progressDialog.show();


        total_order_received = view.findViewById(R.id.total_order_received);
        total_sale_received = view.findViewById(R.id.total_sale_received);
        total_advance_received = view.findViewById(R.id.total_advance_received);
        not_assigned = view.findViewById(R.id.not_assigned);
        cutting = view.findViewById(R.id.cutting);
        stitching = view.findViewById(R.id.stitching);

        setObserver();
    }

    private void setObserver() {
        tailorReportVM = new ViewModelProvider(getActivity()).get(TailorReportVM.class);
        tailorReportVM.getTailorReportLiveData(StaticData.tailorShopId).observe(getActivity(), new Observer<ReportModel>() {
            @Override
            public void onChanged(ReportModel reportModel) {
              //  Toast.makeText(getContext(), "Data = "+reportModelClass.getNotAssigned(), Toast.LENGTH_SHORT).show();
                if (reportModel != null) {

                    reportModels.add(reportModel);
                    progressDialog.dismiss();
                    setReportData();
                }else
                    progressDialog.dismiss();
            }
        });
    }

    private void setReportData() {
        total_order_received.setText(String.valueOf(reportModels.get(0).getTotalOrer()));
        total_sale_received.setText(String.valueOf(reportModels.get(0).getTotalSale()));
        total_advance_received.setText(String.valueOf(reportModels.get(0).getTotalAdvance()));
        not_assigned.setText(String.valueOf(reportModels.get(0).getNotAssigned()));
        cutting.setText(String.valueOf(reportModels.get(0).getCutting()));
        stitching.setText(String.valueOf(reportModels.get(0).getStitching()));
        completed.setText(String.valueOf(reportModels.get(0).getCompleted()));
    }
}
