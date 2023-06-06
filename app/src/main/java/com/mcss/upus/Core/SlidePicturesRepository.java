package com.mcss.upus.Core;


import com.mcss.upus.Model.SlidePicture;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;

public interface SlidePicturesRepository {
    @POST("get_slayt?device_id=1&mainboard_id=1")
    Call<List<SlidePicture>> getPictures();
}
