package com.example.controllerapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.controllerapp.model.ColorOption
import com.example.controllerapp.model.Orientation
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor() : ViewModel() {
    private val _selectedCells = MutableLiveData<List<Int>>(emptyList())
    val selectedCells: LiveData<List<Int>> = _selectedCells

    private val _selectedColor = MutableLiveData<ColorOption>()
    val selectedColor: LiveData<ColorOption> = _selectedColor

    private val _selectedOrientation = MutableLiveData<Orientation>()
    val selectedOrientation: LiveData<Orientation> = _selectedOrientation

    fun updateCells(cells: List<Int>) {
        _selectedCells.value = cells.ifEmpty { listOf(-1) }
    }

    fun updateColor(color: ColorOption) {
        _selectedColor.value = color
    }

    fun updateOrientation(orientation: Orientation) {
        _selectedOrientation.value = orientation
    }
}