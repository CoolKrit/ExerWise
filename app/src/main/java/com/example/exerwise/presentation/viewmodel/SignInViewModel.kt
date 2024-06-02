package com.example.exerwise.presentation.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exerwise.data.repository.AuthRepository

class SignInViewModel(private val authRepository: AuthRepository) : ViewModel() {

    fun signInUser(userEmail: String, userPassword: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        if (validateData(userEmail, userPassword)) {
            authRepository.signInUser(userEmail, userPassword) { success ->
                result.postValue(success)
            }
        }
        return result
    }

    private fun validateData(email: String, password: String): Boolean {
        return email.isNotEmpty() && password.isNotEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}