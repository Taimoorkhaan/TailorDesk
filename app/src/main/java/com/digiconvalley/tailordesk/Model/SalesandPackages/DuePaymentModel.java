package com.digiconvalley.tailordesk.Model.SalesandPackages;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DuePaymentModel {
    @SerializedName("tailorProfilePic")
    @Expose
    private Object tailorProfilePic;
    @SerializedName("tailorName")
    @Expose
    private String tailorName;
    @SerializedName("orderNo")
    @Expose
    private String orderNo;
    @SerializedName("totalPrice")
    @Expose
    private Integer totalPrice;
    @SerializedName("totalSuits")
    @Expose
    private Integer totalSuits;
    @SerializedName("orderDeliveryDate")
    @Expose
    private String orderDeliveryDate;
    @SerializedName("remainingAmount")
    @Expose
    private Integer remainingAmount;

    public Object getTailorProfilePic() {
        return tailorProfilePic;
    }

    public void setTailorProfilePic(Object tailorProfilePic) {
        this.tailorProfilePic = tailorProfilePic;
    }

    public String getTailorName() {
        return tailorName;
    }

    public void setTailorName(String tailorName) {
        this.tailorName = tailorName;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getTotalSuits() {
        return totalSuits;
    }

    public void setTotalSuits(Integer totalSuits) {
        this.totalSuits = totalSuits;
    }

    public String getOrderDeliveryDate() {
        return orderDeliveryDate;
    }

    public void setOrderDeliveryDate(String orderDeliveryDate) {
        this.orderDeliveryDate = orderDeliveryDate;
    }

    public Integer getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Integer remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
}
