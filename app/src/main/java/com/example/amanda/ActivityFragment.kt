package com.example.amanda

import android.annotation.SuppressLint

import android.app.ActivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.amanda.MQTT.MQTT_HOST
import com.example.amanda.MQTT.MqttCilentHelper
import com.example.amanda.data.growlight.Growlight
import com.example.amanda.data.growlight.GrowlightListViewModelFactory
import com.example.amanda.data.growlight.GrowlightViewModel
import com.example.amanda.data.growlight.adapter.GrowlightAdapter
import com.example.amanda.databinding.FragmentActivityBinding
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallbackExtended
import org.eclipse.paho.client.mqttv3.MqttException
import org.eclipse.paho.client.mqttv3.MqttMessage
import java.time.LocalDateTime
import java.util.*

class ActivityFragment: Fragment() {
    private var _binding: FragmentActivityBinding? = null
    private val binding get() = _binding!!
    private lateinit var textview: TextView
    private var selectedHour: Int? = null
    private var selectedMinute: Int? = null
    private val growlightListViewModel by viewModels<GrowlightViewModel> {
        GrowlightListViewModelFactory(this)
    }
    var growlights : List<GrowlightAdapter> = listOf()
    private val mqttClient by lazy {
       MqttCilentHelper(this.requireContext())
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentActivityBinding.inflate(inflater, container, false)
        setMqttCallBack()
        setClickListener()


        val view = binding.root

//      Button Koneksi
        binding.publish.setOnClickListener {
            var snackbarMsg : String
            val topic = "Bebas"
            val msg = "Success Broker"
            snackbarMsg = try {
                mqttClient.publish(topic, msg)
                "Published to topic '$topic'"
            } catch (ex: MqttException) {
                "Error publishing to topic: $topic"
            }
            Snackbar.make(it, snackbarMsg, 300)
                .setAction("Action", null).show()
        }

        binding.connect.setOnClickListener { view ->
            var snackbarMsg : String
            val topic = "Bebas 21"
            snackbarMsg = "Cannot subscribe to empty topic!"
            if (topic.isNotEmpty()) {
                snackbarMsg = try {
                    mqttClient.subscribe(topic)
                    "Subscribed to topic '$topic'"
                } catch (ex: MqttException) {
                    "Error subscribing to topic: $topic"
                }
            }
            Snackbar.make(view, snackbarMsg, Snackbar.LENGTH_SHORT)
                .setAction("Action", null).show()


        }
        return view

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setClickListener(){
        binding.timegrowlightFrom.setOnClickListener {
            showTimePicker()
        }
        binding.btnOk.setOnClickListener {
            tampil()
        }

        binding.timegrowlightTo.setOnClickListener {
            showTimePicker2()
        }

//        meneylesaikan pengiriman mqtt

    }

    @SuppressLint("FragmentLiveDataObserve")
    fun tampil(){
        val linearLayoutManager = LinearLayoutManager(activity)
        linearLayoutManager.orientation = RecyclerView.VERTICAL
        binding.rvRecordjadwal.layoutManager = linearLayoutManager
        val growlightAdapter = GrowlightAdapter { growlight -> selectedHour }
        val concatAdapter = ConcatAdapter(growlightAdapter)

        growlightListViewModel.growlightLiveData.observe(this, {
            it?.let {
                growlightAdapter.submitList(it as MutableList<Growlight>)
            }
        })
        val recyclerView: RecyclerView = binding.rvRecordjadwal
        recyclerView.adapter = concatAdapter
        val jamMulai = binding.timegrowlightFrom.text
        val jamSampai = binding.timegrowlightTo.text
//            val duration = getDurationDescription(this, Duration.ofHours(1))
        growlightListViewModel.insertWaktu(jamMulai.toString(),jamSampai.toString())
//
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun showTimePicker() {
        val hour = selectedHour ?: LocalDateTime.now().hour
        val minute = selectedMinute ?: LocalDateTime.now().minute
        MaterialTimePicker.Builder()
            .setTitleText("Waktu GreenHouse")
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(hour)
            .setMinute(minute)
            .build()
            .apply {
                addOnPositiveButtonClickListener { onTimeSelected(this.hour, this.minute) }
            }.show(parentFragmentManager, MaterialTimePicker::class.java.canonicalName)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onTimeSelected(hour: Int, minute: Int) {
        selectedHour = hour
        selectedMinute = minute
        val hourAsText = if (hour < 10) "0$hour" else hour
        val minuteAsText = if (minute < 10) "0$minute" else minute

        "$hourAsText:$minuteAsText".also { view?.findViewById<TextView>(R.id.timegrowlight_from)?.text = it }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun showTimePicker2(){
        val hour = selectedHour ?: LocalDateTime.now().hour
        val minute = selectedMinute ?: LocalDateTime.now().minute
        MaterialTimePicker.Builder()
            .setTitleText("Waktu GreenHouse")
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(hour)
            .setMinute(minute)
            .build()
            .apply {
                addOnPositiveButtonClickListener { onTimeSelected2(this.hour, this.minute) }
            }.show(parentFragmentManager, MaterialTimePicker::class.java.canonicalName)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun onTimeSelected2(hour: Int, minute: Int) {
        selectedHour = hour
        selectedMinute = minute
        val hourAsText = if (hour < 10) "0$hour" else hour
        val minuteAsText = if (minute < 10) "0$minute" else minute

        "$hourAsText:$minuteAsText".also { view?.findViewById<TextView>(R.id.timegrowlight_to)?.text = it }
    }











    private fun setMqttCallBack() {
        val parent = binding.layout
        mqttClient.setCallback(object : MqttCallbackExtended {
            override fun connectComplete(b: Boolean, s: String) {
                val snackbarMsg = "Connected to host:\n'$MQTT_HOST'."
                Log.w("Debug", snackbarMsg)
                Snackbar.make(parent, snackbarMsg, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            override fun connectionLost(throwable: Throwable) {
                val snackbarMsg = "Connection to host lost:\n'$MQTT_HOST'"
                Log.w("Debug", snackbarMsg)
                Snackbar.make(parent, snackbarMsg, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            @Throws(Exception::class)
            override fun messageArrived(topic: String, mqttMessage: MqttMessage) {
                Log.w("Debug", "Message received from host '$MQTT_HOST': $mqttMessage")
                val str: String = "------------"+ Calendar.getInstance().time +"-------------\n$mqttMessage"
                textview.text = str
            }

            override fun deliveryComplete(iMqttDeliveryToken: IMqttDeliveryToken) {
                Log.w("Debug", "Message published to host '$MQTT_HOST'")
            }
        })
    }

    companion object{
        const val KEYKIRIMWAKTU = "KirimWaktu"
    }

}