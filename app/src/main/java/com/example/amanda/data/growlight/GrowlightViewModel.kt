package com.example.amanda.data.growlight

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.amanda.ActivityFragment
import com.example.amanda.data.growlight.adapter.Dbdummy
import kotlin.random.Random

class GrowlightViewModel(val dbsource: Dbdummy) : ViewModel(){

    val growlightLiveData = dbsource.getGrowlightList()


    fun insertWaktu(waktuAwal: String?, waktuAkhir: String?) {
        if (waktuAwal == null || waktuAkhir == null) {
            return
        }

        val newGrowlight= Growlight(
            Random.nextLong(),
            waktuAwal,
            waktuAkhir
        )

        dbsource.addWaktu(newGrowlight)
    }

    fun getWaktuForId(id: Long) : Growlight? {
        return dbsource.getWaktuForId(id)
    }



    fun removeWaktu(growlight: Growlight) {
        dbsource.removeWaktu(growlight)
    }



}
class GrowlightListViewModelFactory(private val context: ActivityFragment) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GrowlightViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GrowlightViewModel(
                dbsource = Dbdummy.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}