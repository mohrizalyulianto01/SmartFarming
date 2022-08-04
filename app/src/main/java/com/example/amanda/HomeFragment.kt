package com.example.amanda

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amanda.adapter.GreenhouseAdapter
import com.example.amanda.data.GreenhouseDataSource
import com.example.amanda.data.model.Greenhouse

// TODO: Rename parameter arguments, choose names that match


class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var recyclerView: RecyclerView
    var greenhouses: List<Greenhouse> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view: View = inflater.inflate(R.layout.fragment_home, container, false)

//        var expandedSize =  ArrayList<Int>()
//        val myDataSet = GreenhouseDataSource().loadGreenhouse()
//        expandedSize = ArrayList()
//        for (i in 0 until myDataSet.count()) {
//            expandedSize.add(0)
//        }

        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.adapter = GreenhouseAdapter(requireContext(), greenhouses)
//        recyclerView.setHasFixedSize(true)

        return view
    }

    fun loadData(listGreenhouses: List<Greenhouse>){
        Log.d("LOADDATA", listGreenhouses.toString())
        greenhouses = listGreenhouses
        recyclerView.adapter = GreenhouseAdapter(requireContext(), greenhouses)
        recyclerView.adapter?.notifyDataSetChanged()
    }


}