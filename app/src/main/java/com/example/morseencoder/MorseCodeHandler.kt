package com.example.morseencoder

import timber.log.Timber

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


        Timber.i("After processing, the list is $beepList")
        return beepList
    }

    // gonna also need
    // encode char to loop over string
    // send message that I'd like to be able to be called like
    // encodeString("this is my message").sendMessage()
    // which I think I need to like extend something somehow. Extend the list class? The beeps?
}