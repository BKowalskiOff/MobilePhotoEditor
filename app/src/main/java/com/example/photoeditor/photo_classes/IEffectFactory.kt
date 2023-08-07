package com.example.photoeditor.photo_classes

class IEffectFactory {

    fun createEffect(type: EffectType, value: Int): IEffect?{
        return when(type){
            EffectType.BRIGHTNESS -> BrightnessEffect(type, "Brightness", value)
            EffectType.BLUR -> BlurEffect(type, "Blur", value)
            EffectType.SHARPNESS -> SharpnessEffect(type, "Sharpness", value)
            EffectType.GAMMA_CORRECTION -> GammaCorrectionEffect(type, "Gamma correction", value)
            EffectType.CONTRAST -> ContrastEffect(type, "Contrast", value)
            else -> null
        }
    }
    fun createEffect(type: EffectType, values: List<Int>): IEffect?{
        return when(type){
            EffectType.COLOUR_BALANCE -> ColourBalanceEffect(type, "Colour balance", values[0], values[1], values[2])
            else -> null
        }
    }
}