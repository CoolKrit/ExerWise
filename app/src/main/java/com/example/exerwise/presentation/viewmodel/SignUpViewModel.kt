package com.example.exerwise.presentation.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exerwise.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun signUpUser(userName: String, userEmail: String, userPassword: String): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        if (validateData(userName, userEmail, userPassword)) {
            authRepository.signUpUser(userEmail, userPassword, userName) { success ->
                result.postValue(success)
            }
        }
        return result
    }

    private fun validateData(name: String, email: String, password: String): Boolean {
        return name.isNotEmpty() && email.isNotEmpty() &&
                password.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}