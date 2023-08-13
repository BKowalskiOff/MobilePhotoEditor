package com.example.photoeditor.photo_classes

import android.graphics.Bitmap
import android.graphics.Color


// algorithm from site: https://www.dfstudios.co.uk/articles/programming/image-programming-algorithms/image-processing-algorithms-part-5-contrast-adjustment/
class ContrastEffect(override val type: EffectType,
                     override val name: String,
                     private val value: Int) : IEffect {

    override fun modifyPhoto(bitmap: Bitmap): Bitmap {

        val width = bitmap.width
        val height = bitmap.height
        //allocating two integer arrays, one for input image pixels and one for output image pixels
        val pixels = IntArray(width * height)
        val newPixels = IntArray(width * height)
        //filling input image array with pixel values from the bitmap
        bitmap.getPixels(pixels,0, width, 0, 0, width, height)
        val coef = getCoefficient()
        for(y in 0 until height){
            for(x in 0 until width){
                val colour = pixels[y*width + x]
                var red = (coef*(Color.red(colour).toFloat()-128f)+128f).toInt()
                red = truncate(red)
                var green = (coef*(Color.green(colour).toFloat()-128f) + 128f).toInt()
                red = truncate(red)
                var blue = (coef*(Color.blue(colour).toFloat()-128f)+128f).toInt()
                red = truncate(red)
                newPixels[y*width + x] = Color.rgb(red,green,blue)
            }
        }
        // creating an output bitmap with calculated pixels
        val newBitmap = Bitmap.createBitmap(width, height, bitmap.config)
        newBitmap.setPixels(newPixels, 0, width, 0, 0, width, height)
        return newBitmap
    }

    private fun getCoefficient(): Float{
        return 1.5f*value.toFloat()/1023f
    }

}