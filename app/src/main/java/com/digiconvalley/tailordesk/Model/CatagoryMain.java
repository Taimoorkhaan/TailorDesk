package com.digiconvalley.tailordesk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CatagoryMain implements Serializable {
    private Boolean checked=false;
    @SerializedName("catagoryId")
    @Expose
    private Integer catagoryId;
    @SerializedName("catagoryName")
    @Expose
    private String catagoryName;
    @SerializedName("services")
    @Expose
    private List<Service> services = null;

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Integer getCatagoryId() {
        return catagoryId;
    }

    public void setCatagoryId(Integer catagoryId) {
        this.catagoryId = catagoryId;
    }

    public String getCatagoryName() {
        return catagoryName;
    }

    public void setCatagoryName(String catagoryName) {
        this.catagoryName = catagoryName;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    @Override
    public String toString() {
        return catagoryName;
    }
}
