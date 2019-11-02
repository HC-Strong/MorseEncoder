package com.example.morseencoder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class SharedViewModel: ViewModel() {
    val curSecretMessage = MutableLiveData<String?>()

    val lightOn = MutableLiveData<Boolean>()

    fun updateSecretMessage(message: String?) {
        curSecretMessage.value = message
        Timber.i("Secret Message updated to ${curSecretMessage.value.toString()}")
    }

    init {
        lightOn.value = false
    }
}