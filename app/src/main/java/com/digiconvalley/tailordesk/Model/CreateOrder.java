package com.digiconvalley.tailordesk.Model;

import java.io.Serializable;
import java.util.ArrayList;

public class CreateOrder implements Serializable {

    private String serviceName;
    private String serviceImage;
    private String stitchingMode;
    private String stitchingPrice;
    private String devlieryDate=null;
    private String noOfPocket;
    private String pocketMode;
    private String noOfPleats;
    private int noPleatsIndex;
    private int noPocketIndex;
    private String collarType;
    private int collarIndex;
    private ArrayList<ServicePart> noMeasurement;
    private String specialNote;
    private Boolean dressGiven;
    private Boolean urgentNeed;

    private Boolean newStitchingStatus;
    private Boolean alternationStatus;
    private Boolean crossPocketStatus;
    private Boolean straightPocketsStatus;

    private String cloth01ImageName;
    private String cloth02ImageName;
    private String cloth01Name;
    private String cloth02Name;
    private String clothImageUri1;
    private String clothImageUri2;

    private String Pattern01ImageName;
    private String Pattern02ImageName;
    private String Pattern01Name;
    private String Pattern02Name;
    private String Pattern01Uri;
    private String Pattern02Uri;

    private String itemNo;
    private String CustomerId;
    private String ServiceId;
    private String receivedAmount;
    private String totalBill;


    public String getServiceImage() {
        return serviceImage;
    }

    public void setServiceImage(String serviceImage) {
        this.serviceImage = serviceImage;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getStitchingMode() {
        return stitchingMode;
    }

    public void setStitchingMode(String stitchingMode) {
        this.stitchingMode = stitchingMode;
    }

    public String getStitchingPrice() {
        return stitchingPrice;
    }

    public void setStitchingPrice(String stitchingPrice) {
        this.stitchingPrice = stitchingPrice;
    }

    public String getDevlieryDate() {
        return devlieryDate;
    }

    public void setDevlieryDate(String devlieryDate) {
        this.devlieryDate = devlieryDate;
    }

    public String getNoOfPocket() {
        return noOfPocket;
    }

    public void setNoOfPocket(String noOfPocket) {
        this.noOfPocket = noOfPocket;
    }

    public String getPocketMode() {
        return pocketMode;
    }

    public void setPocketMode(String pocketMode) {
        this.pocketMode = pocketMode;
    }

    public String getNoOfPleats() {
        return noOfPleats;
    }

    public void setNoOfPleats(String noOfPleats) {
        this.noOfPleats = noOfPleats;
    }

    public String getSpecialNote() {
        return specialNote;
    }

    public void setSpecialNote(String specialNote) {
        this.specialNote = specialNote;
    }

    public Boolean getDressGiven() {
        return dressGiven;
    }

    public void setDressGiven(Boolean dressGiven) {
        this.dressGiven = dressGiven;
    }

    public Boolean getUrgentNeed() {
        return urgentNeed;
    }

    public void setUrgentNeed(Boolean urgentNeed) {
        this.urgentNeed = urgentNeed;
    }

    public String getCloth01ImageName() {
        return cloth01ImageName;
    }

    public void setCloth01ImageName(String cloth01ImageName) {
        this.cloth01ImageName = cloth01ImageName;
    }

    public String getCloth02ImageName() {
        return cloth02ImageName;
    }

    public void setCloth02ImageName(String cloth02ImageName) {
        this.cloth02ImageName = cloth02ImageName;
    }

    public String getCloth01Name() {
        return cloth01Name;
    }

    public void setCloth01Name(String cloth01Name) {
        this.cloth01Name = cloth01Name;
    }

    public String getCloth02Name() {
        return cloth02Name;
    }

    public void setCloth02Name(String cloth02Name) {
        this.cloth02Name = cloth02Name;
    }

    public String getPattern01ImageName() {
        return Pattern01ImageName;
    }

    public void setPattern01ImageName(String pattern01ImageName) {
        Pattern01ImageName = pattern01ImageName;
    }

    public String getPattern02ImageName() {
        return Pattern02ImageName;
    }

    public void setPattern02ImageName(String pattern02ImageName) {
        Pattern02ImageName = pattern02ImageName;
    }

    public String getPattern01Name() {
        return Pattern01Name;
    }

    public void setPattern01Name(String pattern01Name) {
        Pattern01Name = pattern01Name;
    }

    public String getPattern02Name() {
        return Pattern02Name;
    }

    public void setPattern02Name(String pattern02Name) {
        Pattern02Name = pattern02Name;
    }

    public ArrayList<ServicePart> getNoMeasurement() {
        return noMeasurement;
    }

    public void setNoMeasurement(ArrayList<ServicePart> noMeasurement) {
        this.noMeasurement = noMeasurement;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getCustomerId() {
        return CustomerId;
    }

    public void setCustomerId(String customerId) {
        CustomerId = customerId;
    }

    public String getServiceId() {
        return ServiceId;
    }

    public void setServiceId(String serviceId) {
        ServiceId = serviceId;
    }

    public String getClothImageUri1() {
        return clothImageUri1;
    }

    public void setClothImageUri1(String clothImageUri1) {
        this.clothImageUri1 = clothImageUri1;
    }

    public String getClothImageUri2() {
        return clothImageUri2;
    }

    public void setClothImageUri2(String clothImageUri2) {
        this.clothImageUri2 = clothImageUri2;
    }

    public String getPattern01Uri() {
        return Pattern01Uri;
    }

    public void setPattern01Uri(String pattern01Uri) {
        Pattern01Uri = pattern01Uri;
    }

    public String getPattern02Uri() {
        return Pattern02Uri;
    }

    public void setPattern02Uri(String pattern02Uri) {
        Pattern02Uri = pattern02Uri;
    }

    public int getNoPleatsIndex() {
        return noPleatsIndex;
    }

    public void setNoPleatsIndex(int noPleatsIndex) {
        this.noPleatsIndex = noPleatsIndex;
    }

    public int getNoPocketIndex() {
        return noPocketIndex;
    }

    public void setNoPocketIndex(int noPocketIndex) {
        this.noPocketIndex = noPocketIndex;
    }

    public Boolean getNewStitchingStatus() {
        return newStitchingStatus;
    }

    public void setNewStitchingStatus(Boolean newStitchingStatus) {
        this.newStitchingStatus = newStitchingStatus;
    }

    public Boolean getAlternationStatus() {
        return alternationStatus;
    }

    public void setAlternationStatus(Boolean alternationStatus) {
        this.alternationStatus = alternationStatus;
    }

    public Boolean getCrossPocketStatus() {
        return crossPocketStatus;
    }

    public void setCrossPocketStatus(Boolean crossPocketStatus) {
        this.crossPocketStatus = crossPocketStatus;
    }

    public Boolean getStraightPocketsStatus() {
        return straightPocketsStatus;
    }

    public void setStraightPocketsStatus(Boolean straightPocketsStatus) {
        this.straightPocketsStatus = straightPocketsStatus;
    }

    public String getCollarType() {
        return collarType;
    }

    public void setCollarType(String collarType) {
        this.collarType = collarType;
    }

    public int getCollarIndex() {
        return collarIndex;
    }

    public void setCollarIndex(int collarIndex) {
        this.collarIndex = collarIndex;
    }

    public String getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(String receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public String getTotalBill() {
        return totalBill;
    }

    public void setTotalBill(String totalBill) {
        this.totalBill = totalBill;
    }
}
