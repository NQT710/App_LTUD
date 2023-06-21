package com.nqt.vuive.fragment

import android.graphics.Bitmap
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.nqt.vuive.R
import kotlinx.android.synthetic.main.fragment_add_new_plant_detail.imageProfile

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [addNewPlantDetail.newInstance] factory method to
 * create an instance of this fragment.
 */
class addNewPlantDetail : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_new_plant_detail, container, false)
        // Inflate the layout for this fragment
        val ImageView = view.findViewById<ImageView>(R.id.imageProfile)

        val imageBitmap = arguments?.getParcelable<Bitmap>("data")
        if (imageBitmap != null) {
            ImageView.setImageBitmap(imageBitmap)
        }

        return view
    }


}