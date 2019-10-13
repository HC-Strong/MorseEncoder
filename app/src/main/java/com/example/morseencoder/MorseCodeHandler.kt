package com.example.morseencoder

import android.content.Context
import timber.log.Timber
import java.util.*

class MorseCodeHandler {

    fun encodeString(message : String) : Map<Char, List<Beep>?> {
        //TODO: better than it was, but I still might be able to clean up the code if I make a class for the return type list
        val charArray = message.toCharArray()
        Timber.i("The message is $message")
        //val beepList = listOf<Beep>()
        val beepList: MutableMap<Char, List<Beep>?> = mutableMapOf()
        //var toAdd : Map.Entry<Char, List<Beep>>

        var newKey : Char
        var newValue : List<Beep>?

        for (char in charArray) {
            Timber.i("Current letter is $char")

            //var test = MorseCode.MorseCharacters.filter { it.key == char.toUpperCase() }
            newKey = char.toUpperCase()
            newValue  = MorseCode.MorseCharacters.get(newKey)
            //beepList.add(toAdd)
            beepList.put(newKey, newValue)
            Timber.i(beepList.size.toString())
        }


        Timber.i("The list is currently: $beepList")


        // TODO: this is sending, it should be separate from encoding!!!
        val timer = Timer()


        for (entry in beepList) {
                val task = testTimerTask(entry.value.toString())
                timer.schedule(task, 300)
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