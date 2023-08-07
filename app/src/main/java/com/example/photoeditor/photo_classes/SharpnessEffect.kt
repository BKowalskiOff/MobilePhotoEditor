package com.example.photoeditor.photo_classes

import android.graphics.Bitmap
import android.graphics.Color

class SharpnessEffect(override val type: EffectType,
                      override val name: String,
                      private val value: Int) : IEffect {

    override fun modifyPhoto(bitmap: Bitmap): Bitmap {
        val bm = bitmap.copy(bitmap.config, true)
        for(y in 1 until bm.height-1){
            for(x in 1 until bm.width-1){
                val colour = bm.getPixel(x,y)
                val grad = calculateGradient(bitmap, x, y)
                val red = truncate(Color.red(colour) + grad)
                val green = truncate(Color.green(colour) + grad)
                val blue = truncate(Color.blue(colour) + grad)
                bm.setPixel(x,y, Color.rgb(red,green,blue))
            }
        }
        return bm
    }

    private fun calculateGradient(bitmap: Bitmap, dstX: Int, dstY: Int): Int{
        var avg = 0
        for(x in dstX-1..dstX+1){
            for(y in dstY-1..dstY+1){
                if(x == dstX && y == dstY) continue
                val colour = bitmap.getPixel(x,y)
                avg += (Color.red(colour) + Color.green(colour) + Color.blue(colour))/3
            }
        }
        avg /= 8
        val colour = bitmap.getPixel(dstX, dstY)
        val brightness = (Color.red(colour) + Color.green(colour) + Color.blue(colour))/3
        return brightness - avg
    }

    fun truncate(value: Int): Int{
        if(value < 0) return 0
        if(value > 255) return 255
        return value
    }
}