package com.example.morseencoder

import timber.log.Timber

class MorseCodeHandler {

    fun encodeString(message : String) : List<MorseCode.Letter> {
        //TODO: eventually, I might want a list of words (that contain letters)
        Timber.i("The full secret message is $message")

        val charArray = message.toCharArray().map {it.toUpperCase()}
        val morseLetters: MutableList<MorseCode.Letter> = arrayListOf()

        for (char in charArray) {
            Timber.i("Encoding: $char")
            morseLetters.add(MorseCode.Letter(char))
        }
        Timber.i("''$message'' has been encoded as ''${morseLetters.toString()}''")

        return morseLetters
    }


    // TODO: gonna also need
    // send message that I'd like to be able to be called like
    // encodeString("this is my message").sendMessage()
    // which I think I need to like extend something somehow. Extend the list class? The beeps?
}

