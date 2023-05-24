package com.example.photoeditor.photo_classes

import android.graphics.Bitmap

class ColourBalanceEffect(override val type: EffectType,
                          override val name: String,
                          private val valueRed: Float,
                          private val valueGreen: Float,
                          private val valueBlue: Float) : IEffect {

    override fun modifyPhoto(bitmap: Bitmap) {
        TODO("Not yet implemented")
    }

}