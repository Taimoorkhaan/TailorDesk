package com.digiconvalley.tailordesk.Api;


import com.digiconvalley.tailordesk.Model.SalesandPackages.MainDuePayment;
import com.digiconvalley.tailordesk.Model.SalesandPackages.MainPackageData;
import com.digiconvalley.tailordesk.Model.SalesandPackages.MainSalesOrder;
import com.digiconvalley.tailordesk.Model.SalesandPackages.ReportModel;
import com.digiconvalley.tailordesk.Model.SalesandPackages.ShopPackageModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("TailorGetApi/AdvancePayment?")
    Call<List<MainSalesOrder>> getTailorSaleOrder(@Query("TailorShopId") String tailorId);

    @GET("TailorGetApi/DuePayment?")
    Call<List<MainDuePayment>> getTailorDuePaymentOrder(@Query("TailorShopId") String tailorId);

    @GET("TailorGetApi/PackageGet")
    Call<MainPackageData> getTailorMonthlyPackage();

    @GET("TailorGetApi/Reports?")
    Call<ReportModel> getTailorReport(@Query("TailorShopId") String tailorId);

    @GET("tailorgetapi/ShopPackage?")
    Call<ShopPackageModel> getShopPackage(@Query("PackageId") String packageId,@Query("TailorShopId") String tailorId);
}
