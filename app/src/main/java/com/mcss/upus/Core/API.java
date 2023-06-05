package com.mcss.upus.Core;

import com.mcss.upus.Model.SlidePicture;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    @GET("slide")
    Call<List<SlidePicture>> getPicturesUrl();

}
