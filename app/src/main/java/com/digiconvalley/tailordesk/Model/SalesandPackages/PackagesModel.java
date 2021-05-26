package com.digiconvalley.tailordesk.Model.SalesandPackages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PackagesModel implements Serializable {

    @SerializedName("packageId")
    @Expose
    private Integer packageId;
    @SerializedName("packageName")
    @Expose
    private String packageName;
    @SerializedName("packageTitle")
    @Expose
    private String packageTitle;
    @SerializedName("packageAmount")
    @Expose
    private Integer packageAmount;
    @SerializedName("packageDuration")
    @Expose
    private String packageDuration;
    @SerializedName("packageSubscriptionType")
    @Expose
    private String packageSubscriptionType;
    @SerializedName("packageDescription")
    @Expose
    private String packageDescription;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("subPackage")
    @Expose
    private List<SubPackageModel> subPackage = null;
    @SerializedName("currentPackage")
    @Expose
    private Boolean currentPackage;

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageTitle() {
        return packageTitle;
    }

    public void setPackageTitle(String packageTitle) {
        this.packageTitle = packageTitle;
    }

    public Integer getPackageAmount() {
        return packageAmount;
    }

    public void setPackageAmount(Integer packageAmount) {
        this.packageAmount = packageAmount;
    }

    public String getPackageDuration() {
        return packageDuration;
    }

    public void setPackageDuration(String packageDuration) {
        this.packageDuration = packageDuration;
    }

    public String getPackageSubscriptionType() {
        return packageSubscriptionType;
    }

    public void setPackageSubscriptionType(String packageSubscriptionType) {
        this.packageSubscriptionType = packageSubscriptionType;
    }

    public String getPackageDescription() {
        return packageDescription;
    }

    public void setPackageDescription(String packageDescription) {
        this.packageDescription = packageDescription;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<SubPackageModel> getSubPackage() {
        return subPackage;
    }

    public void setSubPackage(List<SubPackageModel> subPackage) {
        this.subPackage = subPackage;
    }

    public Boolean getCurrentPackage() {
        return currentPackage;
    }

    public void setCurrentPackage(Boolean currentPackage) {
        this.currentPackage = currentPackage;
    }

}
