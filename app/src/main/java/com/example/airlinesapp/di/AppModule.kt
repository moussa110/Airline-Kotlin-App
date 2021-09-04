package com.example.airlinesapp.di

import android.app.Application
import androidx.room.Room
import com.example.airlinesapp.data.local.AirlinesDatabase
import com.example.airlinesapp.data.remote.WebServices
import com.example.airlinesapp.repositories.AirlinesRepository
import com.example.airlinesapp.repositories.DefaultAirlinesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(WebServices.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideWebServices(retrofit: Retrofit): WebServices =
        retrofit.create(WebServices::class.java)

    @Provides
    @Singleton
    fun provideDataBase(application : Application) : AirlinesDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            AirlinesDatabase::class.java,
            "AIRLINES-DB"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDefaultAirlinesRepository(
        db: AirlinesDatabase,
        api: WebServices
    ) = DefaultAirlinesRepository(api,db) as AirlinesRepository


}