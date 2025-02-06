package com.android1.weatherappassement.ApiInterface

import com.android1.weatherappassement.DataClass.JsonData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
        @GET("weather")
        fun getwetherData(
            @Query("q")city:String,
            @Query("appid")appid:String,
            @Query("units")units:String,
        ): Call<JsonData>

    }