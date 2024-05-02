package com.example.usingcharts.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.usingcharts.data.api.ApiResponse
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {
    private val _numbersLiveData = MutableLiveData<List<Int>>()
    val numbersLiveData: LiveData<List<Int>> = _numbersLiveData
    private val numbersList = mutableListOf<Int>()

    fun fetchRandomNumberRepeatedly() {
        viewModelScope.launch {
            while (isActive) {
                try {
                    //get the response from the API the random number
                    val numberString = ApiResponse.api.getRandomNumber().trim()
                    //since it might come in a string form then convert to Int or null
                    val number = numberString.toIntOrNull()
                    //if it's null then it wont do anything since it won't pass.
                    number?.let {
                        //Here if the number of the list is greater or equal to 10 then remove the first one
                        //to keep fresh the list.
                        if (numbersList.size >= 10) {
                            numbersList.removeAt(0)
                        }
                        //Add every new number to the list
                        numbersList.add(it)
                        // Post a copy of the list to trigger LiveData updates
                        _numbersLiveData.postValue(ArrayList(numbersList))
                    }
                    // Repeat every second
                    delay(1000)
                } catch (e: Exception) {
                    Log.e("VMError", "Failure fetching random number", e)
                }
            }
        }
    }
}