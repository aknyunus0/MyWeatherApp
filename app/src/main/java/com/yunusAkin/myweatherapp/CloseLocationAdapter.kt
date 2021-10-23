package com.yunusAkin.myweatherapp

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CloseLocationAdapter(val closeLocationList: List<CloseLocationModel>) :
    RecyclerView.Adapter<CloseLocationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CloseLocationViewHolder {
        return CloseLocationViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return closeLocationList.size
    }

    override fun onBindViewHolder(holder: CloseLocationViewHolder, position: Int) {
        holder.bind(closeLocationList[position])

    }
}