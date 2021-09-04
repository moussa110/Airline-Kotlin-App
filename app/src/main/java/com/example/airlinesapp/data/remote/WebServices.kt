package com.example.airlinesapp.data.remote

import com.example.airlinesapp.data.pojo.AddAirlineRequest
import com.example.airlinesapp.data.pojo.Airline
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface WebServices {
    companion object{
        const val BASE_URL = "https://api.instantwebtools.net/v1/"
    }

    @GET("airlines")
    suspend fun getAllAirlines():List<Airline>

    @POST("airlines")
    suspend fun addAirline(@Body addAirline: AddAirlineRequest): Response<Airline>

}