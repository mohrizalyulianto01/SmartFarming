package com.example.amanda

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.amanda.databinding.FragmentGreenhouseBinding
import com.example.amanda.restapi.RestApiManager
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class GreenhouseFragment: Fragment() {
    private var _binding: FragmentGreenhouseBinding? = null
    private val binding get() = _binding!!
    private lateinit var id: String
    lateinit var token: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGreenhouseBinding.inflate(inflater, container, false)
        val view = binding.root
        var intent = activity?.intent
        val sensors = intent!!.getSerializableExtra("sensors") as HashMap<String, String>?
        val items = sensors!!.keys.toList()
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)

        val sharedPref = requireActivity().getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        token = "Bearer " + sharedPref.getString("token", "")

        binding.sensorAutoCompleteTextView.setAdapter(adapter)

        id = requireActivity().intent.getStringExtra("id")!!

        val datePicker = MaterialDatePicker.Builder
            .datePicker()
            .setTitleText("Select date")
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()

        binding.dateTextInput.setOnClickListener {
            datePicker.show(parentFragmentManager, "tag")
        }

        datePicker.addOnPositiveButtonClickListener {
            val outputDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            binding.dateTextInput.setText(outputDateFormat.format(it))
        }

        binding.applyButton.setOnClickListener {
            val tmp = binding.sensorAutoCompleteTextView.text.toString()
            val date = binding.dateTextInput.text.toString()
            if(tmp.isNullOrBlank() || date.isNullOrBlank()){
                Toast.makeText(context, "Date or sensor empty", Toast.LENGTH_SHORT).show()
            }
            else{
                val sensor = sensors[tmp].toString()
                updateChart(date, sensor)
            }
        }

        //Part1

        //Part2
        val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var str_date = "2022-11-22 06:00:00"
        var date = formatter.parse(str_date) as Date
//        entries.add(Entry(date.time.toFloat(), 10f))

        //Part6
        binding.lineChart.xAxis.valueFormatter = LineChartXAxisValueFormatter()
//        binding.lineChart.data = LineData(vl)

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
        return view
    }

    fun updateChart(date: String, sensor: String)
    {
        val formatter: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val restApiManager = RestApiManager()
        val entries = ArrayList<Entry>()
        restApiManager.getData(token, id, sensor, date){
            val times: MutableList<Float> = mutableListOf()
            it!!.label.forEachIndexed { index, s ->
                var date = formatter.parse(s) as Date
                var entry = Entry(date.time.toFloat(), it.data[index])
//                var entry = Entry(s.toFloat(), it.data[index])
                entries.add(entry)
            }
            Log.d("ENTRIES", entries.toString())
            //Part3
            val vl = LineDataSet(entries, "Sensor")
//            //Part4
            vl.setDrawValues(false)
            vl.setDrawFilled(true)
            vl.lineWidth = 1f
            vl.setDrawFilled(false)
            vl.setDrawCircles(false)
            val colorsArray = intArrayOf(R.color.green)
            vl.setColors(colorsArray, context)
            binding.lineChart.data = LineData(vl)
            binding.lineChart.data.notifyDataChanged()
            binding.lineChart.notifyDataSetChanged()
            binding.lineChart.invalidate()
        }
    }
}