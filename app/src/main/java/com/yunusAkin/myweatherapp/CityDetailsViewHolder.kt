package com.yunusAkin.myweatherapp

import android.content.Context
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_city_details.*
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale;
class CityDetailsViewHolder (container: ViewGroup) :
        RecyclerView.ViewHolder(
                //xml deki verilere bakarak arayüz viewvi oluşturuyor
                LayoutInflater.from(container.context).inflate
                (
                        R.layout.item_weather_week_card,
                        container,
                        false
                )


        )  {

    val txtDayName: TextView = itemView.findViewById(R.id.txtDayName)
    val txtMinTemp: TextView = itemView.findViewById(R.id.txtMinTemp)
    val txtWeatherStateName: TextView = itemView.findViewById(R.id.txtWeatherStateName)
    val txtMaxTemp: TextView = itemView.findViewById(R.id.txtMaxTemp)
    val imgAbbr: ImageView = itemView.findViewById(R.id.imgAbbr)
    val appDate: TextView=itemView.findViewById(R.id.appDate)


    @RequiresApi(Build.VERSION_CODES.O)
    fun bind(weatherCitydDetailsModel: WeatherCitydDetailsModel) {

        var date=LocalDate.parse(weatherCitydDetailsModel.applicable_date)
        var dow:DayOfWeek=date.dayOfWeek
        txtDayName.text=dow.getDisplayName(TextStyle.SHORT, Locale.ENGLISH)
        txtMinTemp.text="Min: "+Math.round(weatherCitydDetailsModel.min_temp).toString()+"°C"
        txtWeatherStateName.text=weatherCitydDetailsModel.weather_state_name
        txtMaxTemp.text= "Max: "+Math.round(weatherCitydDetailsModel.max_temp).toString()+"°C"
        appDate.text=weatherCitydDetailsModel.applicable_date
        var uri: Uri
        uri= Uri.parse(weatherCitydDetailsModel.weather_state_abbr);
        Glide
            .with(imgAbbr.context)
            .load(uri) // the uri you got from Firebase
            .centerCrop()
            .into(imgAbbr)



    }

}

