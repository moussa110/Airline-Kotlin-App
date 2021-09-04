package com.example.airlinesapp.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.airlinesapp.MainCoroutineRule
import com.example.airlinesapp.data.pojo.AddAirlineRequest
import com.example.airlinesapp.getOrAwaitValueTest
import com.example.airlinesapp.repositories.FakeAirlinesRepository
import com.example.airlinesapp.ui.airlineslist.AirlinesViewModel
import com.example.airlinesapp.util.SearchCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class AirlinesViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: AirlinesViewModel

    @Before
    fun setup() {
        viewModel = AirlinesViewModel(FakeAirlinesRepository())
    }

    @Test
    fun `get remote, cache and display id, return success`() {
        viewModel.getAllAirlines()
        val liveData = viewModel.airlinesLiveData.getOrAwaitValueTest()
        val data = liveData.data
        assertThat(data!!.size).isEqualTo(2)
    }

    @Test
    fun `insert new airline and response success, returns success`() {
        viewModel.addNewAirline(AddAirlineRequest(name = "mousa airline"))
        val data = viewModel.addAirlineResultLiveData.getOrAwaitValueTest()
        assertThat(data!!.name).isEqualTo("success")
    }


    @Test
    fun `search by query, returns success`() {
        val livedata = viewModel.searchByQuery(SearchCase.NAME,"egypt air")
        val data = livedata.getOrAwaitValueTest()
        assertThat(data!!.size).isEqualTo(2)
    }

}













