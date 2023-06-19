package com.nqt.vuive.activity

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.nqt.vuive.R
import kotlinx.android.synthetic.main.layout_item.view.imgPhim
import kotlinx.android.synthetic.main.layout_item.view.txtMieuTa
import kotlinx.android.synthetic.main.layout_item.view.txtTenPhim


class RvAdapter (
    var ds: List<OutData>
):RecyclerView.Adapter<RvAdapter.PhimViewHolder>(){
    //make class viewHolder
    inner class PhimViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    //ctrl + i
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhimViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item,parent,false)
        return PhimViewHolder(view)
    }

    override fun onBindViewHolder(holder: PhimViewHolder, position: Int) {
        holder.itemView.apply {
            txtMieuTa.text = ds[position].date
            txtTenPhim.text =ds[position].nameTree
            imgPhim.setImageResource(ds[position].image)
        }
    }
    override fun getItemCount(): Int {
        return ds.size
    }
}




