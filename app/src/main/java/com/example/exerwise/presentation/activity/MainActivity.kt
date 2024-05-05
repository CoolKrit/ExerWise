package com.example.exerwise.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.exerwise.R
import com.example.exerwise.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setBackgroundColor(getColor(R.color.surface))
    }
}