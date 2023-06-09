package com.mcss.upus.Repository;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CargomatUpdate {
    @POST("cargomat_update")
    Call<String> updateData(@Query("take_photo") String takePhoto, @Query("password") String password);
}
