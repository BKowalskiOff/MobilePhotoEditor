package com.example.photoeditor.photo_classes

enum class EffectType {
    BRIGHTNESS,
    BLUR,
    SHARPNESS,
    COLOUR_BALANCE,
    GAMMA_CORRECTION,
    CONTRAST;

    companion object {
        val names = listOf("Jasność", "Rozmycie", "Ostrość", "Balans kolorów", "Korekcja gamma", "Kontrast")
    }
}