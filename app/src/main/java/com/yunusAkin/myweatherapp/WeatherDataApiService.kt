package com.yunusAkin.myweatherapp


import android.content.Context
import android.util.Log
import android.widget.Toast

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONArray
import org.json.JSONObject


class WeatherDataApiService(context: Context) {

    //Api Url and Extensions
    val URL_API: String = "https://www.metaweather.com/"
    val LATT_LONG: String = "api/location/search/?lattlong="
    val LOCATION_CITY: String = "api/location/"
    val IMAGE_ABBR: String = "static/img/weather/"
    val SEARCH_QUERY = "api/location/search/?query="
    val _context: Context = context

    //maintains the latitude and longitude list. list can be used where implemented
    public interface ListByLattLong {

        fun onErorr(message: String)
        fun onResponse(_closeLocationModel: List<CloseLocationModel>)
    }
   // Keeps today's weather information. used where it is implemented
    public interface oneDayWeatherDetail {

        fun onErorr(message: String)
        fun onResponse(weatherCitydDetailsModel: WeatherCitydDetailsModel)
    }
//Maintains a list of weekly weather information. list can be used where implemented
    public interface weekWeatherDetail {

        fun onErorr(message: String)
        fun onResponse(weatherCitydDetailsModel: List<WeatherCitydDetailsModel>)
    }
//keeps the query list. list can be used where implemented
    public interface SearchQuery {
        fun onErorr(message: String)
        fun onResponse(searchModel: List<SearchModel>)
    }
//By taking city name and interface parameter, it creates a search model list, fills the list and keeps the list in the interface.
    fun getSearchQuery(cityName: String, searchQuery: SearchQuery) {
        var url: String
        url = URL_API + SEARCH_QUERY + cityName
        var searchModel: MutableList<SearchModel> = ArrayList()

        var request = JsonArrayRequest(Request.Method.GET, url, null, { response ->

            for (i in 0 until response.length()) {
                val _search = SearchModel()
                val City: JSONObject = response.getJSONObject(i)
                _search.title = City.getString("title")
                _search.woeid = City.getInt("woeid")
                _search.location_type = City.getString("location_type")
                _search.latt_long = City.getString("latt_long")
                searchModel.add(_search)

            }


            searchQuery.onResponse(searchModel)


        },
                { error ->

                    Toast.makeText(_context, "Error: " + error.message, Toast.LENGTH_SHORT).show()


                }

        )

        MySingleton.getInstance(_context).addToRequestQueue(request)
    }
//It takes city id and interface parameter and generate weekly weather detail model list and keep the list in interface.
    fun getWeekWeatherDetais(locationId: String, weekWeatherDetail: weekWeatherDetail) {
        val url: String
        url = URL_API + LOCATION_CITY + locationId
        var weatherCitydDetailsModel: MutableList<WeatherCitydDetailsModel> = ArrayList()
        var request = JsonObjectRequest(Request.Method.GET, url, null, { response ->


            val consolidated_weather: JSONArray = response.getJSONArray("consolidated_weather")

            for (i in 1 until consolidated_weather.length()) {
                val _weekWeatherDetail = WeatherCitydDetailsModel()
                val oneDay_fromApi: JSONObject = consolidated_weather.get(i) as JSONObject
                _weekWeatherDetail.id = oneDay_fromApi.getInt("id")
                _weekWeatherDetail.weather_state_name = oneDay_fromApi.getString("weather_state_name")
                _weekWeatherDetail.weather_state_abbr =
                        URL_API + IMAGE_ABBR + "ico/" + oneDay_fromApi.getString("weather_state_abbr") + ".ico"
                _weekWeatherDetail.wind_direction_compass = oneDay_fromApi.getString("wind_direction_compass")
                _weekWeatherDetail.created = oneDay_fromApi.getString("created")
                _weekWeatherDetail.applicable_date = oneDay_fromApi.getString("applicable_date")
                _weekWeatherDetail.min_temp = oneDay_fromApi.getDouble("min_temp")
                _weekWeatherDetail.max_temp = oneDay_fromApi.getDouble("max_temp")
                _weekWeatherDetail.the_temp = oneDay_fromApi.getDouble("the_temp")
                _weekWeatherDetail.wind_speed = oneDay_fromApi.getDouble("wind_speed")
                _weekWeatherDetail.wind_direction = oneDay_fromApi.getDouble("wind_direction")
                _weekWeatherDetail.air_pressure = oneDay_fromApi.getDouble("air_pressure")
                _weekWeatherDetail.humidity = oneDay_fromApi.getInt("humidity")
                _weekWeatherDetail.visibility = oneDay_fromApi.getDouble("visibility")
                _weekWeatherDetail.predictability = oneDay_fromApi.getInt("predictability")
                weatherCitydDetailsModel.add(_weekWeatherDetail)


            }


            weekWeatherDetail.onResponse(weatherCitydDetailsModel)

        },
                { error ->
                    Toast.makeText(_context, "Error: " + error.message, Toast.LENGTH_SHORT).show()
                }

        )

        MySingleton.getInstance(_context).addToRequestQueue(request)
    }

/*
* It takes city id and interface parameter and creates today's weather detail model object.
* Values from API are assigned to this object.
* The object is sent to the interface.
*/
    fun getOneWeatherDetail(locationId: String, oneDayWeatherDetail: oneDayWeatherDetail) {
        val url: String
        url = URL_API + LOCATION_CITY + locationId
        val request = JsonObjectRequest(Request.Method.GET, url, null,
                { response ->

                    val oneDay = WeatherCitydDetailsModel()
                    val consolidated_weather: JSONArray = response.getJSONArray("consolidated_weather")
                    val oneDay_fromApi: JSONObject = consolidated_weather.get(0) as JSONObject

                    oneDay.id = oneDay_fromApi.getInt("id")
                    oneDay.weather_state_name = oneDay_fromApi.getString("weather_state_name")
                    oneDay.weather_state_abbr =
                            URL_API + IMAGE_ABBR + "png/" + oneDay_fromApi.getString("weather_state_abbr") + ".png"
                    oneDay.wind_direction_compass = oneDay_fromApi.getString("wind_direction_compass")
                    oneDay.created = oneDay_fromApi.getString("created")
                    oneDay.applicable_date = oneDay_fromApi.getString("applicable_date")
                    oneDay.min_temp = oneDay_fromApi.getDouble("min_temp")
                    oneDay.max_temp = oneDay_fromApi.getDouble("max_temp")
                    oneDay.the_temp = oneDay_fromApi.getDouble("the_temp")
                    oneDay.wind_speed = oneDay_fromApi.getDouble("wind_speed")
                    oneDay.wind_direction = oneDay_fromApi.getDouble("wind_direction")
                    oneDay.air_pressure = oneDay_fromApi.getDouble("air_pressure")
                    oneDay.humidity = oneDay_fromApi.getInt("humidity")
                    oneDay.visibility = oneDay_fromApi.getDouble("visibility")
                    oneDay.predictability = oneDay_fromApi.getInt("predictability")


                    oneDayWeatherDetail.onResponse(oneDay)

                },
                { error ->
                    Toast.makeText(_context, "Error: " + error.message, Toast.LENGTH_SHORT).show()
                }
        )

        MySingleton.getInstance(_context).addToRequestQueue(request)


    }
//It takes latitude, longitude and interface as parameters. and values from api are assigned to the nearest city model list. The list is kept in the interface.

    fun getCloseCity(lattLong: String, listByLattLong: ListByLattLong) {
        var closeLocationCityList: MutableList<CloseLocationModel> = ArrayList()
        val url: String
        url = URL_API + LATT_LONG + lattLong


        val request = JsonArrayRequest(Request.Method.GET, url, null,
                { response ->


                    for (i in 0 until response.length()) {
                        val _CloseCity = CloseLocationModel()
                        val City: JSONObject = response.getJSONObject(i)
                        _CloseCity.title = City.getString("title")
                        _CloseCity.woeid = City.getString("woeid")
                        closeLocationCityList.add(_CloseCity)
                        Log.d("yunusakintaggg", _CloseCity.title)
                    }

                    listByLattLong.onResponse(closeLocationCityList)

                },
                { error ->
                    Toast.makeText(_context, "Error: " + error.message, Toast.LENGTH_SHORT).show()
                }
        )
        MySingleton.getInstance(_context).addToRequestQueue(request)

    }

}