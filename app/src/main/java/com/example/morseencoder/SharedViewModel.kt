package com.example.morseencoder

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber

class SharedViewModel: ViewModel() {
    //TODO: which of these live datas should I use backing for? I'd say all but I'm modifying them from elsewhere... probably shouldn't be doing that

    // ENCODER LIVE DATA //
    val curSecretMessage = MutableLiveData<String?>()

    val lightOn = MutableLiveData<Boolean>()

    val curCharacter = MutableLiveData<Int>()

    val messageCancelled = MutableLiveData<Boolean>()

    fun updateSecretMessage(message: String?) {
        curSecretMessage.value = message
        Timber.i("Secret Message updated to ${curSecretMessage.value.toString()}")
    }

    // DECODER LIVE DATA //
    //will put decoder live data here once i start working on decoder section

    // INIT FOR ALL LIVE DATA //
    init {
        lightOn.value = false
        messageCancelled.value = false
    }
}