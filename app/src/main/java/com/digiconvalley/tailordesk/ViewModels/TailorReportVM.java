package com.digiconvalley.tailordesk.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.digiconvalley.tailordesk.Model.SalesandPackages.ReportModel;
import com.digiconvalley.tailordesk.Repositories.TailorReportRepository;

public class TailorReportVM extends AndroidViewModel {
    LiveData<ReportModel> reportModelClassLiveData;
    TailorReportRepository tailorReportRepository;

    public TailorReportVM(@NonNull Application application) {
        super(application);
        tailorReportRepository = new TailorReportRepository();
    }

    public LiveData<ReportModel> getTailorReportLiveData(String tailorId) {
        reportModelClassLiveData = tailorReportRepository.getTailorReport(tailorId);
        return reportModelClassLiveData;
    }
}
