package com.example.photoeditor.photo_classes

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ColourBalanceEffect(override val type: EffectType,
                          override val name: String,
                          private val valueRed: Double,
                          private val valueGreen: Double,
                          private val valueBlue: Double) : IEffect {

    override fun modifyPhoto(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        //allocating two integer arrays, one for input image pixels and one for output image pixels
        val pixels = IntArray(width * height)
        val newPixels = IntArray(width * height)
        //filling input image array with pixel values from the bitmap
        bitmap.getPixels(pixels,0, width, 0, 0, width, height)
        val rCoef = getCoefficientR()
        val gCoef = getCoefficientG()
        val bCoef = getCoefficientB()
        runBlocking(Dispatchers.Default) {
            for (y in 0 until height) {
                launch {
                    for (x in 0 until width) {
                        val colour = pixels[y * width + x]
                        val red = truncate(Color.red(colour) + rCoef)
                        val green = truncate(Color.green(colour) + gCoef)
                        val blue = truncate(Color.blue(colour) + bCoef)
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

    private fun getCoefficientR(): Int{
        // 0 to 1 -> -255 to 255
        return -255 + (this.valueRed * 510).toInt()
    }
    private fun getCoefficientG(): Int{
        // 0 to 1 -> -255 to 255
        return -255 + (this.valueGreen * 510).toInt()
    }
    private fun getCoefficientB(): Int{
        // 0 to 1 -> -255 to 255
        return -255 + (this.valueBlue * 510).toInt()
    }

}