package com.digiconvalley.tailordesk.Model.TailorOrders;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SuitMeasurement implements Serializable {
    @SerializedName("suitMeasurementId")
    @Expose
    private Integer suitMeasurementId;
    @SerializedName("servicePartId")
    @Expose
    private Integer servicePartId;
    @SerializedName("servicePartIcon")
    @Expose
    private String servicePartIcon;
    @SerializedName("servicePartName")
    @Expose
    private String servicePartName;
    @SerializedName("suitMeasurementValue")
    @Expose
    private String suitMeasurementValue;
    @SerializedName("itemNumber")
    @Expose
    private String itemNumber;

    public Integer getSuitMeasurementId() {
        return suitMeasurementId;
    }

    public void setSuitMeasurementId(Integer suitMeasurementId) {
        this.suitMeasurementId = suitMeasurementId;
    }

    public Integer getServicePartId() {
        return servicePartId;
    }

    public void setServicePartId(Integer servicePartId) {
        this.servicePartId = servicePartId;
    }

    public String getServicePartIcon() {
        return servicePartIcon;
    }

    public void setServicePartIcon(String servicePartIcon) {
        this.servicePartIcon = servicePartIcon;
    }

    public String getServicePartName() {
        return servicePartName;
    }

    public void setServicePartName(String servicePartName) {
        this.servicePartName = servicePartName;
    }

    public String getSuitMeasurementValue() {
        return suitMeasurementValue;
    }

    public void setSuitMeasurementValue(String suitMeasurementValue) {
        this.suitMeasurementValue = suitMeasurementValue;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    @Override
    public String toString() {
        return servicePartName +" = "+suitMeasurementValue;
    }
}
