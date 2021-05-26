package com.digiconvalley.tailordesk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ServicePart implements Serializable {
    private String measurementValue=null;
    private int measurementid=0;
    @SerializedName("serviceId")
    @Expose
    private Integer serviceId;
    @SerializedName("servicePartId")
    @Expose
    private Integer servicePartId;
    @SerializedName("servicePartName")
    @Expose
    private String servicePartName;
    @SerializedName("servicePartIcon")
    @Expose
    private String servicePartIcon;

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getServicePartId() {
        return servicePartId;
    }

    public void setServicePartId(Integer servicePartId) {
        this.servicePartId = servicePartId;
    }

    public String getServicePartName() {
        return servicePartName;
    }

    public void setServicePartName(String servicePartName) {
        this.servicePartName = servicePartName;
    }

    public String getServicePartIcon() {
        return servicePartIcon;
    }

    public void setServicePartIcon(String servicePartIcon) {
        this.servicePartIcon = servicePartIcon;
    }

    public String getMeasurementValue() {
        return measurementValue;
    }

    public void setMeasurementValue(String measurementValue) {
        this.measurementValue = measurementValue;
    }

    public int getMeasurementid() {
        return measurementid;
    }

    public void setMeasurementid(int measurementid) {
        this.measurementid = measurementid;
    }

    @Override
    public String toString() {
            return servicePartName + "=" + measurementValue;

    }
}
