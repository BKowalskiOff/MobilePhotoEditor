package com.example.photoeditor.photo_classes

import android.graphics.Bitmap
import android.graphics.Color
import kotlinx.coroutines.*
import kotlin.math.max
import kotlin.math.min

class SharpnessEffect(override val type: EffectType,
                      override val name: String,
                      private val value: Double) : IEffect {

    private val laplacianKernel = arrayOf(
        intArrayOf(-1, -1, -1),
        intArrayOf(-1, 8, -1),
        intArrayOf(-1, -1, -1)
    )

    override fun modifyPhoto(bitmap: Bitmap): Bitmap {
        val width = bitmap.width
        val height = bitmap.height
        //allocating two integer arrays, one for input image pixels and one for output image pixels
        val pixels = IntArray(width * height)
        val newPixels = IntArray(width * height)
        //filling input image array with pixel values from the bitmap
        bitmap.getPixels(pixels,0, width, 0, 0, width, height)
        //runBlocking(Dispatchers.Default)  {
            for (y in 0 until height) {
                //launch {
                    for (x in 0 until width) {
                        val colour = pixels[y * width + x]
                        newPixels[y*width + x] = calculateSharpenedVal(pixels, laplacianKernel,
                            x, y, width, height)
//                        if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
//                            newPixels[y * width + x] = colour
//                            continue
//                        }
//                        val grad = calculateGradient(pixels, x, y, width)
//                        val red = truncate(Color.red(colour) + grad)
//                        val green = truncate(Color.green(colour) + grad)
//                        val blue = truncate(Color.blue(colour) + grad)
//                        newPixels[y * width + x] = Color.rgb(red, green, blue)
                    }
                //}
            //}
        }
        // creating an output bitmap with calculated pixels
        val newBitmap = Bitmap.createBitmap(width, height, bitmap.config)
        newBitmap.setPixels(newPixels, 0, width, 0, 0, width, height)
        return newBitmap
    }

    private fun calculateGradient(pixels: IntArray, dstX: Int, dstY: Int, width: Int): Int{
        var avg = 0
        for(x in dstX-1..dstX+1){
            for(y in dstY-1..dstY+1){
                if(x == dstX && y == dstY) continue
                val colour = pixels[y*width + x]
                avg += (Color.red(colour) + Color.green(colour) + Color.blue(colour))/3
            }
        }
        avg /= 8
        val colour = pixels[dstY*width + dstX]
        val brightness = (Color.red(colour) + Color.green(colour) + Color.blue(colour))/3
        return brightness - avg
    }

    private fun calculateSharpenedVal(pixels: IntArray, kernel: Array<IntArray>, dstX: Int,
                                    dstY: Int, width: Int, height: Int): Int{
        var r = 0
        var g = 0
        var b = 0
        for(dx in -1..1){
            for(dy in -1..1){
                // if outside of image, take a pixel from one row/column closer to the center
                val x = min(width - 1, max(0, dstX-dx))
                val y = min(height - 1, max(0, dstY-dy))
                val colour = pixels[y*width + x]
                val weight = kernel[dx + 1][dy + 1]
                r += Color.red(colour) * weight
                g += Color.green(colour) * weight
                b += Color.blue(colour) * weight
            }
        }

        val center = pixels[dstY*width + dstX]
        r = Color.red(center) + (r*this.value).toInt()
        g = Color.green(center) + (g*this.value).toInt()
        b = Color.blue(center) + (b*this.value).toInt()

        return Color.rgb(truncate(r), truncate(g), truncate(b))
    }

//    private fun calculateSharpenedVal(pixels: IntArray, kernel: Array<IntArray>, dstX: Int,
//                                      dstY: Int, width: Int, height: Int): Int{
//        var greyscaleVal = 0
//        for(dx in -1..1){
//            for(dy in -1..1){
//                // if outside of image, take a pixel from the central row and/or column
//                val x = min(width - 1, max(0, dstX-dx))
//                val y = min(height - 1, max(0, dstY-dy))
//                val colour = pixels[y*width + x]
//                val weight = kernel[dx + 1][dy + 1]
//                greyscaleVal += weight * (Color.red(colour) + Color.green(colour) + Color.blue(colour))/3
//            }
//        }
//        val previous = pixels[dstY*width + dstX]
//        val r = Color.red(previous)*greyscaleVal/255
//        val g = Color.green(previous)*greyscaleVal/255
//        val b = Color.blue(previous)*greyscaleVal/255
//        return Color.rgb(r, g, b)
//    }

}