package com.digiconvalley.tailordesk.Model.TailorProfileApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TailorProfileGet implements Serializable {
    private String shopLogoImage=null;
    private String shopLogoImageName=null;
    @SerializedName("tailorID")
    @Expose
    private Integer tailorID;
    @SerializedName("tailorName")
    @Expose
    private String tailorName;
    @SerializedName("tailorPhoneNo")
    @Expose
    private String tailorPhoneNo;
    @SerializedName("tailorEmail")
    @Expose
    private String tailorEmail;
    @SerializedName("tailorshopData")
    @Expose
    private TailorshopDataProfile tailorshopData;

    public Integer getTailorID() {
        return tailorID;
    }

    public void setTailorID(Integer tailorID) {
        this.tailorID = tailorID;
    }

    public String getTailorName() {
        return tailorName;
    }

    public void setTailorName(String tailorName) {
        this.tailorName = tailorName;
    }

    public String getTailorPhoneNo() {
        return tailorPhoneNo;
    }

    public void setTailorPhoneNo(String tailorPhoneNo) {
        this.tailorPhoneNo = tailorPhoneNo;
    }

    public String getTailorEmail() {
        return tailorEmail;
    }

    public void setTailorEmail(String tailorEmail) {
        this.tailorEmail = tailorEmail;
    }

    public TailorshopDataProfile getTailorshopData() {
        return tailorshopData;
    }

    public void setTailorshopData(TailorshopDataProfile tailorshopData) {
        this.tailorshopData = tailorshopData;
    }
    public String getShopLogoImage() {
        return shopLogoImage;
    }

    public void setShopLogoImage(String shopLogoImage) {
        this.shopLogoImage = shopLogoImage;
    }

    public String getShopLogoImageName() {
        return shopLogoImageName;
    }

    public void setShopLogoImageName(String shopLogoImageName) {
        this.shopLogoImageName = shopLogoImageName;
    }
}
