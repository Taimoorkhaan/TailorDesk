package com.digiconvalley.tailordesk.Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.digiconvalley.tailordesk.Api.ApiClient;
import com.digiconvalley.tailordesk.Model.SalesandPackages.MainDuePayment;
import com.digiconvalley.tailordesk.Model.SalesandPackages.MainSalesOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainTailorDuePaymentRepository {
    MutableLiveData<List<MainDuePayment>> duePaymentMutableLiveData;
    ApiClient apiClient;


    public MainTailorDuePaymentRepository() {
        duePaymentMutableLiveData = new MutableLiveData<>();
    }

    public void getTailorData(String tailorId) {
        apiClient = ApiClient.getInstance();

        apiClient.getTailorDuePaymentModel(tailorId, new Callback<List<MainDuePayment>>() {
            @Override
            public void onResponse(Call<List<MainDuePayment>> call, Response<List<MainDuePayment>> response) {
                duePaymentMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<MainDuePayment>> call, Throwable t) {
                duePaymentMutableLiveData.postValue(null);
                Log.e("mainData", t.getMessage());
            }
        });
    }

    public LiveData<List<MainDuePayment>> getTailorDuePayment(String tailorId) {
        getTailorData(tailorId);
        return duePaymentMutableLiveData;
    }
}
