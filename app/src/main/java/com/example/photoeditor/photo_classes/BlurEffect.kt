package com.example.photoeditor.photo_classes

import android.graphics.Bitmap
import android.graphics.Color

class BlurEffect(override val type: EffectType,
                 override val name: String,
                 private val value: Int) : IEffect {

    override fun modifyPhoto(bitmap: Bitmap): Bitmap {
        val bm = bitmap.copy(bitmap.config, true)
        for(y in 8 until bm.height-8){
            for(x in 8 until bm.width-8){
                val newColour = calculateBlurredVal(bitmap, x, y)
                bm.setPixel(x,y, newColour)
            }
        }
        return bm
    }

    private fun calculateBlurredVal(bitmap: Bitmap, dstX: Int, dstY: Int): Int{
        var avgR = 0
        var avgG = 0
        var avgB = 0
        var sum = 0
        for(x in dstX-8..dstX+8){
            for(y in dstY-8..dstY+8){
                val colour = bitmap.getPixel(x,y)
                if(x == dstX && y == dstY) {
                    avgR += 4 * Color.red(colour)
                    avgG += 4 * Color.green(colour)
                    avgB += 4 * Color.blue(colour)
                    sum += 4
                    continue
                }
                sum += 1
                avgR += Color.red(colour)
                avgG += Color.green(colour)
                avgB += Color.blue(colour)
            }
        }
        avgR /= sum
        avgG /= sum
        avgB /= sum
        return Color.rgb(avgR, avgG, avgB)
    }

}