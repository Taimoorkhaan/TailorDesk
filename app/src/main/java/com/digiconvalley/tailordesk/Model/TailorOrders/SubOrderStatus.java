package com.digiconvalley.tailordesk.Model.TailorOrders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubOrderStatus {
    @SerializedName("status")
    @Expose
    private List<String> status = null;

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }
}
