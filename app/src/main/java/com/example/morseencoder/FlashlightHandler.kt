package com.example.morseencoder

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import timber.log.Timber
import java.util.*
import java.util.logging.Handler

class FlashlightHandler(context: Context?) {
    private var cameraManager = context?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var cameraId : String = getCameraId(cameraManager)
    private var flashLightOn = false

    private var countDown = 5L

    // transmit message by first turning on the light, then for each character/unit (ie pause) waiting
    //the designated amount of time and then setting the state
    fun sendMessage(view: View?, beepList: Map<Char, List<Beep>?>) {
        setFlashlightState(view, turnOn = true)
        Timber.i("Tick Tock: Light initial switch on")
        // TODO: go back and clean up commented out code in MorseCodeHandler

        flashLightTimer(beepList)


/*        var timer = Timer()
        var task : TestTimerTask
        var firstBeep = true


        for (entry in beepList) {
            Timber.i("The current character is ${entry.key}")
            for (beep in entry.value!!) {
                Timber.i("The current beep is $beep")
                task = TestTimerTask(view, beep.isOn, this)
                //timer = Timer()
                timer.schedule(task, 5000)  // TODO: make that 300 be adjustable by user through settings menu

            }
        }
        Timber.i("After processing, the list is $beepList")*/
    }

    private fun flashLightTimer(beepList: Map<Char, List<Beep>?>) {  //TODO:should I consider making the sendMessage function just be the timer not call it?
        val timer = object: CountDownTimer(countDown*1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDown--
                Timber.i("Tick Tock: $countDown... Beep is: $beepList[countdown]")
            }

            override fun onFinish() {
                Timber.i("Tick Tock: time's UP!")}
        }
        timer.start()
    }


    // OLD FAILED VERSION OF SEND MESSAGE FUNCTION FOR REF WHILE I WORK ON NEW VERSION!!!
    fun sendMessageFail(view: View?, beepList: Map<Char, List<Beep>?>) {
        setFlashlightState(view, turnOn = true)
        Timber.i("Tick Tock: Light initial switch on")
        // TODO: go back and clean up commented out code in MorseCodeHandler

        var timer = Timer()
        var task : TestTimerTask
        var firstBeep = true


        for (entry in beepList) {
            Timber.i("The current character is ${entry.key}")
            for (beep in entry.value!!) {
                Timber.i("The current beep is $beep")
                task = TestTimerTask(view, beep.isOn, this)
                //timer = Timer()
                timer.schedule(task, 5000)  // TODO: make that 300 be adjustable by user through settings menu
                //toggleFlashlight(view)
                /*if(beep.isOn) {
                    setFlashlightState(view, turnOn = false)
                    Timber.i("Tick Tock: Off!")
                    //setFlashlightState(view, turnOn = true)
                    //Timber.i("Tick Tock: On!")
                } else {
                    //setFlashlightState(view, turnOn = false)
                    //Timber.i("Tick Tock: Off!")
                }*/
                //FIRST BEEP DETECTION!!!!!
//                if(!firstBeep){
//                    //setFlashlightState(view, turnOn = false)
//                    //Timber.i("Tick Tock: Off!")
//                    task = TestTimerTask(view, false, this)
//                    timer = Timer()
//                    timer.schedule(task, beep.length*2000)
//                } else {
//                    firstBeep = false
//                }
            }
        }
        Timber.i("After processing, the list is $beepList")
    }

    class TestTimerTask(val view: View?, val isOn: Boolean, val flashlight: FlashlightHandler) : TimerTask() {

        override fun run() {
            flashlight.toggleFlashlight(view)
            //Timber.i("Tick Tock: Light is turning ${if(isOn) "off" else "on"}!")
            Timber.i("Tick Tock: inside the run method")

/*            if(isOn) {
                //flashlight.setFlashlightState(view, turnOn = false)
                //Timber.i("Tick Tock: Off!")
                flashlight.setFlashlightState(view, turnOn = true)
                Timber.i("Tick Tock: On!")
            } else {
                flashlight.setFlashlightState(view, turnOn = false)
                Timber.i("Tick Tock: Off!")
            }*/

        }
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
    fun setFlashlightState(view: View?, turnOn: Boolean = true) {
        Timber.v("logging view here to suppress warnings: $view")
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
