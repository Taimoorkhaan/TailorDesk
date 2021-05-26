package com.digiconvalley.tailordesk.Model.SalesandPackages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReportModel implements Serializable {

    @SerializedName("totalOrer")
    @Expose
    private Integer totalOrer;
    @SerializedName("totalSale")
    @Expose
    private Integer totalSale;
    @SerializedName("totalAdvance")
    @Expose
    private Integer totalAdvance;
    @SerializedName("notAssigned")
    @Expose
    private Integer notAssigned;
    @SerializedName("cutting")
    @Expose
    private Integer cutting;
    @SerializedName("stitching")
    @Expose
    private Integer stitching;
    @SerializedName("completed")
    @Expose
    private Integer completed;

    public Integer getTotalOrer() {
        return totalOrer;
    }

    public void setTotalOrer(Integer totalOrer) {
        this.totalOrer = totalOrer;
    }

    public Integer getTotalSale() {
        return totalSale;
    }

    public void setTotalSale(Integer totalSale) {
        this.totalSale = totalSale;
    }

    public Integer getTotalAdvance() {
        return totalAdvance;
    }

    public void setTotalAdvance(Integer totalAdvance) {
        this.totalAdvance = totalAdvance;
    }

    public Integer getNotAssigned() {
        return notAssigned;
    }

    public void setNotAssigned(Integer notAssigned) {
        this.notAssigned = notAssigned;
    }

    public Integer getCutting() {
        return cutting;
    }

    public void setCutting(Integer cutting) {
        this.cutting = cutting;
    }

    public Integer getStitching() {
        return stitching;
    }

    public void setStitching(Integer stitching) {
        this.stitching = stitching;
    }

    public Integer getCompleted() {
        return completed;
    }

    public void setCompleted(Integer completed) {
        this.completed = completed;
    }

}
