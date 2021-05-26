package com.digiconvalley.tailordesk.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TailorCustomers implements Serializable {
    @SerializedName("customerId")
    @Expose
    private Integer customerId;
    @SerializedName("customerName")
    @Expose
    private String customerName;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

}
