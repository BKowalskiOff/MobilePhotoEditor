package com.example.photoeditor.photo_classes

import android.graphics.Bitmap
import android.graphics.Color
import android.util.Log
import kotlinx.coroutines.*
import kotlin.math.PI
import kotlin.math.exp
import kotlin.math.max
import kotlin.system.measureTimeMillis

class BlurEffect(override val type: EffectType,
                 override val name: String,
                 private val value: Double) : IEffect {

    override fun modifyPhoto(bitmap: Bitmap): Bitmap {

        val width = bitmap.width
        val height = bitmap.height
        //allocating two integer arrays, one for input image pixels and one for output image pixels
        val pixels = IntArray(width * height)
        val newPixels = IntArray(width * height)
        //filling input image array with pixel values from the bitmap
        bitmap.getPixels(pixels,0, width, 0, 0, width, height)
        val radius = getRadius()
        val kernel = generateGaussianKernel(radius)
        //launching a new coroutine per row of the image to speed up the processing
        runBlocking(Dispatchers.Default)  {
            for (y in 0 until height) {
                launch {
                    for (x in 0 until width) {
                        newPixels[y * width + x] = calculateBlurredVal(pixels, kernel, x, y,
                            radius, width, height)
                    }
                }
            }
        }
        // creating an output bitmap with calculated pixels
        val newBitmap = Bitmap.createBitmap(width, height, bitmap.config)
        newBitmap.setPixels(newPixels, 0, width, 0, 0, width, height)
        return newBitmap
    }

    fun getRadius(): Int{
        // 0 to 1 -> 3 to 15
        return (this.value*12).toInt() + 3
    }

    private fun calculateBlurredVal(pixels: IntArray, kernel: Array<DoubleArray>, dstX: Int,
                                    dstY: Int, radius: Int, width: Int, height: Int): Int{
        var r = 0.0
        var g = 0.0
        var b = 0.0
        for(dx in -radius..radius){
            for(dy in -radius..radius){
                val x = dstX-dx
                val y = dstY-dy
                // if outside image bounds, move to the next iteration
                if (x < 0 || x >= width || y < 0 || y >= height) continue
                val colour = pixels[y*width + x]
                val weight = kernel[dx+radius][dy+radius]
                r += Color.red(colour).toDouble() * weight
                g += Color.green(colour).toDouble() * weight
                b += Color.blue(colour).toDouble() * weight
            }
        }
//        r *= 255
//        g *= 255
//        b *= 255
        return Color.rgb(r.toInt(), g.toInt(), b.toInt())
    }

    private fun generateGaussianKernel(radius: Int): Array<DoubleArray>{

        // minimum sigma value
        val sigma = max(radius/2, 1).toDouble()
        // ensuring an odd width of the kernel to always have a center value
        val kernelWidth = (2*radius + 1)

        val kernel = Array(kernelWidth){
            DoubleArray(kernelWidth)
        }

        var sum = 0.0

        for (x in -radius .. radius){
            for (y in -radius..radius){
                val exponent = -(x*x + y*y)/(2 * sigma * sigma)
                val value = exp(exponent)/(2 * PI * sigma * sigma)
                kernel[x+radius][y+radius] = value
                sum += value
            }
        }
        var newSum = 0.0
        // normalizing the kernel values so that they add up to 1
        for (x in -radius .. radius){
            for (y in -radius..radius){
                kernel[x+radius][y+radius] /= sum
                newSum += kernel[x+radius][y+radius]
            }
        }

        return kernel
    }

}