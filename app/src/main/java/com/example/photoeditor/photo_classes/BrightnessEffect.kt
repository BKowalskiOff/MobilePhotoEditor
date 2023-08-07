package com.example.photoeditor.photo_classes

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color

class BrightnessEffect(override val type: EffectType,
                       override val name: String,
                       private val value: Int) : IEffect {

    override fun modifyPhoto(bitmap: Bitmap): Bitmap {
        val bm = bitmap.copy(bitmap.config, true)
        val coef = getCoefficient()
        for(y in 0 until bm.height){
            for(x in 0 until bm.width){
                val colour = bm.getPixel(x,y)
                val red = clip(Color.red(colour) + coef)
                val green = clip(Color.green(colour) + coef)
                val blue = clip(Color.blue(colour) + coef)
                bm.setPixel(x,y,Color.rgb(red,green,blue))
            }
        }
        return bm
    }

    fun getCoefficient(): Int{
        return -255 + (this.value * 510)/1023
    }

    fun clip(value: Int): Int{
        if(value < 0) return 0
        if(value > 255) return 255
        return value
    }

}