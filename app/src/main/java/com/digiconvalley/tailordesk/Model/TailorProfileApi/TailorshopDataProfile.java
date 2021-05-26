package com.digiconvalley.tailordesk.Model.TailorProfileApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TailorshopDataProfile implements Serializable {

    @SerializedName("tailorShopId")
    @Expose
    private Integer tailorShopId;
    @SerializedName("tailorShopName")
    @Expose
    private String tailorShopName;
    @SerializedName("shopAddress")
    @Expose
    private ShopAddressProfile shopAddress;
    @SerializedName("tailorShopLogo")
    @Expose
    private String tailorShopLogo;
    public Integer getTailorShopId() {
        return tailorShopId;
    }

    public void setTailorShopId(Integer tailorShopId) {
        this.tailorShopId = tailorShopId;
    }

    public String getTailorShopName() {
        return tailorShopName;
    }

    public void setTailorShopName(String tailorShopName) {
        this.tailorShopName = tailorShopName;
    }

    public ShopAddressProfile getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(ShopAddressProfile shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getTailorShopLogo() {
        return tailorShopLogo;
    }

    public void setTailorShopLogo(String tailorShopLogo) {
        this.tailorShopLogo = tailorShopLogo;
    }
}
