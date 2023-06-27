package com.example.photoeditor.photo_classes

import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Photo {

private val effects: MutableList<IEffect> = mutableListOf<IEffect>()
var bitmap: Bitmap? = null

    constructor(bitmap: Bitmap?) {
        this.bitmap = bitmap
    }

    fun applyEffects(){
        //TODO: implement method that applies effects to the image
    }

}