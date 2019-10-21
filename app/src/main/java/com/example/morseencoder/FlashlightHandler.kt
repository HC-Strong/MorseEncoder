package com.example.morseencoder

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.CountDownTimer
import android.view.View
import timber.log.Timber

class FlashlightHandler(context: Context?) {
    private var cameraManager = context?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var cameraId : String = getCameraId(cameraManager)
    private var flashLightOn = false

    // transmit message
    fun sendMessage(view: View?,  morseLetters: List<MorseCode.Letter>) {
        Timber.v("logging view here to suppress warnings: $view")

        flashLightTimer(morseLetters)
    }

    private fun flashLightTimer( morseLetters: List<MorseCode.Letter>) {  //TODO:should I consider making the sendMessage function just be the timer not call it?
        val interval = 333L     // TODO: make that countDownInterval be adjustable by user through settings menu
        var letterNum = 0
        var beepNum = 0
        var curLetter: MorseCode.Letter
        var curBeep: Beep       // TODO: should this be morsecode.beep like the letter is morsecode.letter?
        var beepCount: Int

        val timer = object: CountDownTimer( 12*interval, interval ) { // TODO: set this back to calculate length but use actual duration not just count of letters (see above line for how it used to be)
            // TODO: I should probably override the onstart or whatever function so there's not a tick-length delay before it starts (and so I don't have to add 1 to the duration)
            override fun onTick(millisUntilFinished: Long) {
                Timber.i("Tick Tock: Letter $letterNum of ${morseLetters.count()}")

                curLetter = morseLetters[letterNum]
                curBeep = curLetter.code!![beepNum] // getting the beepNumth beep in the list of beeps that is the code attrib on the letter class
                beepCount = curLetter.code!!.count() // TODO: A) this only works for dots, not dashes and B) the Letter class should have a property that calculates this for me
                setFlashlightState(curBeep.isOn)

                if (beepNum < beepCount-1) {
                    beepNum++
                } else {
                    letterNum++
                    beepNum = 0
                }
                Timber.i("Tick Tock: the letter is ${curLetter.char} and the beep is $curBeep")
            }

            override fun onFinish() {
                Timber.i("Tick Tock: Message Sent!")}
        }
        timer.start()
    }


    private fun cameraAvailable(context : Context) : Boolean {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    private fun getCameraId(cameraManager: CameraManager) : String {
        // TODO figure out how does as CameraManager differ from : CameraManger?
        return cameraManager.cameraIdList[0]
    }

    fun toggleFlashlight(view: View?) {
        Timber.v("logging view here to suppress warnings: $view")
        if (!flashLightOn) {
            Timber.i("flashLightOn is $flashLightOn (toggle)")
            cameraManager.setTorchMode(cameraId, true)
            flashLightOn = true
        } else {
            Timber.i("flashLightOn is $flashLightOn (toggle)")
            cameraManager.setTorchMode(cameraId, false)
            flashLightOn = false
        }
    }
/// TODO this uses repeated code from the toggle method and should be modified to remove redundancy
    fun setFlashlightState(turnOn: Boolean = true) {
        if (turnOn) {
            Timber.i("flashLightOn is $flashLightOn")
            cameraManager.setTorchMode(cameraId, true)
            flashLightOn = true
        } else {
            Timber.i("flashLightOn is $flashLightOn")
            cameraManager.setTorchMode(cameraId, false)
            flashLightOn = false
        }
    }
}
