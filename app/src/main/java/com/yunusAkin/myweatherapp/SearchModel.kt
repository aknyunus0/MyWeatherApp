package com.yunusAkin.myweatherapp

class SearchModel(

    var title: String=" ",
    var location_type: String=" ",
    var woeid: Int=0,
    var latt_long: String=" "
) {
    override fun toString(): String {
        return title
    }
}