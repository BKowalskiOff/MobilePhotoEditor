package com.example.photoeditor.photo_classes

import android.graphics.Bitmap
import android.graphics.Color
import kotlin.math.pow

class GammaCorrectionEffect(override val type: EffectType,
                            override val name: String,
                            private val value: Int) : IEffect {

    override fun modifyPhoto(bitmap: Bitmap): Bitmap {
        val bm = bitmap.copy(bitmap.config, true)
        val coef = getCoefficient()
        for(y in 0 until bm.height){
            for(x in 0 until bm.width){
                val colour = bm.getPixel(x,y)

                var red = (255f * (Color.red(colour).toFloat()/255f).pow(coef)).toInt()
                red = truncate(red)
                var green = (255f * (Color.green(colour).toFloat()/255f).pow(coef)).toInt()
                green = truncate(green)
                var blue = (255f * (Color.blue(colour).toFloat()/255f).pow(coef)).toInt()
                blue = truncate(blue)
                bm.setPixel(x,y, Color.rgb(red,green,blue))
            }
        }
        return bm
    }

    private fun getCoefficient(): Float{
        return 1f/(0.01f + 3f*value.toFloat()/1023f)
    }

}