package com.example.mishasampletask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFragment()
    }


    private fun initFragment() {
        // Initialize the fragment
        val fragment = InfoFragment() // Replace with your actual fragment class

        // Load the fragment
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragmentContainer,
                fragment
            ) // Replace "R.id.fragmentContainer" with the ID of the container in your activity layout
            .commit()


    }
}