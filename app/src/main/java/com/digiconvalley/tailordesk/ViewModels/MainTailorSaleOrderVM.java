package com.digiconvalley.tailordesk.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.digiconvalley.tailordesk.Model.SalesandPackages.MainSalesOrder;
import com.digiconvalley.tailordesk.Repositories.TailorSaleOrderRepository;

import java.util.List;

public class MainTailorSaleOrderVM extends AndroidViewModel {
    LiveData<List<MainSalesOrder>> mainSalesOrderLiveData;
    TailorSaleOrderRepository tailorSaleOrderRepository;

    public MainTailorSaleOrderVM(@NonNull Application application) {
        super(application);
        tailorSaleOrderRepository = new TailorSaleOrderRepository();
    }

    public LiveData<List<MainSalesOrder>> getTailorSaleOrderLiveData(String tailorId) {
        mainSalesOrderLiveData = tailorSaleOrderRepository.getTailorSaleNewOrder(tailorId);
        return mainSalesOrderLiveData;
    }
}
