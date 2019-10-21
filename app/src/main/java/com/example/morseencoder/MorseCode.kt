package com.example.morseencoder

import com.example.morseencoder.MorseCode.timeUnit

object MorseCode {
    val MorseCharacters = mapOf(
        'A' to listOf(Beep.DOT, Beep.CHAR_PAUSE, Beep.DASH, Beep.CHAR_PAUSE),
        'H' to listOf(Beep.DOT, Beep.CHAR_PAUSE, Beep.DOT,  Beep.CHAR_PAUSE, Beep.DOT, Beep.CHAR_PAUSE, Beep.DOT, Beep.CHAR_PAUSE),
        'I' to listOf(Beep.DOT, Beep.CHAR_PAUSE, Beep.DOT,  Beep.CHAR_PAUSE),
        '|' to listOf(Beep.CHAR_END),
        ' ' to listOf(Beep.SPACE)
    )
    const val timeUnit = 333

/*    fun make(char: Char) { // put this here envisioning its use for encoding messages one user-entered character at a time: ie MorseCode.make('A')

    }*/

    class Letter(val char: Char){
        val code = MorseCharacters.get(char)

    }

}

enum class Beep(val length: Long, val isOn: Boolean) {
    DOT(1L * timeUnit, isOn = true),
    DASH(3L * timeUnit, isOn = true),
    CHAR_PAUSE(1L * timeUnit, isOn = false),  // TODO: I kinda want to figure out how to just automatically add this, but for now I need to spell it out
    CHAR_END(2L * timeUnit, isOn = false),  // Note that this is 2 not 3 because each dot or dash will be ended with 1 unit of off
    SPACE(4L * timeUnit, isOn = false)      // Note that this is 4 instead of 7 because each character ends with 3 units of off
} // TODO maybe I should have the CHAR_END and SPACE lengths be correct and subtract from them when they're actually sent?

/*  MORSE CODE RESOURCES
    https://en.wikipedia.org/wiki/Morse_code
    https://morsecode.scphillips.com/morse2.html
*/