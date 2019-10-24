package com.example.morseencoder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class SharedViewModel: ViewModel() {
    val curSecretMessage = MutableLiveData<String?>()

    fun updateSecretMessage(message: String?) {
        curSecretMessage.value = message
        Timber.i("Secret Message updated to ${curSecretMessage.value.toString()}")
    }
}