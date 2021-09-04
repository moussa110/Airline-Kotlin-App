package com.example.airlinesapp.repositories


import com.example.airlinesapp.data.pojo.AddAirlineRequest
import com.example.airlinesapp.data.pojo.Airline
import com.example.airlinesapp.util.Resource
import com.example.airlinesapp.util.SearchCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import retrofit2.Response

class FakeAirlinesRepository : AirlinesRepository {
    private val dummyData = listOf(
        Airline(country = "Egypt",createdDate = "4-9-2021",established = "4-9-2021",id = 1.0,logo = "www.google.com.png",name = "dummy name1",slogan = "slogan",website = "www.google.com"),
        Airline(country = "Ecuador",createdDate = "4-9-2021",established = "4-9-2021",id = 2.0,logo = "www.google.com.png",name = "dummy name2",slogan = "slogan",website = "www.google.com")
    )

    override fun getAirlines(): Flow<Resource<List<Airline>>> {
       return flowOf(Resource.Success(dummyData))
    }

    override fun searchByQuery(aCase: SearchCase, query: String): Flow<List<Airline>> {
        return flowOf(dummyData)
    }

    override suspend fun addAirline(addAirline: AddAirlineRequest): Response<Airline>{
        return Response.success(Airline(_id = "as125421g3",__v =0,name = "success" ))
    }

}











