package com.digiconvalley.tailordesk.Model;

import android.net.Uri;

import java.io.Serializable;
import java.util.ArrayList;

public class TailorBasicProfile implements Serializable {
    private int tailorID;
    private String personName;
    private String shopName;
    private String tailorEmail;
    private String countryName;
    private String cityName;
    private String shopAddress;
    private ArrayList<Integer> integerArrayList;
    private String tailorPhoneNo;
    private Double latitude;
    private Double longtite;
    private String address;
    private String addressNear;
    private String tailorShopLogoName;
    private String tailorShopLogoUri;


    public String getTailorShopLogoName() {
        return tailorShopLogoName;
    }

    public void setTailorShopLogoName(String tailorShopLogoName) {
        this.tailorShopLogoName = tailorShopLogoName;
    }

    public String getTailorShopLogoUri() {
        return tailorShopLogoUri;
    }

    public void setTailorShopLogoUri(String tailorShopLogoUri) {
        this.tailorShopLogoUri = tailorShopLogoUri;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongtite() {
        return longtite;
    }

    public void setLongtite(Double longtite) {
        this.longtite = longtite;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTailorPhoneNo() {
        return tailorPhoneNo;
    }

    public void setTailorPhoneNo(String tailorPhoneNo) {
        this.tailorPhoneNo = tailorPhoneNo;
    }

    public ArrayList<Integer> getIntegerArrayList() {
        return integerArrayList;
    }

    public void setIntegerArrayList(ArrayList<Integer> integerArrayList) {
        this.integerArrayList = integerArrayList;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getTailorEmail() {
        return tailorEmail;
    }

    public void setTailorEmail(String tailorEmail) {
        this.tailorEmail = tailorEmail;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public int getTailorID() {
        return tailorID;
    }

    public void setTailorID(int tailorID) {
        this.tailorID = tailorID;
    }

    public String getAddressNear() {
        return addressNear;
    }

    public void setAddressNear(String addressNear) {
        this.addressNear = addressNear;
    }
}
