package com.digiconvalley.tailordesk.Model.SalesandPackages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SubPackageModel implements Serializable {
    @SerializedName("subPackageId")
    @Expose
    private Integer subPackageId;
    @SerializedName("subPackageName")
    @Expose
    private String subPackageName;

    public Integer getSubPackageId() {
        return subPackageId;
    }

    public void setSubPackageId(Integer subPackageId) {
        this.subPackageId = subPackageId;
    }

    public String getSubPackageName() {
        return subPackageName;
    }

    public void setSubPackageName(String subPackageName) {
        this.subPackageName = subPackageName;
    }

}
