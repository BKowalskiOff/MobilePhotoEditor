package com.example.photoeditor.photo_classes

import android.graphics.Bitmap

interface IEffect {
    val type: EffectType
    val name: String
    fun modifyPhoto(bitmap: Bitmap): Bitmap
}