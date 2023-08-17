package com.example.photoeditor.photo_classes

class IEffectFactory {

    fun createEffect(type: EffectType, value: Double): IEffect?{
        return when(type){
            EffectType.BRIGHTNESS -> BrightnessEffect(type, "Brightness", value)
            EffectType.BLUR -> BlurEffect(type, "Blur", value)
            EffectType.GAMMA_CORRECTION -> GammaCorrectionEffect(type, "Gamma correction", value)
            EffectType.CONTRAST -> ContrastEffect(type, "Contrast", value)
            else -> null
        }
    }
    fun createEffect(type: EffectType, rValue: Double, gValue: Double, bValue: Double): IEffect?{
        return when(type){
            EffectType.COLOUR_BALANCE -> ColourBalanceEffect(type, "Colour balance", rValue, gValue, bValue)
            else -> null
        }
    }
    fun createEffect(type: EffectType): IEffect?{
        return when(type){
            EffectType.SHARPNESS -> SharpnessEffect(type, "Sharpness")
            else -> null
        }
    }
}