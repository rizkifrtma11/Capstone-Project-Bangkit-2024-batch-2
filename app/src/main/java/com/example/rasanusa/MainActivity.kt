package com.example.rasanusa

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.room.Room
import com.example.rasanusa.data.localdatabase.FoodHistoryRoomDatabase
import com.example.rasanusa.databinding.ActivityMainBinding
import com.example.rasanusa.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    lateinit var database: FoodHistoryRoomDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        setupBottomNavbar()
        setupRoomDB()
        setupIntent()
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
//                R.id.navigation_ -> {
//                    navView.visibility = View.GONE
//                }
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
            "ProfileFragment" -> navController.navigate(R.id.navigation_profile)
            "ScanFragment" -> navController.navigate(R.id.navigation_scan)
            "SubscriptionFragment" -> navController.navigate(R.id.navigation_subscription)
        }
    }

}