package com.example.airlinesapp.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.asLiveData
import androidx.test.filters.SmallTest
import com.example.airlinesapp.data.local.AirlinesDao
import com.example.airlinesapp.data.local.AirlinesDatabase
import com.example.airlinesapp.data.pojo.Airline
import com.example.airlinesapp.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class AirlinesDaoTest {

    private val dummyData = listOf(
        Airline(country = "Egypt",established = "4-9-2021",createdDate = "4-9-2021",head_quaters = "Tanta",id = 1.0,logo = "www.google.com.png",name = "dummy name1",slogan = "slogan",website = "www.google.com"),
        Airline(country = "Ecuador",established = "4-9-2021",createdDate = "4-9-2021",head_quaters = "Tanta",id = 1.0,logo = "www.google.com.png",name = "dummy name2",slogan = "slogan",website = "www.google.com")
    )

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AirlinesDatabase
    private lateinit var dao: AirlinesDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.airlinesDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertAndRetrieveAirlines() = runBlockingTest {

        dao.insertAll(dummyData)
        val allAirlinesItem = dao.retrieveAllAirlines().asLiveData().getOrAwaitValue()
        assertThat(allAirlinesItem).contains(dummyData[0])
    }

    @Test
    fun retrieveAirlinesByName() = runBlockingTest {

        dao.insertAll(dummyData)
        val allAirlinesItem = dao.retrieveAirlinesByName("dummy name1").asLiveData().getOrAwaitValue()
        assertThat(allAirlinesItem).hasSize(1)
    }

    @Test
    fun retrieveAirlinesByCountry() = runBlockingTest {
        dao.insertAll(dummyData)
        val allAirlinesItem = dao.retrieveAirlinesByCountry("Ecuador").asLiveData().getOrAwaitValue()
        assertThat(allAirlinesItem).hasSize(1)
    }

    @Test
    fun retrieveAirlinesById() = runBlockingTest {
        dao.insertAll(dummyData)
        val allAirlinesItem = dao.retrieveAirlinesById(1.0).asLiveData().getOrAwaitValue()
        assertThat(allAirlinesItem).hasSize(1)
    }

    @Test
    fun deleteAllAirlines() = runBlockingTest {

        dao.insertAll(dummyData)
        dao.deleteAllAirlines()
        val allAirlinesItem = dao.retrieveAllAirlines().asLiveData().getOrAwaitValue()
        assertThat(allAirlinesItem).isEmpty()
    }
}
