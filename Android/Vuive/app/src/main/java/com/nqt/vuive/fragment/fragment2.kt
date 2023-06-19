package com.nqt.vuive.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nqt.vuive.R
import com.nqt.vuive.activity.OutData
import com.nqt.vuive.activity.RvAdapter

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [fragment1.newInstance] factory method to
 * create an instance of this fragment.
 */
class fragment2 : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_fragment2, container, false)
        val ds= mutableListOf<OutData>()
        ds.add(OutData(R.drawable.xuongrong1,"cây sương rồng","26 . 5 . 2023"))
        ds.add(OutData(R.drawable.xuongrong2,"cây sương rồng","26 . 5 . 2023"))
        ds.add(OutData(R.drawable.xuongrong3,"cây sương rồng","26 . 5 . 2023"))
        ds.add(OutData(R.drawable.xuongrong4,"cây sương rồng","26 . 5 . 2023"))


        val recyclerView: RecyclerView = view.findViewById(R.id.rvTab2)
        recyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        val adapter = RvAdapter(ds)
        recyclerView.adapter = adapter
//        val adapter = RvAdapter(ds)
//        rvDemo.adapter= adapter
//        rvDemo.layoutManager = LinearLayoutManager(
//            activity,
//            GridLayoutManager.VERTICAL,
//            false )
        return view
    }
}