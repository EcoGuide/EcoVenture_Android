package com.example.ecoguide.ModelView

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecoguide.Model.Hotel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotelViewModel: ViewModel() {

    private val hotel: MutableLiveData<List<Hotel>> = MutableLiveData()
    private val errorMessage: MutableLiveData<String> = MutableLiveData()



    fun gethotel(): LiveData<List<Hotel>> = hotel
    fun getErrorMessage(): LiveData<String> = errorMessage


}