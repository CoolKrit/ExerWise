package com.example.exerwise.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class AuthRepository {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseStore = FirebaseFirestore.getInstance()

    fun signUpUser(email: String, password: String, name: String, callback: (Boolean) -> Unit) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userMap = hashMapOf("name" to name)
                    firebaseStore.collection("users").document(firebaseAuth.currentUser!!.uid)
                        .set(userMap)
                        .addOnSuccessListener {
                            firebaseAuth.currentUser!!.sendEmailVerification()
                            callback(true)
                        }
                        .addOnFailureListener { callback(false) }
                } else {
                    callback(false)
                }
            }
    }

    fun signInUser(email: String, password: String, callback: (Boolean) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
    }
}