package com.example.exerwise.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.exerwise.R
import com.example.exerwise.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.mainFragmentContainer) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setupWithNavController(navController)
    }

    companion object {
        const val THEME_PREFERENCES = "ThemePrefs"

        const val DARK_THEME_ENABLED_KEY = "isDarkThemeEnabled"
    }
}