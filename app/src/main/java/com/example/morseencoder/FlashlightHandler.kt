package com.example.morseencoder

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.CountDownTimer
import android.view.View
import androidx.navigation.findNavController
import timber.log.Timber

class FlashlightHandler(context: Context?, private val viewModel: SharedViewModel) {
    private var cameraManager = context?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var cameraId : String = getCameraId(cameraManager)
    private var flashLightOn = false

    lateinit var timer: CountDownTimer

    // transmit message
    fun sendMessage(view: View?,  morseLetters: List<MorseCode.Letter>) {
        Timber.v("logging view here to suppress warnings: $view")

        flashLightTimer(view, morseLetters)
    }

    private fun flashLightTimer( view: View?, morseLetters: List<MorseCode.Letter>) {  //TODO:should I consider making the sendMessage function just be the timer not call it?
        val interval = 333L     // TODO: make that countDownInterval be adjustable by user through settings menu

        var letterNum = 1
        var beepNum = 1
        var beepProgress = 1

        var curLetter: MorseCode.Letter
        var curBeep: Beep       // TODO: should this be morsecode.beep like the letter is morsecode.letter?

        var beepCount: Int
        var beepDuration: Int

       val timerLength = morseLetters.sumBy { it.duration!! }.minus(Beep.CHAR_END.duration).plus(Beep.CHAR_PAUSE.duration) // reduce the pause at the end of the message from 3 units to 1 for better pacing



        timer = object: CountDownTimer( timerLength*interval, interval ) {

            override fun onTick(millisUntilFinished: Long) {

                curLetter = morseLetters[letterNum-1]
                curBeep = curLetter.code!![beepNum-1] // getting the beepNumth beep in the list of beeps that is the code attrib on the letter class
                beepCount = curLetter.code!!.count() // number of beeps in the current letter to track when complete
                beepDuration = curBeep.duration

                Timber.i("Tick Tock: Sending letter ${letterNum} of ${morseLetters.count()}: ${curLetter.char}...")
                //Timber.i("for beepNum $beepNum: beepCount is $beepCount, beepDuration is $beepDuration, and beepProgress is $beepProgress")
                setFlashlightState(curBeep.isOn)

                when {
                    beepProgress < beepDuration   -> {beepProgress++}
                    beepNum < beepCount           -> {beepNum++; beepProgress = 1}
                    else                          -> {letterNum++; beepNum = 1; beepProgress = 1; viewModel.curCharacter.value = viewModel.curCharacter.value?.plus(1) ?: 0}
                }
            }

            override fun onFinish() {
                Timber.i("Tick Tock: Message Sent!")
                viewModel.messageCancelled.value = false
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
            viewModel.lightOn.value = true // TODO: setting the local variable flashLightOn and the viewModel livedata lightOn seems pretty redundant. should switch to just using the livedata here and when it's turned off below
        } else {
            //Timber.i("flashLightOn is $flashLightOn")
            cameraManager.setTorchMode(cameraId, false)
            flashLightOn = false
            viewModel.lightOn.value = false
        }
    }
}
