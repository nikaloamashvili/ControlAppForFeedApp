package com.example.controllerapp.model

import android.graphics.Color

enum class ColorOption(val colorValue: Int) {
    RED(Color.RED),
    GREEN(Color.GREEN),
    BLUE(Color.BLUE);


    companion object {
        fun fromName(name: String?): ColorOption? = values().firstOrNull { it.name == name }
    }
}