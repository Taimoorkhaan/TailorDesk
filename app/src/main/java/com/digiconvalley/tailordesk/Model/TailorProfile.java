package com.digiconvalley.tailordesk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TailorProfile {
    String tailorName;
    String tailorShopId;
    @SerializedName("tailorID")
    @Expose
    private Integer tailorID;
    @SerializedName("tailorPhoneNo")
    @Expose
    private String tailorPhoneNo;

    public Integer getTailorID() {
        return tailorID;
    }

    public void setTailorID(Integer tailorID) {
        this.tailorID = tailorID;
    }

    public String getTailorPhoneNo() {
        return tailorPhoneNo;
    }

    public void setTailorPhoneNo(String tailorPhoneNo) {
        this.tailorPhoneNo = tailorPhoneNo;
    }

    public String getTailorName() {
        return tailorName;
    }

    public void setTailorName(String tailorName) {
        this.tailorName = tailorName;
    }

    public String getTailorShopId() {
        return tailorShopId;
    }

    public void setTailorShopId(String tailorShopId) {
        this.tailorShopId = tailorShopId;
    }
}
