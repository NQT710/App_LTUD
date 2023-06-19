package com.nqt.vuive.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nqt.vuive.R
import kotlinx.android.synthetic.main.layout_item.view.*
import kotlinx.android.synthetic.main.layout_item_plant_typle.view.*

class RvAdapterPlantsTypes (
    var ds: List<OutData>
):RecyclerView.Adapter<RvAdapterPlantsTypes.PhimViewHolder>(){
    //make class viewHolder
    inner class PhimViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    //ctrl + i
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhimViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_plant_typle,parent,false)
        return PhimViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhimViewHolder, position: Int) {
        holder.itemView.apply {
            txtPlantofPlant.text = ds[position].date
            txtPlantTyples.text =ds[position].nameTree
            imgPlantTyples.setBackgroundResource(ds[position].image)
        }
    }
    override fun getItemCount(): Int {
        return ds.size
    }
}




