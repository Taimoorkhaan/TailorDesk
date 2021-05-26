package com.digiconvalley.tailordesk.DataSturcture;

import com.digiconvalley.tailordesk.Model.CreateOrder;
import com.digiconvalley.tailordesk.Model.MeasurementDetail;
import com.digiconvalley.tailordesk.Model.TailorOrders.TailorOrders;
import com.digiconvalley.tailordesk.Model.TailorProfile;

import java.util.ArrayList;

public class StaticData {

    public static String baseUrl = "http://3.14.105.67/";
    public static String baseUrlImages = "http://3.14.105.67/Uploads/";
    //  public static String baseUrlImages="https://stiching-wo6.conveyor.cloud/Uploads/";
    public static ArrayList<CreateOrder> createOrders = new ArrayList<>();
    public static int n = 0, orderNo = 0;
    public static String customerid, tailorShopId, customerName;
    public static ArrayList<TailorOrders> tailorPastDuaOrders = new ArrayList<>();
    public static TailorProfile tailorProfile;
}
