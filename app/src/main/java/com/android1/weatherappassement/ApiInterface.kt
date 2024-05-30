package com.android1.weatherappassement

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