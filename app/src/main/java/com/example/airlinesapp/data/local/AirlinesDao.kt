package com.example.airlinesapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.airlinesapp.data.pojo.Airline
import kotlinx.coroutines.flow.Flow


@Dao
interface AirlinesDao {
    @Query("SELECT * FROM Airlines")
    fun retrieveAllAirlines(): Flow<List<Airline>>

    @Query("SELECT * FROM Airlines where name like :name || '%' ")
    fun retrieveAirlinesByName(name:String): Flow<List<Airline>>

    @Query("SELECT * FROM Airlines where id=:id")
    fun retrieveAirlinesById(id:Double): Flow<List<Airline>>

    @Query("SELECT * FROM Airlines where country like :country || '%'")
    fun retrieveAirlinesByCountry(country:String): Flow<List<Airline>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(airLines: List<Airline>)

    @Query("DELETE FROM Airlines")
    fun deleteAllAirlines()
}