package com.example.exerwise.presentation.fragment

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.exerwise.R
import com.example.exerwise.data.repository.AuthRepository
import com.example.exerwise.databinding.FragmentSignUpBinding
import com.example.exerwise.presentation.viewmodel.SignUpViewModel
import com.example.exerwise.presentation.viewmodel.ViewModelFactory

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var signUpViewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        signUpViewModel =
            ViewModelProvider(this, ViewModelFactory(AuthRepository()))[SignUpViewModel::class.java]

        binding.signUp.setOnClickListener {
            val userName = binding.nameInputLayout.editText.text.toString().trim()
            val userEmail = binding.emailInputLayout.editText.text.toString().trim()
            val userPassword = binding.passwordInputLayout.editText.text.toString().trim()

            signUpViewModel.signUpUser(userName, userEmail, userPassword)
                .observe(viewLifecycleOwner) { success ->
                    if (success) {
                        findNavController().popBackStack()
                    } else {
                        Toast.makeText(requireContext(), "Sign up failed", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
        }
        binding.signInTextView.setOnClickListener { findNavController().navigate(R.id.signInFragment) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}