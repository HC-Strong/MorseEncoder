package com.example.morseencoder

object MorseCode {

    const val timeUnit = 335 // TODO: either clean this up or actually use it somewhere. currently setting the interval in the timer but should eventually be a user-defined setting

/*    fun make(char: Char) { // put this here envisioning its use for encoding messages one user-entered character at a time: ie MorseCode.make('A')

    }*/

    class Letter(val char: Char){
        val code = MorseCharacters.get(char)
        val duration = code?.sumBy { it.duration }
    }

    val MorseCharacters = mapOf(
        // LETTERS //
        'A' to listOf(Beep.DOT, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_END),   // · −
        'B' to listOf(Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_END),   // − · · ·
        'C' to listOf(Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_END),   // − · − ·
        'D' to listOf(Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_END),   // − · ·
        'E' to listOf(Beep.DOT, Beep.CHAR_END),   // ·
        'F' to listOf(Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_END),   // · · − ·
        'G' to listOf(Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_END),   // − − ·
        'H' to listOf(Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_END),   // · · · ·
        'I' to listOf(Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_END),   // · ·
        'J' to listOf(Beep.DOT, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_END),   // · − − −
        'K' to listOf(Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_END),   // − · −
        'L' to listOf(Beep.DOT, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_END),   // · − · ·
        'M' to listOf(Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_END),   // − −
        'N' to listOf(Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_END),   // − ·
        'O' to listOf(Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_END),   // − − −
        'P' to listOf(Beep.DOT, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_END),   // · − − ·
        'Q' to listOf(Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_END),   // − − · −
        'R' to listOf(Beep.DOT, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_END),   // · − ·
        'S' to listOf(Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_END),   // · · ·
        'T' to listOf(Beep.DASH, Beep.CHAR_END),   // −
        'U' to listOf(Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_END),   // · · −
        'V' to listOf(Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_END),   // · · · −
        'W' to listOf(Beep.DOT, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_END),   // · − −
        'X' to listOf(Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_END),   // − · · −
        'Y' to listOf(Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_END),   // − · − −
        'Z' to listOf(Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_END),   // − − · ·

        // NUMBERS //
        '0' to listOf(Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_END),   // − − − − −
        '1' to listOf(Beep.DOT, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_END),   // · − − − −
        '2' to listOf(Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_END),   // · · − − −
        '3' to listOf(Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_END),   // · · · − −
        '4' to listOf(Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_END),   // · · · · −
        '5' to listOf(Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_END),   // · · · · ·
        '6' to listOf(Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_END),   // − · · · ·
        '7' to listOf(Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_END),   // − − · · ·
        '8' to listOf(Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_END),   // − − − · ·
        '9' to listOf(Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DASH, Beep.CHAR_PAUSE,  Beep.DOT, Beep.CHAR_END),   // − − − − ·

        // Other //
        '|' to listOf(Beep.CHAR_END), //TODO: do I really need this mapping here?
        ' ' to listOf(Beep.SPACE)
    //TODO: really want to add the other stuff (puncutation and prosigns) as well
    )

}

enum class Beep(val duration: Int, val isOn: Boolean) {
    DOT(1, isOn = true),
    DASH(3, isOn = true),
    CHAR_PAUSE(1, isOn = false),  // TODO: I kinda want to figure out how to just automatically add this, but for now I need to spell it out
    CHAR_END(3, isOn = false),
    SPACE(4, isOn = false) // the space duration is supposed to be 7, not 4, but in most cases it will be preceded by a CHAR_END which is off for 3 units, so making the correction here
} // TODO maybe I should have the SPACE length be correct and subtract accordingly from the duration when the message is sent?

/*  MORSE CODE RESOURCES
    https://en.wikipedia.org/wiki/Morse_code
    https://morsecode.scphillips.com/morse2.html
*/