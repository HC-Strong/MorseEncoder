package com.example.morseencoder

object MorseCode {
    val MorseCharacters = mapOf(
        'A' to listOf(Beep.DOT, Beep.CHAR_PAUSE, Beep.DASH, Beep.CHAR_END),
        'H' to listOf(Beep.DOT, Beep.CHAR_PAUSE, Beep.DOT,  Beep.CHAR_PAUSE, Beep.DOT, Beep.CHAR_PAUSE, Beep.DOT, Beep.CHAR_END),
        'I' to listOf(Beep.DOT, Beep.CHAR_PAUSE, Beep.DOT,  Beep.CHAR_END),
        '|' to listOf(Beep.CHAR_END), //TODO: do I really need this mapping here?
        ' ' to listOf(Beep.SPACE)
    )
    const val timeUnit = 335 // TODO: either clean this up or actually use it somewhere. currently setting the interval in the timer but should eventually be a user-defined setting

/*    fun make(char: Char) { // put this here envisioning its use for encoding messages one user-entered character at a time: ie MorseCode.make('A')

    }*/

    class Letter(val char: Char){
        val code = MorseCharacters.get(char)
        val duration = code?.sumBy { it.duration }
    }

}

enum class Beep(val duration: Int, val isOn: Boolean) {
    DOT(1, isOn = true),
    DASH(3, isOn = true),
    CHAR_PAUSE(1, isOn = false),  // TODO: I kinda want to figure out how to just automatically add this, but for now I need to spell it out
    CHAR_END(3, isOn = false),
    SPACE(7, isOn = false)
} // TODO maybe I should have the CHAR_END and SPACE lengths be correct and subtract from them when they're actually sent?

/*  MORSE CODE RESOURCES
    https://en.wikipedia.org/wiki/Morse_code
    https://morsecode.scphillips.com/morse2.html
*/