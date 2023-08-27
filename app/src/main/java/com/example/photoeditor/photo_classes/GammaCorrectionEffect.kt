package com.example.photoeditor.photo_classes

import android.graphics.Bitmap
import android.graphics.Color
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.math.pow

class GammaCorrectionEffect(override val type: EffectType,
                            override val name: String,
                            private val value: Double) : IEffect {

    override fun modifyPhoto(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        //allocating two integer arrays, one for input image pixels and one for output image pixels
        val pixels = IntArray(width * height)
        val newPixels = IntArray(width * height)
        //filling input image array with pixel values from the bitmap
        bitmap.getPixels(pixels, 0, width, 0, 0, width, height)
        val coef = getCoefficient()
        runBlocking(Dispatchers.Default) {
        for (y in 0 until height) {
            launch {
                for (x in 0 until width) {
                    val colour = pixels[y * width + x]

                    var red = (255f * (Color.red(colour).toFloat() / 255f).pow(coef)).toInt()
                    red = truncate(red)
                    var green = (255f * (Color.green(colour).toFloat() / 255f).pow(coef)).toInt()
                    green = truncate(green)
                    var blue = (255f * (Color.blue(colour).toFloat() / 255f).pow(coef)).toInt()
                    blue = truncate(blue)
                    newPixels[y * width + x] = Color.rgb(red, green, blue)
                }
            }
        }
        }
        // creating an output bitmap with calculated pixels
        val newBitmap = Bitmap.createBitmap(width, height, bitmap.config)
        newBitmap.setPixels(newPixels, 0, width, 0, 0, width, height)
        return newBitmap
    }

    private fun getCoefficient(): Float{
        return 1f/(0.01f + 3f*value.toFloat())
    }

}