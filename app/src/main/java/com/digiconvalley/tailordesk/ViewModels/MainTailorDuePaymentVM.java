package com.digiconvalley.tailordesk.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.digiconvalley.tailordesk.Model.SalesandPackages.MainDuePayment;
import com.digiconvalley.tailordesk.Repositories.MainTailorDuePaymentRepository;

import java.util.List;

public class MainTailorDuePaymentVM extends AndroidViewModel {
    LiveData<List<MainDuePayment>> mainDuePaymentLiveData;
    MainTailorDuePaymentRepository tailorSaleOrderRepository;

    public MainTailorDuePaymentVM(@NonNull Application application) {
        super(application);
        tailorSaleOrderRepository = new MainTailorDuePaymentRepository();
    }
    public LiveData<List<MainDuePayment>> getTailorDuePaymentLiveData(String tailorId) {
        mainDuePaymentLiveData = tailorSaleOrderRepository.getTailorDuePayment(tailorId);
        return mainDuePaymentLiveData;
    }
}
