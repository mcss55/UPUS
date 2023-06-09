package com.mcss.upus.Repository;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface SendBundleInfo {
    @POST("cargomat_insert")
    Call<Void> insertData(@Query("device_id") String deviceId,
                    @Query("mainboard_id") String mainBoardId,
                    @Query("client_phone") String clientPhone,
                    @Query("password") String password,
                    @Query("bundle_system_code") String bundleSystemCode,
                    @Query("save_photo") String savePhoto,
                    @Query("box_no") String boxNo,
                    @Query("device_name") String deviceName);
}
