package com.example.morseencoder

import android.content.Context
import android.graphics.Bitmap
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur

internal object BlurBuilder {
    private val BITMAP_SCALE = 0.6f
    private val BLUR_RADIUS = 15f

    fun blur(context: Context, image:Bitmap):Bitmap {

        val width = Math.round(image.getWidth() * BITMAP_SCALE)
        val height = Math.round(image.getHeight() * BITMAP_SCALE)

        val inputBitmap = Bitmap.createScaledBitmap(image, width, height, false)
        val outputBitmap = Bitmap.createBitmap(inputBitmap)

        val rs = RenderScript.create(context)

        val intrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))
        val tmpIn = Allocation.createFromBitmap(rs, inputBitmap)
        val tmpOut = Allocation.createFromBitmap(rs, outputBitmap)

        intrinsicBlur.setRadius(BLUR_RADIUS)
        intrinsicBlur.setInput(tmpIn)
        intrinsicBlur.forEach(tmpOut)
        tmpOut.copyTo(outputBitmap)

        return outputBitmap
    }
}

// No longer using this, but keeping it around in case I want it for something later
// The code to use this class to blur the drawable called button_background_contained and set it as the background
    //for the sendMsgBtn is below

/*// Blur the button background //
val drawable: Drawable = resources.getDrawable(R.drawable.button_background_contained, context?.theme)
val bitmap = Bitmap.createBitmap(100, 73, Bitmap.Config.ARGB_8888)
val canvas = Canvas(bitmap)
drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
drawable.draw(canvas)

val buttonForBackground = binding.sendMsgBtn
val resultBmp = BlurBuilder.blur(context!!, bitmap)
buttonForBackground.background = resultBmp.toDrawable(resources)*/
