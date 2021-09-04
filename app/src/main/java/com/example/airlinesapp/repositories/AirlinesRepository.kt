package com.example.airlinesapp.repositories

import com.example.airlinesapp.data.pojo.AddAirlineRequest
import com.example.airlinesapp.data.pojo.Airline
import com.example.airlinesapp.util.Resource
import com.example.airlinesapp.util.SearchCase
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AirlinesRepository {

     fun getAirlines(): Flow<Resource<List<Airline>>>

    fun searchByQuery(aCase: SearchCase, query: String): Flow<List<Airline>>

    suspend fun addAirline(addAirline: AddAirlineRequest): Response<Airline>
}