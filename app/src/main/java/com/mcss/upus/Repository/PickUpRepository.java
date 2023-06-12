package com.mcss.upus.Repository;

import com.mcss.upus.Model.PickUpResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface PickUpRepository {

    @POST("pickup_code")
    Call<PickUpResponse> getPickUpDetails(@Query("password") String pickUpCode);

}
