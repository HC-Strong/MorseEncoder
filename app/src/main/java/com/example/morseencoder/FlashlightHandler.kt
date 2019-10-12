package com.example.morseencoder

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.camera2.CameraManager
import android.view.View
import timber.log.Timber

class FlashlightHandler(context: Context?) {
    private var cameraManager = context?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private var cameraId : String = getCameraId(cameraManager)
    private var flashLightOn = false


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
            Timber.i("flashLightOn is $flashLightOn")
            cameraManager.setTorchMode(cameraId, true)
            flashLightOn = true
        } else {
            Timber.i("flashLightOn is $flashLightOn")
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
