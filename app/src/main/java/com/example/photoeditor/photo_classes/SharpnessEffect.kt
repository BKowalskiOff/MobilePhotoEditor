package com.example.photoeditor.photo_classes

import android.graphics.Bitmap
import android.graphics.Color
import kotlinx.coroutines.*

class SharpnessEffect(override val type: EffectType,
                      override val name: String) : IEffect {

    override fun modifyPhoto(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        //allocating two integer arrays, one for input image pixels and one for output image pixels
        val pixels = IntArray(width * height)
        val newPixels = IntArray(width * height)
        //filling input image array with pixel values from the bitmap
        bitmap.getPixels(pixels,0, width, 0, 0, width, height)
        runBlocking(Dispatchers.Default)  {
            for (y in 1 until height - 1) {
                launch {
                    for (x in 1 until width - 1) {
                        val colour = pixels[y * width + x]
                        if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                            newPixels[y * width + x] = colour
                            continue
                        }
                        val grad = calculateGradient(pixels, x, y, width)
                        val red = truncate(Color.red(colour) + grad)
                        val green = truncate(Color.green(colour) + grad)
                        val blue = truncate(Color.blue(colour) + grad)
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

    private fun calculateGradient(pixels: IntArray, dstX: Int, dstY: Int, width: Int): Int{
        var avg = 0
        for(x in dstX-1..dstX+1){
            for(y in dstY-1..dstY+1){
                if(x == dstX && y == dstY) continue
                val colour = pixels[y*width + x]
                avg += (Color.red(colour) + Color.green(colour) + Color.blue(colour))/3
            }
        }
        avg /= 8
        val colour = pixels[dstY*width + dstX]
        val brightness = (Color.red(colour) + Color.green(colour) + Color.blue(colour))/3
        return brightness - avg
    }

}