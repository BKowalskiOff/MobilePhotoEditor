package com.example.photoeditor.photo_classes

class IEffectFactory {

    fun createEffect(type: EffectType, value: Int): IEffect?{
        return when(type){
            EffectType.BRIGHTNESS -> BrightnessEffect(type, "Brightness", value)
            EffectType.BLUR -> BlurEffect(type, "Blur", value)
            EffectType.GAMMA_CORRECTION -> GammaCorrectionEffect(type, "Gamma correction", value)
            EffectType.CONTRAST -> ContrastEffect(type, "Contrast", value)
            else -> null
        }
    }
    fun createEffect(type: EffectType, rValue: Int, gValue: Int, bValue: Int): IEffect?{
        return when(type){
            EffectType.COLOUR_BALANCE -> ColourBalanceEffect(type, "Colour balance", rValue, bValue, gValue)
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