package com.digiconvalley.tailordesk.Model.SalesandPackages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MainPackageData implements Serializable {

    @SerializedName("mothlyPackages")
    @Expose
    private List<PackagesModel> monthlyPackages = null;
    @SerializedName("yearlyPackages")
    @Expose
    private List<PackagesModel> yearlyPackages = null;

    public List<PackagesModel> getMonthlyPackages() {
        return monthlyPackages;
    }

    public void setMothlyPackages(List<PackagesModel> monthlyPackages) {
        this.monthlyPackages = monthlyPackages;
    }

    public List<PackagesModel> getYearlyPackages() {
        return yearlyPackages;
    }

    public void setYearlyPackages(List<PackagesModel> yearlyPackages) {
        this.yearlyPackages = yearlyPackages;
    }

}
