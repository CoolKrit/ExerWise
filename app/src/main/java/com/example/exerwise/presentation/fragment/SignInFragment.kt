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
import com.example.exerwise.databinding.FragmentSignInBinding
import com.example.exerwise.presentation.viewmodel.SignInViewModel
import com.example.exerwise.presentation.viewmodel.SignUpViewModel
import com.example.exerwise.presentation.viewmodel.ViewModelFactory

class SignInFragment : Fragment() {
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    private lateinit var signInViewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        binding.signUpTextView.setOnClickListener { findNavController().navigate(R.id.signUpFragment) }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}