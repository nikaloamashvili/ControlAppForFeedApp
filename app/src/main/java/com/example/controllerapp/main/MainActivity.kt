package com.example.controllerapp.main

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.controllerapp.databinding.ActivityMainBinding
import com.example.controllerapp.model.ColorOption
import com.example.controllerapp.model.Orientation
import com.example.controllerapp.viewmodel.ConfigViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val cellIds = (1..10).toList()
    private val checkBoxes = mutableListOf<CheckBox>()
    private lateinit var binding: ActivityMainBinding
    private val viewModel: ConfigViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        restoreState(savedInstanceState)
        setupCheckboxes()
        setupColorButtons()
        setupOrientationButtons()


        binding.btnSend.setOnClickListener { sendConfiguration() }


// Observe ViewModel if UI needs to react
        viewModel.selectedCells.observe(this) { /* update UI if needed */ }
    }


    private fun restoreState(savedInstanceState: Bundle?) {
        val savedCheckedCells = savedInstanceState?.getIntegerArrayList("checked_cells")
        val savedColor = savedInstanceState?.getString("selected_color")
        val savedOrientation = savedInstanceState?.getString("selected_orientation")


        if (savedCheckedCells != null) viewModel.updateCells(savedCheckedCells)
        if (savedColor != null) ColorOption.fromName(savedColor)?.let { viewModel.updateColor(it) }
        if (savedOrientation != null) Orientation.fromName(savedOrientation)?.let { viewModel.updateOrientation(it) }
    }


    private fun setupCheckboxes() {
        cellIds.forEach { id ->
            val cb = CheckBox(this).apply { text = "Cell $id" }
            checkBoxes.add(cb)
            binding.cellCheckboxContainer.addView(cb)
        }


// initialize from VM
        val initial = viewModel.selectedCells.value ?: emptyList()
        checkBoxes.forEachIndexed { index, cb -> cb.isChecked = initial.contains(cellIds[index]) }


// listen for changes
        checkBoxes.forEachIndexed { index, cb ->
            cb.setOnCheckedChangeListener { _, _ ->
                val selected = checkBoxes.mapIndexedNotNull { i, c -> if (c.isChecked) cellIds[i] else null }
                viewModel.updateCells(selected)
            }
        }
    }
    private fun setupOrientationButtons() {
        val mapping = mapOf(
            "Horizontal" to Orientation.HORIZONTAL,
            "Vertical" to Orientation.VERTICAL
        )

        mapping.forEach { (label, option) ->
            val rb = RadioButton(this).apply { text = label }
            binding.orientationGroup.addView(rb)
            if (option == viewModel.selectedOrientation.value)
                binding.orientationGroup.check(rb.id)
            rb.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) viewModel.updateOrientation(option)
            }
        }
    }

    private fun sendConfiguration() {
        val selectedCells = viewModel.selectedCells.value ?: listOf(-1)
        val selectedOrientation = viewModel.selectedOrientation.value?.name
        val selectedColor = viewModel.selectedColor.value?.name

        val intent = Intent("com.example.displayapp.UPDATE_CONFIG").apply {
            // Explicit broadcast target (safer than implicit)
            setComponent(
                ComponentName(
                    "com.example.displayapp",
                    "com.example.displayapp.broadcast.ConfigUpdateReceiver"
                )
            )
            putIntegerArrayListExtra("visible_ids", ArrayList(selectedCells))
            putExtra("orientation", selectedOrientation)
            putExtra("color", selectedColor)
        }

        sendBroadcast(intent)
        Toast.makeText(
            this,
            "Sent: $selectedCells | $selectedOrientation | $selectedColor",
            Toast.LENGTH_SHORT
        ).show()
    }


    private fun setupColorButtons() {
        val mapping = mapOf(
            "Red" to ColorOption.RED,
            "Green" to ColorOption.GREEN,
            "Blue" to ColorOption.BLUE
        )


        mapping.forEach { (label, option) ->
            val rb = RadioButton(this).apply { text = label }
            binding.colorGroup.addView(rb)
            if (option == viewModel.selectedColor.value) binding.colorGroup.check(rb.id)
            rb.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) viewModel.updateColor(option)
            }
        }
    }}