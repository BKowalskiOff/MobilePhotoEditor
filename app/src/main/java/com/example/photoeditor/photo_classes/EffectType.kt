package com.example.photoeditor.photo_classes

enum class EffectType {
    BRIGHTNESS,
    BLUR,
    SHARPNESS,
    COLOUR_BALANCE,
    GAMMA_CORRECTION,
    CONTRAST,
    GRAYSCALE;

    companion object {
        val names = listOf("Jasność", "Rozmycie", "Ostrość", "Balans kolorów", "Korekcja gamma", "Kontrast", "Skala szarości")
    }
}