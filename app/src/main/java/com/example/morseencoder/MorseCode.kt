package com.example.morseencoder

import com.example.morseencoder.MorseCode.timeUnit

object MorseCode {
    val MorseCharacters = mapOf(
        'A' to listOf(Beep.DOT, Beep.DASH),
        'H' to listOf(Beep.DOT, Beep.DOT, Beep.DOT, Beep.DOT),
        'I' to listOf(Beep.DOT, Beep.DOT),
        '|' to listOf(Beep.CHAR_END),
        ' ' to listOf(Beep.SPACE)
    )

    const val timeUnit = 300

}

enum class Beep(val length: Long, val isOn: Boolean) {
    DOT(1L * timeUnit, isOn = true),
    DASH(3L * timeUnit, isOn = true),
    CHAR_END(1L * timeUnit, isOn = false),
    SPACE(7L * timeUnit, isOn = false)
}

/*  MORSE CODE RESOURCES
    https://en.wikipedia.org/wiki/Morse_code
    https://morsecode.scphillips.com/morse2.html
*/