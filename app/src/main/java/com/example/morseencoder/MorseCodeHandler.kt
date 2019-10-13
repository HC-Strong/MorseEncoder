package com.example.morseencoder

import android.content.Context
import timber.log.Timber
import java.util.*

class MorseCodeHandler {

    fun encodeString(message : String) : Map<Char, List<Beep>?> {
        //TODO: better than it was, but I still might be able to clean up the code if I make a class for the return type list
        val charArray = message.toCharArray()
        Timber.i("The message is $message")
        val beepList: MutableMap<Char, List<Beep>?> = mutableMapOf()

        var newKey : Char
        var newValue : List<Beep>?
        var firstLetter = true

        for (char in charArray) {
            Timber.i("Current letter is $char")
            if (!firstLetter) {
                beepList.put('|', listOf(Beep.CHAR_END)) // TODO: there must be a way to grab this by looking up the key or value from MorseCode.kt, not recreate it here
            } else {
                firstLetter = false
            }

            newKey = char.toUpperCase()
            newValue  = MorseCode.MorseCharacters.get(newKey)
            beepList.put(newKey, newValue)
            Timber.i(beepList.size.toString())
        }


        Timber.i("The list is currently: $beepList")


/*        // TODO: this is sending, it should be separate from encoding!!!
        val timer = Timer()




        for (entry in beepList) {
            Timber.i("The current entry is $entry")
            for (beep in entry.value!!) {
                Timber.i("The current beep is $beep")
                val task = testTimerTask(beep.toString())
                timer.schedule(task, 300)
            }
        }
        Timber.i("After processing, the list is $beepList")*/

        return beepList
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

/*class testTimerTask(val string: String) : TimerTask() {
    override fun run() {
        Timber.i("Tick Tock: $string")

    }
}*/
