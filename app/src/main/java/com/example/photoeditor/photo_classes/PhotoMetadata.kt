package com.example.photoeditor.photo_classes

import java.text.SimpleDateFormat
import java.util.Date
import kotlin.reflect.typeOf

class PhotoMetadata(private val name: String,
                    private val resolution: Pair<Int, Int>,
                    private val timestamp: Long) {


    val bundled: HashMap<String, Any>
        get() {
            val data: HashMap<String, Any> = HashMap<String, Any>()

            data["Name"] = this.name
            data["Resolution"] = this.resolution.first.toString() + " x " + this.resolution.second.toString()
            data["Date"] =  SimpleDateFormat("dd-MM-yyyy").format(Date(this.timestamp))

            return data
        }

}