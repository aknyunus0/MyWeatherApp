package com.yunusAkin.myweatherapp

 data class CloseLocationModel (

        val distance: String=" ",
        var title :String=" ",
        val location_type: String=" ",
        var woeid: String=" ",
        val latt_long: String=" ",
        )

 {
     override fun toString(): String {
         return "CloseLocationModel(title='$title', woeid=$woeid)"
     }


 }