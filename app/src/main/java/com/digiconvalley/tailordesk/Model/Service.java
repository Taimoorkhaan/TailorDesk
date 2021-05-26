package com.digiconvalley.tailordesk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Service implements Serializable {
    @SerializedName("serviceId")
    @Expose
    private Integer serviceId;
    @SerializedName("serviceName")
    @Expose
    private String serviceName;
    @SerializedName("servicePicture")
    @Expose
    private String servicePicture;
    @SerializedName("catagoryId")
    @Expose
    private Integer catagoryId;
    @SerializedName("servicePart")
    @Expose
    private List<ServicePart> servicePart = null;

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServicePicture() {
        return servicePicture;
    }

    public void setServicePicture(String servicePicture) {
        this.servicePicture = servicePicture;
    }

    public Integer getCatagoryId() {
        return catagoryId;
    }

    public void setCatagoryId(Integer catagoryId) {
        this.catagoryId = catagoryId;
    }

    public List<ServicePart> getServicePart() {
        return servicePart;
    }

    public void setServicePart(List<ServicePart> servicePart) {
        this.servicePart = servicePart;
    }

    @Override
    public String toString() {
        return serviceName;
    }
}
