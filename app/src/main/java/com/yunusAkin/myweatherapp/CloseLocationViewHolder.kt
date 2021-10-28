package com.yunusAkin.myweatherapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

 class CloseLocationViewHolder (container: ViewGroup)  :
    RecyclerView.ViewHolder(

        //xml deki verilere bakarak arayüz viewvi oluşturuyor
        LayoutInflater.from(container.context).inflate
            (
            R.layout.item_near_city_card,
            container,
            false
        )
    )  {



     val TxtCityName: TextView = itemView.findViewById(R.id.TxtCityName)
             // val TxtCityId: TextView = itemView.findViewById(R.id.TxtCityId)
    val cardview1:CardView=itemView.findViewById(R.id.cardview1)


    fun bind(closeLocationModel: CloseLocationModel) {
        val intent: Intent? = null
        TxtCityName.text = closeLocationModel.title
                // TxtCityId.text = closeLocationModel.woeid
        cardview1.setOnClickListener( object : View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(cardview1.context, CityDetailsActivity::class.java)
                intent.putExtra("Woeid",closeLocationModel.woeid)
                intent.putExtra("CityName",TxtCityName.text )
               cardview1.context.startActivity(intent)

            }




        })


    }
}