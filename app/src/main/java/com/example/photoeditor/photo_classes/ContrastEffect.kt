package com.example.photoeditor.photo_classes

import android.graphics.Bitmap
import android.graphics.Color


// algorithm from site: https://www.dfstudios.co.uk/articles/programming/image-programming-algorithms/image-processing-algorithms-part-5-contrast-adjustment/
class ContrastEffect(override val type: EffectType,
                     override val name: String,
                     private val value: Int) : IEffect {

    override fun modifyPhoto(bitmap: Bitmap): Bitmap {
        val bm = bitmap.copy(bitmap.config, true)
        val coef = getCoefficient()
        for(y in 0 until bm.height){
            for(x in 0 until bm.width){
                val colour = bm.getPixel(x,y)
                var red = (coef*(Color.red(colour).toFloat()-128f)+128f).toInt()
                red = clip(red)
                var green = (coef*(Color.green(colour).toFloat()-128f) + 128f).toInt()
                red = clip(red)
                var blue = (coef*(Color.blue(colour).toFloat()-128f)+128f).toInt()
                red = clip(red)
                bm.setPixel(x,y, Color.rgb(red,green,blue))
            }
        }
        return bm
    }

    private fun getCoefficient(): Float{
        return 2f*value.toFloat()/1023f
    }

    private fun clip(value: Int): Int{
        if(value < 0) return 0
        if(value > 255) return 255
        return value
    }
}