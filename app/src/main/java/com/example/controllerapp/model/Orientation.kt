package com.example.controllerapp.model

enum class Orientation { HORIZONTAL, VERTICAL;
    companion object {
        fun fromName(name: String?): Orientation? = values().firstOrNull { it.name == name }
    }
}