package com.example.amanda.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.amanda.GreenhouseActivity
import com.example.amanda.R
import com.example.amanda.data.model.Greenhouse


class GreenhouseAdapter(
    private val context: Context,
    private val dataset: List<Greenhouse>
    ) : RecyclerView.Adapter<GreenhouseAdapter.ItemViewHolder>() {

    private var currentPosition = 0

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val greenhouseNameTextView: TextView = view.findViewById(R.id.greenhouse_name)
        val greenhouseSubtitleTextView: TextView = view.findViewById(R.id.greenhouse_subtitle)
        val greenhouseLastUpdateTextView: TextView = view.findViewById(R.id.greenhouse_last_update)
        val itemTitle: LinearLayout = view.findViewById(R.id.item_title)
        val sensorsRecyclerView: RecyclerView = view.findViewById(R.id.sensors_recycler_view)
        val arrowImage: ImageView = view.findViewById(R.id.arrow_image)
        val detailButton: Button = view.findViewById(R.id.detail_button)
        var expanded = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_greenhouse, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val slideDown: Animation = AnimationUtils.loadAnimation(context, R.anim.slide_down)
        val greenhouse = dataset[position]
        val sensorAdapter = SensorAdapter(context, greenhouse.sensors)
        holder.sensorsRecyclerView.adapter = sensorAdapter
        holder.sensorsRecyclerView.addItemDecoration(EqualSpacingItemDecoration(
            5,
            EqualSpacingItemDecoration.HORIZONTAL
        ))
        holder.greenhouseNameTextView.text = greenhouse.name
        holder.greenhouseLastUpdateTextView.text = greenhouse.last_update
        val subtitle = greenhouse.location + " | " + greenhouse.commodity
        holder.greenhouseSubtitleTextView.text = subtitle
        holder.sensorsRecyclerView.visibility = View.GONE
        holder.detailButton.visibility = View.GONE
        holder.itemTitle.setOnClickListener(){
            if (!holder.expanded){
                holder.arrowImage.setImageResource(R.drawable.ic_baseline_arrow_drop_up_24)
                holder.sensorsRecyclerView.visibility = View.VISIBLE
                holder.detailButton.visibility = View.VISIBLE
                holder.sensorsRecyclerView.startAnimation(slideDown)
                holder.expanded = true
            }
            else{
                holder.arrowImage.setImageResource(R.drawable.ic_baseline_arrow_drop_down_24)
                holder.sensorsRecyclerView.visibility = View.GONE
                holder.detailButton.visibility = View.GONE
                holder.expanded = false
            }
//            notifyDataSetChanged()
        }

        val sensors = HashMap<String, String>()
        greenhouse.sensors.forEach {
            sensors[it.name] = it.variable_name
        }
        holder.detailButton.setOnClickListener {
            var intent = Intent(context, GreenhouseActivity::class.java)
            intent.putExtra("greenhouse", greenhouse.name)
            intent.putExtra("id", greenhouse.id)
            intent.putExtra("sensors", sensors)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}