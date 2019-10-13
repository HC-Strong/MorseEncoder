package com.example.morseencoder

import android.content.Context
import timber.log.Timber
import java.util.*

class MorseCodeHandler {

    fun encodeString(message : String) : MutableList<Map<Char, List<Beep>?>> { // Yes, it's a list of maps of chars to  lists of beeps
        //TODO: I'm not sure having a list of maps of Chars to Lists of Beeps is the best way to go here... Maybe I can make a class for this
        val charArray = message.toCharArray()
        Timber.i("The message is $message")
        //val beepList = listOf<Beep>()
        val beepList: MutableList<Map<Char, List<Beep>?>> = arrayListOf()
        var toAdd : Map<Char, List<Beep>>

        for (char in charArray) {
            Timber.i("Current letter is $char")


            toAdd = MorseCode.MorseCharacters.filter { it.key == char.toUpperCase() }
            beepList.add(toAdd)
            Timber.i(MorseCode.MorseCharacters.get(char.toUpperCase()).toString())
        }


        Timber.i("The list is currently: $beepList")


        // TODO: this is sending, it should be separate from encoding!!!
        val timer = Timer()


        for (charMap in beepList) {
            for ( beepSet in charMap) {
                val task = testTimerTask(beepSet.value.toString())
                timer.schedule(task, 300)
            }
        }
        Timber.i("After processing, the list is $beepList")

        return beepList
    }

    fun transmitMessage(message: MutableList<Map<Char, List<Beep>?>>) {

    }

    fun holdFor(millisToWait : Int) {

    }

/*    inline fun Timer.schedule(
        delay: Long,
        crossinline action: TimerTask.() -> Unit
    ): TimerTask {
        return testTimerTask()
    }*/

    // gonna also need
    // send message that I'd like to be able to be called like
    // encodeString("this is my message").sendMessage()
    // which I think I need to like extend something somehow. Extend the list class? The beeps?
}

class testTimerTask(val string: String) : TimerTask() {
    override fun run() {
        Timber.i("Tick Tock: $string")

    }

}