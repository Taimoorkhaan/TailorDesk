package com.digiconvalley.tailordesk.Model.TailorProfileApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ShopAddressProfile implements Serializable {
    @SerializedName("shopAddressId")
    @Expose
    private Integer shopAddressId;
    @SerializedName("shopAddressCityName")
    @Expose
    private String shopAddressCityName;
    @SerializedName("shopAddressCountryName")
    @Expose
    private String shopAddressCountryName;
    @SerializedName("xcoordinate")
    @Expose
    private Double xcoordinate;
    @SerializedName("ycoordinate")
    @Expose
    private Double ycoordinate;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("shopPinLocation")
    @Expose
    private String shopPinLocation;

    public Integer getShopAddressId() {
        return shopAddressId;
    }

    public void setShopAddressId(Integer shopAddressId) {
        this.shopAddressId = shopAddressId;
    }

    public String getShopAddressCityName() {
        return shopAddressCityName;
    }

    public void setShopAddressCityName(String shopAddressCityName) {
        this.shopAddressCityName = shopAddressCityName;
    }

    public String getShopAddressCountryName() {
        return shopAddressCountryName;
    }

    public void setShopAddressCountryName(String shopAddressCountryName) {
        this.shopAddressCountryName = shopAddressCountryName;
    }

    public Double getXcoordinate() {
        return xcoordinate;
    }

    public void setXcoordinate(Double xcoordinate) {
        this.xcoordinate = xcoordinate;
    }

    public Double getYcoordinate() {
        return ycoordinate;
    }

    public void setYcoordinate(Double ycoordinate) {
        this.ycoordinate = ycoordinate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShopPinLocation() {
        return shopPinLocation;
    }

    public void setShopPinLocation(String shopPinLocation) {
        this.shopPinLocation = shopPinLocation;
    }

}
