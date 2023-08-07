package com.example.photoeditor.photo_classes

import android.graphics.Bitmap

class ColourBalanceEffect(override val type: EffectType,
                          override val name: String,
                          private val valueRed: Int,
                          private val valueGreen: Int,
                          private val valueBlue: Int) : IEffect {

    override fun modifyPhoto(bitmap: Bitmap): Bitmap {
        TODO("Not yet implemented")
    }

}