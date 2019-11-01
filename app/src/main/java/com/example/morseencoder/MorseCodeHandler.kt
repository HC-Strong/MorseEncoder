package com.example.morseencoder

import timber.log.Timber

class MorseCodeHandler {

    fun encodeString(message : String) : List<MorseCode.Letter> {
        //TODO: eventually, I might want a list of words (that contain letters)
        Timber.i("The full secret message is $message")

        val charArray = message.toCharArray().map {it.toUpperCase()}
        val sanitizedCharArray: MutableList<Char> = arrayListOf()
        val morseLetters: MutableList<MorseCode.Letter> = arrayListOf()
        val illegalCharacters: MutableList<Char> = arrayListOf()
        var morseLetter: MorseCode.Letter

        for (char in charArray) {
            morseLetter = MorseCode.Letter(char)

            if(morseLetter.code != null) { // TODO: I feel like there should be a better way to check for an error here. "try" or assert or something
                Timber.i("Encoding: $char as ${morseLetter.code.toString()}")  // TODO: note that the error didn't occur till app tried to send the null beep
                morseLetters.add(morseLetter)
                sanitizedCharArray.add(char)
            } else {
                Timber.i("Skipping illegal character: $char")
                illegalCharacters.add(char)
            }
        }
        val sanitizedMessage = sanitizedCharArray.toString()
        Timber.i("\"$sanitizedMessage\" has been encoded as \"${morseLetters.map {it.code.toString()}}\"")
        Timber.i("The following illegal characters were omitted from the message: $illegalCharacters")

        return morseLetters
    }


    // TODO: gonna also need
    // send message that I'd like to be able to be called like
    // encodeString("this is my message").sendMessage()
    // which I think I need to like extend something somehow. Extend the list class? The beeps?
}

