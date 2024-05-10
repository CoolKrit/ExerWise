package com.example.exerwise.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.exerwise.R
import com.example.exerwise.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.mainFragmentContainer) as NavHostFragment).navController
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.bottomNavigationView.setupWithNavController(navController)

        /*replaceFragment(HomeFragment())

        binding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragment -> replaceFragment(HomeFragment())
                R.id.workoutFragment -> replaceFragment(WorkoutFragment())
                else -> {}
            }
            true
        }*/
    }

    override fun onStart() {
        super.onStart()
        /*val sharedPreferences = getSharedPreferences(
            "ItemListSharedPreferences",
            Context.MODE_PRIVATE
        )
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
        val navController = findNavController(binding.mainFragmentContainer.id)
        binding.bottomNavigationView.setupWithNavController(navController)*/
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}