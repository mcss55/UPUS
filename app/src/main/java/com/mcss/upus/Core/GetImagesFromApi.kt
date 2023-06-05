package com.mcss.upus.Core

import com.mcss.upus.Model.SlidePicture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.POST

class GetImagesFromApi {

    private lateinit var apiService: ApiService
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.example.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Launch a coroutine on the Main dispatcher
    suspend fun getData() {

    }

}

interface ApiService {

    @POST("get_slayt?device_id=1&mainboard_id=1")
    suspend fun getPics(): List<SlidePicture>

}
