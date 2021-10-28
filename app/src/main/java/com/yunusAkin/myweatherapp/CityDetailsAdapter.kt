package com.yunusAkin.myweatherapp

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CityDetailsAdapter(val cityDetailsList: List<WeatherCitydDetailsModel>):RecyclerView.Adapter<CityDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityDetailsViewHolder {
        return CityDetailsViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return cityDetailsList.size
    }

    override fun onBindViewHolder(holder: CityDetailsViewHolder, position: Int) {
        holder.bind(cityDetailsList[position])


    }

}
