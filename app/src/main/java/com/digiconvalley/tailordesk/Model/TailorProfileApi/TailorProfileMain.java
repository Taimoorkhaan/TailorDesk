package com.digiconvalley.tailordesk.Model.TailorProfileApi;

import com.digiconvalley.tailordesk.Model.CatagoryMain;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TailorProfileMain implements Serializable {
    private ArrayList<CatagoryMain> catagoryMainArrayList;
    @SerializedName("tailorData")
    @Expose
    private TailorProfileGet tailorData;
    @SerializedName("category")
    @Expose
    private List<CategoryProfile> category = null;

    public TailorProfileGet getTailorData() {
        return tailorData;
    }

    public void setTailorData(TailorProfileGet tailorData) {
        this.tailorData = tailorData;
    }

    public List<CategoryProfile> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryProfile> category) {
        this.category = category;
    }

    public ArrayList<CatagoryMain> getCatagoryMainArrayList() {
        return catagoryMainArrayList;
    }

    public void setCatagoryMainArrayList(ArrayList<CatagoryMain> catagoryMainArrayList) {
        this.catagoryMainArrayList = catagoryMainArrayList;
    }
}
