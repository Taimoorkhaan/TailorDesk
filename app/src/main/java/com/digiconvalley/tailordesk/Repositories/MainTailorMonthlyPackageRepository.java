package com.digiconvalley.tailordesk.Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.digiconvalley.tailordesk.Api.ApiClient;
import com.digiconvalley.tailordesk.Model.SalesandPackages.MainPackageData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainTailorMonthlyPackageRepository {
    MutableLiveData<MainPackageData> monthlyPackageMutableLiveData;
    ApiClient apiClient;


    public MainTailorMonthlyPackageRepository() {
        monthlyPackageMutableLiveData = new MutableLiveData<>();
    }

    public void getTailorData() {
        apiClient = ApiClient.getInstance();

        apiClient.getMonthlyPackageModel(new Callback<MainPackageData>() {
            @Override
            public void onResponse(Call<MainPackageData> call, Response<MainPackageData> response) {
                monthlyPackageMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<MainPackageData> call, Throwable t) {
                monthlyPackageMutableLiveData.postValue(null);
                Log.e("mainData", t.getMessage());
            }
        });
    }

    public LiveData<MainPackageData> getTailorMonthlyPackage() {
        getTailorData();
        return monthlyPackageMutableLiveData;
    }
}
