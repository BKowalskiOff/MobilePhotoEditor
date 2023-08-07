package com.example.photoeditor.photo_classes

import android.graphics.Bitmap
import android.graphics.BitmapFactory

class Photo {

private val effects: MutableList<IEffect> = mutableListOf<IEffect>()
var original: Bitmap? = null
var preview: Bitmap? = null

    constructor(bitmap: Bitmap?) {
        this.original = bitmap
    }

    fun applyEffects(){
        //TODO: implement method that applies effects to the image
    }

}