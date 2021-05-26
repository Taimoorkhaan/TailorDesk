package com.digiconvalley.tailordesk.Model.TailorOrders;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderSuit implements Serializable {
    private Integer orderId;
    @SerializedName("customerId")
    @Expose
    private Integer customerId;
    @SerializedName("customerName")
    @Expose
    private String customerName;
    @SerializedName("customerPhoneNo")
    @Expose
    private String customerPhoneNo;
    @SerializedName("customerFacePic")
    @Expose
    private String customerFacePic;
    @SerializedName("serviceId")
    @Expose
    private Integer serviceId;
    @SerializedName("orderSuitId")
    @Expose
    private Integer orderSuitId;
    @SerializedName("orderSuitNo")
    @Expose
    private Object orderSuitNo;
    @SerializedName("orderSuitName")
    @Expose
    private String orderSuitName;
    @SerializedName("orderSuitPic1")
    @Expose
    private String orderSuitPic1;
    @SerializedName("orderSuitpic2")
    @Expose
    private String orderSuitpic2;
    @SerializedName("orderPatternPic1")
    @Expose
    private String orderPatternPic1;
    @SerializedName("orderPatternPic2")
    @Expose
    private String orderPatternPic2;
    @SerializedName("orderSuitDesc")
    @Expose
    private String orderSuitDesc;
    @SerializedName("orderSuitPrice")
    @Expose
    private String orderSuitPrice;
    @SerializedName("numberOfPocket")
    @Expose
    private Integer numberOfPocket;
    @SerializedName("pocketType")
    @Expose
    private String pocketType;
    @SerializedName("indexOfPleats")
    @Expose
    private Integer indexOfPleats;
    @SerializedName("indexOfPocket")
    @Expose
    private Integer indexOfPocket;
    @SerializedName("audio")
    @Expose
    private String audio;
    @SerializedName("pleats")
    @Expose
    private String pleats;
    @SerializedName("itemNumber")
    @Expose
    private String itemNumber;
    @SerializedName("orderSuitType")
    @Expose
    private String orderSuitType;
    @SerializedName("urgentStatus")
    @Expose
    private Boolean urgentStatus;
    @SerializedName("deliveryDate")
    @Expose
    private String deliveryDate;
    @SerializedName("orderSuitStatus")
    @Expose
    private String orderSuitStatus;
    @SerializedName("suitMeasurement")
    @Expose
    private List<SuitMeasurement> suitMeasurement = null;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

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

    public String getCustomerFacePic() {
        return customerFacePic;
    }

    public void setCustomerFacePic(String customerFacePic) {
        this.customerFacePic = customerFacePic;
    }

    public Integer getServiceId() {
        return serviceId;
    }

    public void setServiceId(Integer serviceId) {
        this.serviceId = serviceId;
    }

    public Integer getOrderSuitId() {
        return orderSuitId;
    }

    public void setOrderSuitId(Integer orderSuitId) {
        this.orderSuitId = orderSuitId;
    }

    public Object getOrderSuitNo() {
        return orderSuitNo;
    }

    public void setOrderSuitNo(Object orderSuitNo) {
        this.orderSuitNo = orderSuitNo;
    }

    public String getOrderSuitName() {
        return orderSuitName;
    }

    public void setOrderSuitName(String orderSuitName) {
        this.orderSuitName = orderSuitName;
    }

    public String getOrderSuitPic1() {
        return orderSuitPic1;
    }

    public void setOrderSuitPic1(String orderSuitPic1) {
        this.orderSuitPic1 = orderSuitPic1;
    }

    public String getOrderSuitpic2() {
        return orderSuitpic2;
    }

    public void setOrderSuitpic2(String orderSuitpic2) {
        this.orderSuitpic2 = orderSuitpic2;
    }

    public String getOrderPatternPic1() {
        return orderPatternPic1;
    }

    public void setOrderPatternPic1(String orderPatternPic1) {
        this.orderPatternPic1 = orderPatternPic1;
    }

    public String getOrderPatternPic2() {
        return orderPatternPic2;
    }

    public void setOrderPatternPic2(String orderPatternPic2) {
        this.orderPatternPic2 = orderPatternPic2;
    }

    public String getOrderSuitDesc() {
        return orderSuitDesc;
    }

    public void setOrderSuitDesc(String orderSuitDesc) {
        this.orderSuitDesc = orderSuitDesc;
    }

    public String getOrderSuitPrice() {
        return orderSuitPrice;
    }

    public void setOrderSuitPrice(String orderSuitPrice) {
        this.orderSuitPrice = orderSuitPrice;
    }

    public Integer getNumberOfPocket() {
        return numberOfPocket;
    }

    public void setNumberOfPocket(Integer numberOfPocket) {
        this.numberOfPocket = numberOfPocket;
    }

    public String getPocketType() {
        return pocketType;
    }

    public void setPocketType(String pocketType) {
        this.pocketType = pocketType;
    }

    public Integer getIndexOfPleats() {
        return indexOfPleats;
    }

    public void setIndexOfPleats(Integer indexOfPleats) {
        this.indexOfPleats = indexOfPleats;
    }

    public Integer getIndexOfPocket() {
        return indexOfPocket;
    }

    public void setIndexOfPocket(Integer indexOfPocket) {
        this.indexOfPocket = indexOfPocket;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getPleats() {
        return pleats;
    }

    public void setPleats(String pleats) {
        this.pleats = pleats;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
    }

    public String getOrderSuitType() {
        return orderSuitType;
    }

    public void setOrderSuitType(String orderSuitType) {
        this.orderSuitType = orderSuitType;
    }

    public Boolean getUrgentStatus() {
        return urgentStatus;
    }

    public void setUrgentStatus(Boolean urgentStatus) {
        this.urgentStatus = urgentStatus;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getOrderSuitStatus() {
        return orderSuitStatus;
    }

    public void setOrderSuitStatus(String orderSuitStatus) {
        this.orderSuitStatus = orderSuitStatus;
    }

    public List<SuitMeasurement> getSuitMeasurement() {
        return suitMeasurement;
    }

    public void setSuitMeasurement(List<SuitMeasurement> suitMeasurement) {
        this.suitMeasurement = suitMeasurement;
    }

    public String getCustomerPhoneNo() {
        return customerPhoneNo;
    }

    public void setCustomerPhoneNo(String customerPhoneNo) {
        this.customerPhoneNo = customerPhoneNo;
    }
}
