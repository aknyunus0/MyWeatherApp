package com.yunusAkin.myweatherapp


import android.content.Context
import android.util.Log
import android.widget.Toast

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import org.json.JSONObject


class WeatherDataApiService(context: Context) {

    val LATT_LONG: String =
        "https://www.metaweather.com/api/location/search/?lattlong="

    val _context:Context= context

 public interface  ListByLattLong {

    fun onErorr(message:String)
    fun onResponse(_closeLocationModel: List<CloseLocationModel>)
}



    fun getCloseCity(lattLong: String, listByLattLong:  ListByLattLong){
        var closeLocationCityList:MutableList<CloseLocationModel> = ArrayList()
        val url:String
        url=LATT_LONG+lattLong



        val request = JsonArrayRequest(Request.Method.GET, url, null,
            { response ->



                for (i in 0 until  response.length()){
                    val _CloseCity=CloseLocationModel()
                    val City: JSONObject = response.getJSONObject(i)
                    _CloseCity.title=City.getString("title")
                    _CloseCity.woeid=City.getString("woeid")
                    closeLocationCityList.add(_CloseCity)
                    Log.d("yunusakintaggg", _CloseCity.title)
                }

                listByLattLong.onResponse(closeLocationCityList)

            },
            { error ->
                Toast.makeText(_context,"hata"+error.message,Toast.LENGTH_SHORT).show()
            }
        )
 MySingleton.getInstance(_context).addToRequestQueue(request)

    }

}