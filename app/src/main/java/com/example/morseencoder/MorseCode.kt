package com.example.morseencoder

import com.example.morseencoder.MorseCode.timeUnit

object MorseCode {
    val MorseCharacters = mapOf(
        'A' to listOf(Beep.DOT, Beep.DASH),
        'H' to listOf(Beep.DOT, Beep.DOT, Beep.DOT, Beep.DOT),
        'I' to listOf(Beep.DOT, Beep.DOT)
    )

    const val timeUnit = 300

}

enum class Beep(val length: Int) {
    DOT(1 * timeUnit),
    DASH(3 * timeUnit),
    WORD_SEPARATOR(7 * timeUnit)
}

/*  MORSE CODE RESOURCES
    https://en.wikipedia.org/wiki/Morse_code
    https://morsecode.scphillips.com/morse2.html
*/
