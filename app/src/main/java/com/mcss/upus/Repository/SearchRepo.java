package com.mcss.upus.Repository;

import com.mcss.upus.Model.SearchInventoryResponse;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SearchRepo {

    @POST("cargomat_search")
    Call<String> getPhoneNumber(@Query("bundle_system_code") String inventoryCode);

}
