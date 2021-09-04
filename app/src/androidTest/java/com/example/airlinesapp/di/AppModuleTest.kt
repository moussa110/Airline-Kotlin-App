package com.example.airlinesapp.di

import android.content.Context
import androidx.room.Room
import com.example.airlinesapp.data.local.AirlinesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModuleTest {

    @Provides
    @Named("test_db")
    fun provideInMemoryDb(@ApplicationContext context: Context) =
        Room.inMemoryDatabaseBuilder(context, AirlinesDatabase::class.java)
            .allowMainThreadQueries()
            .build()
}