package com.digiconvalley.tailordesk.Repositories;


import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.digiconvalley.tailordesk.Api.ApiClient;
import com.digiconvalley.tailordesk.Model.SalesandPackages.MainSalesOrder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TailorSaleOrderRepository {
    MutableLiveData<List<MainSalesOrder>> salesOrderMutableLiveData;
    ApiClient apiClient;


    public TailorSaleOrderRepository() {
        salesOrderMutableLiveData = new MutableLiveData<>();
    }

    public void getTailorData(String tailorId) {
        apiClient = ApiClient.getInstance();

        apiClient.getTailorSalesModel(tailorId, new Callback<List<MainSalesOrder>>() {
            @Override
            public void onResponse(Call<List<MainSalesOrder>> call, Response<List<MainSalesOrder>> response) {
                salesOrderMutableLiveData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<MainSalesOrder>> call, Throwable t) {
                salesOrderMutableLiveData.postValue(null);
                Log.e("mainData", t.getMessage());
            }
        });
    }

    public LiveData<List<MainSalesOrder>> getTailorSaleNewOrder(String tailorId) {
        getTailorData(tailorId);
        return salesOrderMutableLiveData;
    }
}
