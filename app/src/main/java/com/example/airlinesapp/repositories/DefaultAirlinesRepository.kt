package com.example.airlinesapp.repositories

import androidx.room.withTransaction
import com.example.airlinesapp.data.local.AirlinesDatabase
import com.example.airlinesapp.data.pojo.AddAirlineRequest
import com.example.airlinesapp.data.pojo.Airline
import com.example.airlinesapp.data.remote.WebServices
import com.example.airlinesapp.util.SearchCase
import com.example.airlinesapp.util.networkBoundResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DefaultAirlinesRepository @Inject constructor(
    private val api: WebServices,
    private val db: AirlinesDatabase
):AirlinesRepository{
    private val dao = db.airlinesDao()

    override fun getAirlines() =
        networkBoundResource(
            query = {
                dao.retrieveAllAirlines()
            },
            fetch = {
                api.getAllAirlines()
            },
            saveFetchResult = { airlines ->
                db.withTransaction {
                    dao.deleteAllAirlines()
                    dao.insertAll(airlines)
                }
            }
        )

    override fun searchByQuery(aCase:SearchCase, query: String): Flow<List<Airline>> {
        return when (aCase){
            SearchCase.NAME -> dao.retrieveAirlinesByName(query)
            SearchCase.ID -> dao.retrieveAirlinesById(query.toDouble())
            SearchCase.COUNTRY -> dao.retrieveAirlinesByCountry(query)
            }

        }

     override suspend fun addAirline(addAirline: AddAirlineRequest)= api.addAirline(addAirline)






}