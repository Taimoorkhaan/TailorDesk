package com.digiconvalley.tailordesk.Api;

import com.digiconvalley.tailordesk.DataSturcture.StaticData;
import com.digiconvalley.tailordesk.Model.SalesandPackages.MainDuePayment;
import com.digiconvalley.tailordesk.Model.SalesandPackages.MainPackageData;
import com.digiconvalley.tailordesk.Model.SalesandPackages.MainSalesOrder;
import com.digiconvalley.tailordesk.Model.SalesandPackages.ReportModel;
import com.digiconvalley.tailordesk.Model.SalesandPackages.ShopPackageModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private ApiInterface apiInterface;
    private static ApiClient apiClient;
    private Gson gson;

    private ApiClient() {
        this.gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(StaticData.baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build();

        this.apiInterface = retrofit.create(ApiInterface.class);

    }

    public static ApiClient getInstance() {
        if (apiClient == null) {
            setInstance(new ApiClient());
        }
        return apiClient;
    }

    private static void setInstance(ApiClient apiClient) {
        ApiClient.apiClient = apiClient;
    }

    private OkHttpClient getClient() {
        return new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
                        Request.Builder builder = chain.request().newBuilder();
                        // builder.addHeader("Accept", "application/json");
                        //builder.addHeader("Content-Type", "application/json");


                        return chain.proceed(builder.build());
                    }
                })
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build();
    }

    public void getTailorSalesModel(String tailorId, Callback<List<MainSalesOrder>> callback) {
        Call<List<MainSalesOrder>> call = this.apiInterface.getTailorSaleOrder(tailorId);
        call.enqueue(callback);
    }

    public void getTailorDuePaymentModel(String tailorId, Callback<List<MainDuePayment>> callback) {
        Call<List<MainDuePayment>> call = this.apiInterface.getTailorDuePaymentOrder(tailorId);
        call.enqueue(callback);
    }

    public void getMonthlyPackageModel(Callback<MainPackageData> callback) {
        Call<MainPackageData> call = this.apiInterface.getTailorMonthlyPackage();
        call.enqueue(callback);
    }

    public void getTailorReport(String tailorId, Callback<ReportModel> callback) {
        Call<ReportModel> call = this.apiInterface.getTailorReport(tailorId);
        call.enqueue(callback);
    }
    public void getShopPackage(String packageId, String tailorId, Callback<ShopPackageModel> callback){
        Call<ShopPackageModel> call = this.apiInterface.getShopPackage(packageId,tailorId);
        call.enqueue(callback);
    }
}
