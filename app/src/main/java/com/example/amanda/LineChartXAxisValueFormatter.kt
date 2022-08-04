package com.example.amanda

import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class LineChartXAxisValueFormatter : IndexAxisValueFormatter() {
    override fun getFormattedValue(value: Float): String {

        val emissionsMilliSince1970Time = value.toLong()

        // Show time in local version

        // Show time in local version
        val timeMilliseconds = Date(emissionsMilliSince1970Time)
//        val dateTimeFormat: DateFormat =
//            DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())

//        return dateTimeFormat.format(timeMilliseconds)

        // Convert float value to date string
        // Convert from seconds back to milliseconds to format time  to show to the user
//        val emissionsMilliSince1970Time: Long = TimeUnit.SECONDS.toMillis(value.toLong())
        // Show time in local version
        // Show time in local version
//        val timeMilliseconds = Date(emissionsMilliSince1970Time)
        val dateTimeFormat = SimpleDateFormat("HH:mm:SS")

        return dateTimeFormat.format(timeMilliseconds)
    }
}
