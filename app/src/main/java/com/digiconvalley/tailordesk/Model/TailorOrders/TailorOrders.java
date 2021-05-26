package com.digiconvalley.tailordesk.Model.TailorOrders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TailorOrders implements Serializable {
    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("orderNo")
    @Expose
    private String orderNo;
    @SerializedName("orderDesc")
    @Expose
    private String orderDesc;
    @SerializedName("totalSuits")
    @Expose
    private Integer totalSuits;
    @SerializedName("totalPrice")
    @Expose
    private Integer totalPrice;
    @SerializedName("ramainingAmount")
    @Expose
    private Integer ramainingAmount;
    @SerializedName("recievedAmount")
    @Expose
    private Integer recievedAmount;
    @SerializedName("discountAmount")
    @Expose
    private Integer discountAmount;
    @SerializedName("orderStatus")
    @Expose
    private String orderStatus;
    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("orderDeliveryDate")
    @Expose
    private String orderDeliveryDate;
    @SerializedName("totalPerson")
    @Expose
    private Integer totalPerson;
    @SerializedName("orderSuit")
    @Expose
    private List<OrderSuit> orderSuit = null;
    @SerializedName("orderReceivedAmounts")
    @Expose
    private List<OrderReceivedAmountLog> orderReceivedAmounts = null;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getOrderDesc() {
        return orderDesc;
    }

    public void setOrderDesc(String orderDesc) {
        this.orderDesc = orderDesc;
    }

    public Integer getTotalSuits() {
        return totalSuits;
    }

    public void setTotalSuits(Integer totalSuits) {
        this.totalSuits = totalSuits;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Integer getRamainingAmount() {
        return ramainingAmount;
    }

    public void setRamainingAmount(Integer ramainingAmount) {
        this.ramainingAmount = ramainingAmount;
    }

    public Integer getRecievedAmount() {
        return recievedAmount;
    }

    public void setRecievedAmount(Integer recievedAmount) {
        this.recievedAmount = recievedAmount;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(Integer discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderDeliveryDate() {
        return orderDeliveryDate;
    }

    public void setOrderDeliveryDate(String orderDeliveryDate) {
        this.orderDeliveryDate = orderDeliveryDate;
    }

    public Integer getTotalPerson() {
        return totalPerson;
    }

    public void setTotalPerson(Integer totalPerson) {
        this.totalPerson = totalPerson;
    }

    public List<OrderSuit> getOrderSuit() {
        return orderSuit;
    }

    public void setOrderSuit(List<OrderSuit> orderSuit) {
        this.orderSuit = orderSuit;
    }

    public List<OrderReceivedAmountLog> getOrderReceivedAmounts() {
        return orderReceivedAmounts;
    }

    public void setOrderReceivedAmounts(List<OrderReceivedAmountLog> orderReceivedAmounts) {
        this.orderReceivedAmounts = orderReceivedAmounts;
    }
}


