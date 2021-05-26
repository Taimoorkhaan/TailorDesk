package com.digiconvalley.tailordesk.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.digiconvalley.tailordesk.Model.SalesandPackages.MainPackageData;
import com.digiconvalley.tailordesk.Repositories.MainTailorMonthlyPackageRepository;

public class MainTailorMonthlyPackageVM extends AndroidViewModel {
    LiveData<MainPackageData> mainMonthlyPackageLiveData;
    MainTailorMonthlyPackageRepository monthlyPackageRepository;

    public MainTailorMonthlyPackageVM(@NonNull Application application) {
        super(application);
        monthlyPackageRepository = new MainTailorMonthlyPackageRepository();
    }

    public LiveData<MainPackageData> getTailorMonthlyPackageLiveData() {
        mainMonthlyPackageLiveData = monthlyPackageRepository.getTailorMonthlyPackage();
        return mainMonthlyPackageLiveData;
    }
}
