package com.example.grzesiek87.qrscanner.repository;

import com.example.grzesiek87.qrscanner.domain.WarehouseRest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("/warehouse/getRestWarehouse/")
    Call<List<WarehouseRest>> getWarehousesRest();
}
