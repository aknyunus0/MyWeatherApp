package com.yunusAkin.myweatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import androidx.recyclerview.widget.LinearLayoutManager


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val weatherDataApiService = WeatherDataApiService(this@MainActivity)
        _RecycCloseLocation.layoutManager = LinearLayoutManager(this)
        weatherDataApiService.getCloseCity("40.84466784884701,31.136923975966003", object :WeatherDataApiService.ListByLattLong{
            override fun onErorr(message: String) {
                Toast.makeText(this@MainActivity,"hata"+message,Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(_closeLocationModel: List<CloseLocationModel>) {
                _RecycCloseLocation.adapter = CloseLocationAdapter(_closeLocationModel)
            }

        })









    }
}


