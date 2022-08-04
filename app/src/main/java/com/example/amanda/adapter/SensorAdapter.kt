package com.example.amanda.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.amanda.R
import com.example.amanda.data.model.Greenhouse
import com.example.amanda.data.model.Sensor

class SensorAdapter(
    private val context: Context,
    private val dataset: List<Sensor>
) : RecyclerView.Adapter<SensorAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val sensorNameTextView: TextView = view.findViewById(R.id.sensorName)
        val sensorLastValueTextView: TextView = view.findViewById(R.id.sensorLastValue)
        val sensorUnitTextView: TextView = view.findViewById(R.id.sensorUnit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_sensor, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val sensor = dataset[position]
        holder.sensorNameTextView.text = sensor.name
        holder.sensorLastValueTextView.text = sensor.last_value
        holder.sensorUnitTextView.text = sensor.unit
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}