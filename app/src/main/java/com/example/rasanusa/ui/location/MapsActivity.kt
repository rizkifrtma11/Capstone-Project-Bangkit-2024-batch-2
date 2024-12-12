package com.example.rasanusa.ui.location

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.rasanusa.R
import com.example.rasanusa.data.localdatabase.repository.locationData

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.rasanusa.databinding.ActivityMapsBinding
import com.example.rasanusa.vectorToBitmap
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true

        addFoodMarkers()
        getUserLocation()


    }

    private fun getUserLocation() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {

            mMap.isMyLocationEnabled = true
            mMap.uiSettings.isMyLocationButtonEnabled = true

            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    val userLatLng = LatLng(location.latitude, location.longitude)

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 15f))

                    checkNearbyFoods(userLatLng)
                }
            }
        }
    }

    private fun addFoodMarkers() {
        for (location in locationData) {
            val position = LatLng(location.latitude, location.longitude)
            mMap.addMarker(
                MarkerOptions()
                    .position(position)
                    .title(location.foodName)
                    .snippet("Asal: ${location.asal}")
                    .icon(vectorToBitmap(this, R.drawable.ic_marker_person_phone, Color.RED))
            )
        }
    }

    private fun checkNearbyFoods(userLatLng: LatLng) {
        for (zone in locationData) {
            val distance = FloatArray(1)
            Location.distanceBetween(
                userLatLng.latitude, userLatLng.longitude,
                zone.latitude, zone.longitude, distance
            )
        }
    }
}