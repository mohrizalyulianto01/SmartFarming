package com.example.amanda

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.amanda.databinding.ActivityDetailBinding
import com.example.amanda.databinding.ActivityMainBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }

        val items =
            listOf("Sensor EC", "Sensor DO", "Sensor Temperature", "Sensor CO2", "Sensor pH")
        val adapter = ArrayAdapter(this, R.layout.list_item, items)
        binding.sensorAutoCompleteTextView.setAdapter(adapter)

        val datePicker = MaterialDatePicker.Builder
            .datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        binding.dateTextInput.setOnClickListener {
            datePicker.show(supportFragmentManager, "tag")
        }

        datePicker.addOnPositiveButtonClickListener {
            val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            binding.dateTextInput.setText(outputDateFormat.format(it))
        }

        //Part1
        val entries = ArrayList<Entry>()

        //Part2
        entries.add(Entry(1f, 10f))
        entries.add(Entry(2f, 2f))
        entries.add(Entry(3f, 7f))
        entries.add(Entry(4f, 20f))
        entries.add(Entry(5f, 16f))

        //Part3
        val vl = LineDataSet(entries, "Sensor ph")

        //Part4
        vl.setDrawValues(false)
        vl.setDrawFilled(true)
        vl.lineWidth = 3f
        vl.setDrawFilled(false)
        val colorsArray = intArrayOf(R.color.green)
        vl.setColors(colorsArray, this)

        //Part6
        binding.lineChart.data = LineData(vl)

        //Part7
        binding.lineChart.axisRight.isEnabled = false

        //Part8
        binding.lineChart.setTouchEnabled(true)
        binding.lineChart.setPinchZoom(true)

        //Part9
        binding.lineChart.description.text = "Date"
        binding.lineChart.setNoDataText("No data yet!")

        //Part10
        binding.lineChart.animateX(1800, Easing.EaseInExpo)
    }
}