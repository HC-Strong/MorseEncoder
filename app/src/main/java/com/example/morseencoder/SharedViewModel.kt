package com.example.morseencoder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class SharedViewModel: ViewModel() {
    //TODO: which of these live datas should I use backing for? all probably
    val curSecretMessage = MutableLiveData<String?>()

    val lightOn = MutableLiveData<Boolean>()

    val sendingCharacter = MutableLiveData<Int>()

    fun updateSecretMessage(message: String?) {
        curSecretMessage.value = message
        Timber.i("Secret Message updated to ${curSecretMessage.value.toString()}")
    }

    init {
        lightOn.value = false
        sendingCharacter.value = 1
    }
}