package com.example.airlinesapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.airlinesapp.data.pojo.Airline

@Database(entities = [Airline::class],version = 1)
abstract class AirlinesDatabase : RoomDatabase(){
    abstract fun airlinesDao(): AirlinesDao
}