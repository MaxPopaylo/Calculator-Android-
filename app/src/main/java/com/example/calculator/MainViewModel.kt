package com.example.calculator

import android.annotation.SuppressLint
import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.android.parcel.Parcelize

class MainViewModel: ViewModel() {

    val state: LiveData<State> get() = stateLiveData
    private val stateLiveData = MutableLiveData<State>()

    @Parcelize
    data class State(
        var firstNum: Number,
        var secondNum: Number,
        var result: Number,
        var action: Action
    ) :Parcelable
}


