package com.example.morseencoder

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.CountDownTimer
import android.view.View
import androidx.navigation.findNavController
import timber.log.Timber

class FlashlightHandler(context: Context?) {
    private var cameraManager = context?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var cameraId : String = getCameraId(cameraManager)
    private var flashLightOn = false

    // transmit message
    fun sendMessage(view: View?,  morseLetters: List<MorseCode.Letter>) {
        Timber.v("logging view here to suppress warnings: $view")

        flashLightTimer(view, morseLetters)
    }

    private fun flashLightTimer( view: View?, morseLetters: List<MorseCode.Letter>) {  //TODO:should I consider making the sendMessage function just be the timer not call it?
        val interval = 333L     // TODO: make that countDownInterval be adjustable by user through settings menu

        var letterNum = 0
        var beepNum = 0
        var beepProgress = 0

        var curLetter: MorseCode.Letter
        var curBeep: Beep       // TODO: should this be morsecode.beep like the letter is morsecode.letter?

        var beepCount: Int
        var beepDuration: Int

       val timerLength = morseLetters.sumBy { it.duration!! }



        val timer = object: CountDownTimer( timerLength*interval-1, interval ) {
            // TODO: I should probably override the onstart or whatever function so there's not a tick-length delay before it starts (and so I don't have to add 1 to the duration)
            override fun onTick(millisUntilFinished: Long) {

                curLetter = morseLetters[letterNum]
                curBeep = curLetter.code!![beepNum] // getting the beepNumth beep in the list of beeps that is the code attrib on the letter class
                beepCount = curLetter.code!!.count() // number of beeps in the current letter to track when complete
                beepDuration = curBeep.duration
                //Timber.i("BeepDuration is $beepDuration")
                //Timber.i("BeepNum is $beepNum")

                Timber.i("Tick Tock: Sending letter ${letterNum+1} of ${morseLetters.count()}: ${curLetter.char}...")
                setFlashlightState(curBeep.isOn)

                when {
                    beepProgress < beepDuration-1   -> {beepProgress++}
                    beepNum < beepCount-1           -> {beepNum++; beepProgress = 0}
                    else                            -> {letterNum++; beepNum = 0}
                }
            }

            override fun onFinish() {
                Timber.i("Tick Tock: Message Sent!")
                view?.findNavController()?.navigate(R.id.action_senderFragment_to_sentFragment)
            }
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
            //Timber.i("flashLightOn is $flashLightOn (toggle)")
            cameraManager.setTorchMode(cameraId, true)
            flashLightOn = true
        } else {
            //Timber.i("flashLightOn is $flashLightOn (toggle)")
            cameraManager.setTorchMode(cameraId, false)
            flashLightOn = false
        }
    }
/// TODO this uses repeated code from the toggle method and should be modified to remove redundancy
    fun setFlashlightState(turnOn: Boolean = true) {
        if (turnOn) {
            //Timber.i("flashLightOn is $flashLightOn")
            cameraManager.setTorchMode(cameraId, true)
            flashLightOn = true
        } else {
            //Timber.i("flashLightOn is $flashLightOn")
            cameraManager.setTorchMode(cameraId, false)
            flashLightOn = false
        }
    }
}
