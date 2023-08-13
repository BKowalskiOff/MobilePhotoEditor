package com.example.photoeditor.photo_classes

import android.graphics.Bitmap

interface IEffect {
    val type: EffectType
    val name: String
    fun modifyPhoto(bitmap: Bitmap): Bitmap

    fun truncate(value: Int): Int{
        if(value < 0) return 0
        if(value > 255) return 255
        return value
    }
}