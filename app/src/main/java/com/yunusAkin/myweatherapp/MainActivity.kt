package com.yunusAkin.myweatherapp

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), LocationListener, InternetConnectivityReceiver.ConnectivityReceiverListener {

    private val locationPermissionCode = 2

    private var snackBar: Snackbar? = null
    val weatherDataApiService = WeatherDataApiService(this@MainActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerReceiver(InternetConnectivityReceiver(), IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))
        var locationManager: LocationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    locationPermissionCode
            )
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 5f, this)

    }

    fun Search(view: View) {

        val intent = Intent(this, SearchActivity::class.java)
        startActivity(intent)

    }


    override fun onLocationChanged(location: Location) {
        _RecycCloseLocation.layoutManager = LinearLayoutManager(this)
        weatherDataApiService.getCloseCity(
                location.latitude.toString() + "," + location.longitude.toString(),
                object : WeatherDataApiService.ListByLattLong {
                    override fun onErorr(message: String) {
                        Toast.makeText(this@MainActivity, "hata" + message, Toast.LENGTH_SHORT).show()
                    }

                    override fun onResponse(_closeLocationModel: List<CloseLocationModel>) {
                        _RecycCloseLocation.adapter = CloseLocationAdapter(_closeLocationModel)
                    }

                })
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if (requestCode == locationPermissionCode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onProviderEnabled(provider: String) {}
    override fun onProviderDisabled(provider: String) {}
    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showNetworkMessage(isConnected)
    }


    override fun onResume() {
        super.onResume()
        InternetConnectivityReceiver.internetConnectivityReceiverListener = this
    }

    private fun showNetworkMessage(isConnected: Boolean) {
        //Occurs if there is no internet connection
        if (!isConnected) {
            snackBar = Snackbar.make(findViewById(R.id.mainAct), "Not connection internet!", Snackbar.LENGTH_SHORT)
            snackBar?.duration = BaseTransientBottomBar.LENGTH_INDEFINITE
            snackBar?.show()
        } else {
            snackBar?.dismiss()
        }
    }
}


