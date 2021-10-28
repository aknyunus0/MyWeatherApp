package com.yunusAkin.myweatherapp

import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(),InternetConnectivityReceiver.ConnectivityReceiverListener  {
    private var snackBar: Snackbar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        var CityName:MutableList<String> =ArrayList()
        var woeID:MutableList<Int> =ArrayList()
        val weatherDataApiService = WeatherDataApiService(this@SearchActivity)
        registerReceiver(InternetConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(text: String): Boolean {
                weatherDataApiService.getSearchQuery(text,
                    object : WeatherDataApiService.SearchQuery {
                        override fun onErorr(message: String) {
                            TODO("Not yet implemented")
                        }

                        override fun onResponse(searchModel: List<SearchModel>) {
                            CityName=ArrayList()
                            woeID=ArrayList()

                            for (i in 0 until searchModel.count() ){
                                CityName.add(searchModel.get(i).title)
                                woeID.add(searchModel.get(i).woeid)
                            }
                            val adapter = ArrayAdapter <String> (this@SearchActivity, android.R.layout.simple_list_item_1, CityName)
                           Query_list.adapter=adapter

                        }


                    })
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                return false
            }

        })


        Query_list.setOnItemClickListener{adapterView,view,i,l->
            val intent = Intent(this, CityDetailsActivity::class.java)
            intent.putExtra("Woeid",woeID[i].toString())
            intent.putExtra("CityName",CityName[i])
            startActivity(intent)
            Toast.makeText(this,woeID[i].toString(),Toast.LENGTH_LONG ).show()


        }


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
            snackBar = Snackbar.make(findViewById(R.id.searchAct), "Not connection internet!", Snackbar.LENGTH_SHORT)
            snackBar?.duration = BaseTransientBottomBar.LENGTH_INDEFINITE
            snackBar?.show()
        } else {
            snackBar?.dismiss()
        }
    }

}
