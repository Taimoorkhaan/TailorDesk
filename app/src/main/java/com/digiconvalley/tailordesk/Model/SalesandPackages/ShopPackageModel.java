package com.digiconvalley.tailordesk.Model.SalesandPackages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShopPackageModel implements Serializable {
    @SerializedName("shopPackageId")
    @Expose
    private Integer shopPackageId;
    @SerializedName("packageId")
    @Expose
    private Integer packageId;
    @SerializedName("packages")
    @Expose
    private Object packages;
    @SerializedName("tailorShopId")
    @Expose
    private Integer tailorShopId;
    @SerializedName("tailorShops")
    @Expose
    private Object tailorShops;
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("date")
    @Expose
    private String date;

    public Integer getShopPackageId() {
        return shopPackageId;
    }

    public void setShopPackageId(Integer shopPackageId) {
        this.shopPackageId = shopPackageId;
    }

    public Integer getPackageId() {
        return packageId;
    }

    public void setPackageId(Integer packageId) {
        this.packageId = packageId;
    }

    public Object getPackages() {
        return packages;
    }

    public void setPackages(Object packages) {
        this.packages = packages;
    }

    public Integer getTailorShopId() {
        return tailorShopId;
    }

    public void setTailorShopId(Integer tailorShopId) {
        this.tailorShopId = tailorShopId;
    }

    public Object getTailorShops() {
        return tailorShops;
    }

    public void setTailorShops(Object tailorShops) {
        this.tailorShops = tailorShops;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
