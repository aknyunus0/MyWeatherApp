package com.yunusAkin.myweatherapp

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CloseLocationViewHolder(container: ViewGroup) :
    RecyclerView.ViewHolder(
        //xml deki verilere bakarak arayüz viewvi oluşturuyor
        LayoutInflater.from(container.context).inflate
            (
            R.layout.item_near_city_card,
            container,
            false
        )
    ) {

    val TxtCityName: TextView = itemView.findViewById(R.id.TxtCityName)
    val TxtCityId: TextView = itemView.findViewById(R.id.TxtCityId)


    fun bind(closeLocationModel: CloseLocationModel) {

        TxtCityName.text = closeLocationModel.title
        TxtCityId.text = closeLocationModel.woeid.toString()

    }
}