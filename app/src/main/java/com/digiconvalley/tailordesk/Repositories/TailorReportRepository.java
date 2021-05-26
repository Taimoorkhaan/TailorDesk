package com.digiconvalley.tailordesk.Repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.digiconvalley.tailordesk.Api.ApiClient;
import com.digiconvalley.tailordesk.Model.SalesandPackages.ReportModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TailorReportRepository {
    MutableLiveData<ReportModel> reportMutableLiveData;
    ApiClient apiClient;

    public TailorReportRepository() {
        reportMutableLiveData = new MutableLiveData<>();
    }

    public void getTailorData(String tailorId) {
        apiClient = ApiClient.getInstance();

        apiClient.getTailorReport(tailorId, new Callback<ReportModel>() {
            @Override
            public void onResponse(Call<ReportModel> call, Response<ReportModel> response) {
                reportMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<ReportModel> call, Throwable t) {
                reportMutableLiveData.postValue(null);

            }
        });
    }
    public LiveData<ReportModel> getTailorReport(String tailorId) {
        getTailorData(tailorId);
        return reportMutableLiveData;
    }
}
