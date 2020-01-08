package com.example.morseencoder

import android.opengl.Visibility
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import timber.log.Timber

class SharedViewModel: ViewModel() {
    //TODO: which of these live datas should I use backing for? I'd say all but I'm modifying them from elsewhere... probably shouldn't be doing that

    // ENCODER LIVE DATA //
    val curSecretMessage = MutableLiveData<String?>()

    val lightOn = MutableLiveData<Boolean>()

    val curCharacter = MutableLiveData<Int>()

    val messageCancelled = MutableLiveData<Boolean>()

    val encoderMode = MutableLiveData<Int>()


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
        encoderMode.value = 8
    }

    val encoderModeNames = mapOf(
        "Text Entry Mode" to visibleStates(visState1 = VISIBLE, visState2 = GONE, visState3 = GONE),
        "Morse Code Mode" to visibleStates (visState1 = GONE, visState2 = VISIBLE, visState3 = GONE),
        "Manual Mode" to visibleStates(visState1 = GONE, visState2 = GONE, visState3 = VISIBLE)
    )
    // TODO should make a constant somewhere with those text strings so I can edit them more easily


// TODO I bet I could handle this better than just spelling them all out. what If I want to add more to the dropdown?
    // TODO also consider that it appears that the views are ints, so I'm probably just going around this the hard way

   // val encoderModeString = encoderModeNames[encoderMode.value ?: 0] // I don't think I'm using this line...

    fun getVisStateFromName(modeName: String) : Int {
        return encoderModeNames.getOrDefault(
            modeName, visibleStates(visState1 = GONE, visState2 = GONE, visState3 = GONE))
            .visState1
    }
}

/*fun getEncoderModeName(modeInt: Int) : String {
    return EncoderModeNames[modeInt]
}*/



class visibleStates(
    val visState1: Int, val visState2: Int, val visState3: Int) {
}
