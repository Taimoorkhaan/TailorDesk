package com.digiconvalley.tailordesk.Model.TailorProfileApi;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CategoryProfile implements Serializable {
    private Boolean checked=true;
    @SerializedName("tailorShopId")
    @Expose
    private Integer tailorShopId;
    @SerializedName("catagoryId")
    @Expose
    private Integer catagoryId;
    @SerializedName("shopServiceId")
    @Expose
    private Integer shopServiceId;
    @SerializedName("catagoryName")
    @Expose
    private String catagoryName;

    public CategoryProfile(Integer catagoryId, String catagoryName) {
        this.catagoryId = catagoryId;
        this.catagoryName = catagoryName;
    }

    public Integer getTailorShopId() {
        return tailorShopId;
    }

    public void setTailorShopId(Integer tailorShopId) {
        this.tailorShopId = tailorShopId;
    }

    public Integer getCatagoryId() {
        return catagoryId;
    }

    public void setCatagoryId(Integer catagoryId) {
        this.catagoryId = catagoryId;
    }

    public Integer getShopServiceId() {
        return shopServiceId;
    }

    public void setShopServiceId(Integer shopServiceId) {
        this.shopServiceId = shopServiceId;
    }

    public String getCatagoryName() {
        return catagoryName;
    }

    public void setCatagoryName(String catagoryName) {
        this.catagoryName = catagoryName;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
