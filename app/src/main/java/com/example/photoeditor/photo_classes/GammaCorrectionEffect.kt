package com.example.photoeditor.photo_classes

import android.graphics.Bitmap

class GammaCorrectionEffect(override val type: EffectType,
                            override val name: String,
                            private val value: Float) : IEffect {

    override fun modifyPhoto(bitmap: Bitmap) {
        TODO("Not yet implemented")
    }

}