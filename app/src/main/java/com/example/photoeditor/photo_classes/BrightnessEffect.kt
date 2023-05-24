package com.example.photoeditor.photo_classes

import android.graphics.Bitmap

class BrightnessEffect(override val type: EffectType,
                       override val name: String,
                       private val value: Int) : IEffect {

    override fun modifyPhoto(bitmap: Bitmap) {
        TODO("Not yet implemented")
    }

}