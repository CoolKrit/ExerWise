package com.example.exerwise

import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.exerwise.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseStore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseStore = FirebaseFirestore.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textViewSignIn = binding.signIn
        val buttonSignUp = binding.signUp

        buttonSignUp.setOnClickListener { signUpUser() }
        textViewSignIn.setOnClickListener { findNavController().navigate(R.id.signInFragment) }
    }

    private fun signUpUser() {
        val userName = binding.nameInputLayout.editText.text.toString().trim()
        val userEmail = binding.emailInputLayout.editText.text.toString().trim()
        val userPassword = binding.passwordInputLayout.editText.text.toString().trim()

        if (!validateData(
                userName = userName,
                userEmail = userEmail,
                userPassword = userPassword
            )
        ) return

        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener {
            if (it.isSuccessful) {
                val userMap = hashMapOf(
                    "name" to userName
                )
                firebaseStore.collection("users").document(firebaseAuth.currentUser!!.uid)
                    .set(userMap)

                firebaseAuth.currentUser!!.sendEmailVerification()
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), it.exception?.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun validateData(userName: String, userEmail: String, userPassword: String): Boolean {
        return when {
            userName.isEmpty() -> {
                binding.nameInputLayout.editText.error = "Name is empty!"
                false
            }

            userEmail.isEmpty() -> {
                binding.emailInputLayout.editText.error = "Email is empty!"
                false
            }

            userPassword.isEmpty() -> {
                binding.passwordInputLayout.editText.error = "Password is empty!"
                false
            }

            !Patterns.EMAIL_ADDRESS.matcher(userEmail).matches() -> {
                binding.emailInputLayout.editText.error = "Email is invalid!"
                false
            }

            else -> true
        }
    }
}