package com.example.rasanusa.ui.mainactivity

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.example.rasanusa.R
import com.example.rasanusa.data.localdatabase.repository.locationData
import com.example.rasanusa.data.localdatabase.roomdatabase.FoodHistoryRoomDatabase
import com.example.rasanusa.databinding.ActivityMainBinding
import com.example.rasanusa.ui.login.LoginActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    lateinit var database: FoodHistoryRoomDatabase
    private lateinit var fusedLocationClient : FusedLocationProviderClient

    private fun permissionCheck() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_LOCATION_PERMISSION
            )
        }else{
            notificationPermissionCheck()
        }
    }

    private fun notificationPermissionCheck(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.POST_NOTIFICATIONS), REQUEST_NOTIFICATION_PERMISSION)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_LOCATION_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, getString(R.string.location_granted), Toast.LENGTH_SHORT).show()
                    notificationPermissionCheck()
                } else {
                    Toast.makeText(this, getString(R.string.location_needed), Toast.LENGTH_SHORT).show()
                }
            }

            REQUEST_NOTIFICATION_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, getString(R.string.notification_granted), Toast.LENGTH_SHORT).show()
                    setupLocationNotification()
                } else {
                    Toast.makeText(this, getString(R.string.notification_needed), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }else{
            permissionCheck()
        }

        setupBottomNavbar()
        setupRoomDB()
        setupIntent()
    }

    @SuppressLint("MissingPermission")
    private fun setupLocationNotification() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            if (location != null) {
                val userLatLng = LatLng(location.latitude, location.longitude)
                checkNearbyFoods(userLatLng)
            } else {
                Log.d("Location", "Lokasi tidak ditemukan")
            }
        }.addOnFailureListener { exception ->
            Log.e("Location", "Gagal mendapatkan lokasi: ${exception.message}")
        }
    }

    private fun checkNearbyFoods(userLatLng: LatLng) {
        for (zone in locationData) {
            val distance = FloatArray(1)
            Location.distanceBetween(
                userLatLng.latitude, userLatLng.longitude,
                zone.latitude, zone.longitude, distance
            )
            if (distance[0] <= zone.radius) {
                Log.d("Geofencing", "Berada di dalam zona ${zone.asal}")
                showNotification(getString(R.string.food_nearby), getString(R.string.text_notification, zone.asal, zone.foodName) )
                break
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun showNotification(title: String, message: String){
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel("GEOFENCE", "Geofence Notifications", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, "GEOFENCE")
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.mipmap.ic_rasanusa_launcher)
            .build()

        notificationManager.notify(1, notification)
    }

    private fun setupRoomDB() {
        database = Room.databaseBuilder(
            applicationContext,
            FoodHistoryRoomDatabase::class.java, "rasanusa"
        ).allowMainThreadQueries().build()
    }

    private fun setupBottomNavbar(){
        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_scan -> {
                    navView.visibility = View.GONE
                }
                R.id.navigation_history -> {
                    navView.visibility = View.GONE
                }
                R.id.navigation_subscription -> {
                    navView.visibility = View.GONE
                }
                R.id.navigation_chatbot -> {
                    navView.visibility = View.GONE
                }
                else -> {
                    navView.visibility = View.VISIBLE
                }
            }
        }

    }

    private fun setupIntent() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        val navigateTo = intent.getStringExtra("navigate_to")
        when(navigateTo){
            "HomeFragment" -> navController.navigate(R.id.navigation_home)
            "ProfileFragment" -> navController.navigate(R.id.navigation_profile)
            "ScanFragment" -> navController.navigate(R.id.navigation_scan)
            "SubscriptionFragment" -> navController.navigate(R.id.navigation_subscription)
        }
    }

    companion object {
        private const val REQUEST_LOCATION_PERMISSION = 1
        private const val REQUEST_NOTIFICATION_PERMISSION= 2
    }

}