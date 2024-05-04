package com.example.exerwise.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.exerwise.data.repository.AuthRepository

class ViewModelFactory(private val authRepository: AuthRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {
            return SignUpViewModel(authRepository) as T
        } else if (modelClass.isAssignableFrom(SignInViewModel::class.java)) {
            return SignInViewModel(authRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}