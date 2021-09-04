package com.example.airlinesapp.ui.airlineslist

import androidx.lifecycle.*
import com.example.airlinesapp.data.pojo.AddAirlineRequest
import com.example.airlinesapp.data.pojo.Airline
import com.example.airlinesapp.repositories.AirlinesRepository
import com.example.airlinesapp.util.Resource
import com.example.airlinesapp.util.SearchCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AirlinesViewModel @Inject constructor (
    private val repository: AirlinesRepository
        ):ViewModel() {

    lateinit var airlinesLiveData: LiveData<Resource<List<Airline>>>
    private var _addAirlineResultLiveData = MutableLiveData<Airline?>()
    val addAirlineResultLiveData:LiveData<Airline?> get() = _addAirlineResultLiveData

init {
    getAllAirlines()
}
    fun getAllAirlines() {
        airlinesLiveData = repository.getAirlines().asLiveData()
    }


    fun searchByQuery(aCase: SearchCase, name:String) = repository.searchByQuery(aCase,name).asLiveData()

    fun addNewAirline(addAirline: AddAirlineRequest){
        viewModelScope.launch {
            var response = repository.addAirline(addAirline)
            if (response.isSuccessful)
                _addAirlineResultLiveData.value = response.body()
            else
               _addAirlineResultLiveData.value = null
        }
    }

}