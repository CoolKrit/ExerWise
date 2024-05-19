package com.example.exerwise.presentation.fragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.exerwise.R
import com.example.exerwise.data.repository.AuthRepository
import com.example.exerwise.databinding.FragmentSignInBinding
import com.example.exerwise.presentation.viewmodel.SignInViewModel
import com.example.exerwise.presentation.viewmodel.SignUpViewModel
import com.example.exerwise.presentation.viewmodel.ViewModelFactory
import com.google.firebase.auth.FirebaseAuth

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var signInViewModel: SignInViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signInViewModel =
            ViewModelProvider(this, ViewModelFactory(AuthRepository()))[SignInViewModel::class.java]

        binding.signIn.setOnClickListener {
            val userEmail = binding.emailInputLayout.editText.text.toString().trim()
            val userPassword = binding.passwordInputLayout.editText.text.toString().trim()

            signInViewModel.signInUser(userEmail, userPassword)
                .observe(viewLifecycleOwner) { success ->
                    if (success) {
                        findNavController().navigate(R.id.mainActivity)
                        requireActivity().finish()
                    } else {
                        Toast.makeText(requireContext(), "Sign in failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }

        binding.forgotPassword.setOnClickListener {
            showResetPasswordDialog()
        }
        binding.signUpTextView.setOnClickListener { findNavController().navigate(R.id.signUpFragment) }
    }

    private fun showResetPasswordDialog() {
        val builder = AlertDialog.Builder(requireContext())
        val inflater = LayoutInflater.from(requireContext())
        val view = inflater.inflate(R.layout.dialog_reset_password, null)
        builder.setView(view)

        val dialog = builder.create()

        val emailEditText = view.findViewById<EditText>(R.id.editTextEmail)
        val resetPasswordButton = view.findViewById<Button>(R.id.buttonResetPassword)

        resetPasswordButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            if (TextUtils.isEmpty(email)) {
                Toast.makeText(requireContext(), "Please enter your email", Toast.LENGTH_SHORT).show()
            } else {
                FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(requireContext(), "Reset email sent", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        } else {
                            Toast.makeText(requireContext(), "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

        dialog.show()
    }
}