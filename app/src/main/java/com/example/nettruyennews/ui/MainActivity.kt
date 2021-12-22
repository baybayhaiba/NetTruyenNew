package com.example.nettruyennews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.nettruyennews.R
import com.example.nettruyennews.util.Constant
import com.example.nettruyennews.util.requestPermission
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.containerFragment) as NavHostFragment
//        navController = navHostFragment.navController
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_menu)

//        bottomNavigationView.setupWithNavController(navController)

        requestPermission(Constant.ALL_PERMISSION_ACTIVITY)

    }
}