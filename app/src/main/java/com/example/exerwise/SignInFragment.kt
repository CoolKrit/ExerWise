package com.example.exerwise

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.exerwise.databinding.FragmentSignInBinding
import com.google.firebase.auth.FirebaseAuth


class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textViewSignUp = binding.signUp
        val buttonSignIn = binding.signIn

        buttonSignIn.setOnClickListener { signInUser() }
        textViewSignUp.setOnClickListener { findNavController().navigate(R.id.signUpFragment) }
    }

    private fun signInUser() {
        val userEmail = binding.emailInputLayout.editText.text.toString().trim()
        val userPassword = binding.passwordInputLayout.editText.text.toString().trim()

        if (!validateData(
                userEmail = userEmail,
                userPassword = userPassword
            )
        ) return

        firebaseAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener {
            if (it.isSuccessful) {
                findNavController().navigate(R.id.mainActivity)
                requireActivity().finish()
            } else {
                Toast.makeText(requireContext(), it.exception?.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun validateData(userEmail: String, userPassword: String): Boolean {
        return when {

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