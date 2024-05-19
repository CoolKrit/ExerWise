package com.example.exerwise.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.exerwise.R

class SignInUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in_up)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}