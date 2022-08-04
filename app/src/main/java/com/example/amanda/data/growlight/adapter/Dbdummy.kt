package com.example.amanda.data.growlight.adapter


import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.amanda.R
import com.example.amanda.data.growlight.Growlight


class Dbdummy(resources: Resources) {

    private val initialWaktuList = waktulist(resources)
//    private val initialWaktuList = waktulist(resources)
    private val waktuLiveData = MutableLiveData(initialWaktuList)

    fun showgrowlighttime(): ArrayList<Growlight> {
        val growlist = ArrayList<Growlight>()
        return growlist

    }

    fun removeWaktu(growlight: Growlight) {
        val currentList = waktuLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(growlight)
            waktuLiveData.postValue(updatedList)
        }
    }

    fun addWaktu(growlight: Growlight) {
        val currentList = waktuLiveData.value
        if (currentList == null) {
            waktuLiveData.postValue(listOf(growlight))
        } else {
            val updatedList = currentList.toMutableList()
            updatedList.add(0, growlight)
            waktuLiveData.postValue(updatedList)
        }
    }

    fun getWaktuForId(id: Long): Growlight? {
        waktuLiveData.value?.let { growlight ->
            return growlight.firstOrNull{ it.id == id}
        }
        return null
    }

    fun getGrowlightList(): LiveData<List<Growlight>> {
        return waktuLiveData
//       Buat fungsi sorting list berdasarkan jam awal
    }

    companion object {
        private var INSTANCE: Dbdummy? = null

        fun getDataSource(resources: Resources): Dbdummy {
            return synchronized(Dbdummy::class) {
                val newInstance = INSTANCE ?: Dbdummy(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }


    fun waktulist (resources: Resources): List<Growlight> {
        return listOf(
            Growlight(
                id = 1,
                jamawal = String(),
                jamakhir = String()

            ))



    }


}














