package com.yunusAkin.myweatherapp

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_city_details.*
import kotlinx.android.synthetic.main.activity_main.*

class CityDetailsActivity : AppCompatActivity(), InternetConnectivityReceiver.ConnectivityReceiverListener {
    private var snackBar: Snackbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_details)
        val intent = intent
        val woeID = intent.getStringExtra("Woeid")
        val cityName = intent.getStringExtra("CityName")

        registerReceiver(InternetConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        btnClose.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })
        txtCityName.text = cityName;

        val weatherDataApiService = WeatherDataApiService(this@CityDetailsActivity)
        weatherDataApiService.getOneWeatherDetail(woeID.toString(), object : WeatherDataApiService.oneDayWeatherDetail {
            override fun onErorr(message: String) {
                TODO("Not yet implemented")
            }

            override fun onResponse(weatherCitydDetailsModel: WeatherCitydDetailsModel) {

                txtTheTemp.text = Math.round(weatherCitydDetailsModel.the_temp).toString() + "°C"
                txtMinTemp.text = "Min: " + Math.round(weatherCitydDetailsModel.min_temp) + "°C"
                txtMaxTemp.text = "Max: " + Math.round(weatherCitydDetailsModel.max_temp) + "°C"
                var uri: Uri
                uri = Uri.parse(weatherCitydDetailsModel.weather_state_abbr);
                Glide
                        .with(this@CityDetailsActivity)
                        .load(uri) // the uri you got from Firebase
                        .centerCrop()
                        .into(imgAbbr)
                txtVisibility.text = "Visibility: " + Math.round(weatherCitydDetailsModel.visibility) + " miles"
                txthumidity.text = "Humidity: " + weatherCitydDetailsModel.humidity + "%"
                txtPressure.text = "Pressure: " + Math.round(weatherCitydDetailsModel.air_pressure) + "mb"
                txtWeatherStateName.text = weatherCitydDetailsModel.weather_state_name

            }

        })

        RecycWeekWeather.layoutManager = GridLayoutManager(this, 3)
        weatherDataApiService.getWeekWeatherDetais(woeID.toString(), object : WeatherDataApiService.weekWeatherDetail {
            override fun onErorr(message: String) {
                TODO("Not yet implemented")
            }

            override fun onResponse(weatherCitydDetailsModel: List<WeatherCitydDetailsModel>) {
                RecycWeekWeather.adapter = CityDetailsAdapter(weatherCitydDetailsModel)

            }


        })

    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }


    override fun onResume() {
        super.onResume()
        InternetConnectivityReceiver.internetConnectivityReceiverListener = this
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        if (!isConnected) {
            snackBar = Snackbar.make(findViewById(R.id.detailsAct), "Not connection internet!", Snackbar.LENGTH_SHORT)
            snackBar?.duration = BaseTransientBottomBar.LENGTH_INDEFINITE
            snackBar?.show()
        } else {
            snackBar?.dismiss()
        }
    }
}