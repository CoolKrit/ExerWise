package com.example.exerwise.presentation.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.exerwise.App
import com.example.exerwise.R
import com.example.exerwise.databinding.FragmentProfileBinding
import com.example.exerwise.presentation.activity.MainActivity.Companion.DARK_THEME_ENABLED_KEY
import com.example.exerwise.presentation.activity.MainActivity.Companion.THEME_PREFERENCES
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences =
            requireActivity().getSharedPreferences(THEME_PREFERENCES, Context.MODE_PRIVATE)
        val isDarkThemeEnabled = sharedPreferences.getBoolean(DARK_THEME_ENABLED_KEY, false)

        binding.darkThemeSwitch.isChecked = isDarkThemeEnabled

        FirebaseFirestore.getInstance().collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("finishedWorkouts").get().addOnSuccessListener {
                val count = it.size()
                binding.workoutsTV.text = count.toString()
            }

        FirebaseFirestore.getInstance().collection("users")
            .document(FirebaseAuth.getInstance().currentUser!!.uid)
            .get().addOnSuccessListener {
                val name = it.get("name")
                binding.userName.text = name.toString()
            }

        val adapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = adapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Duration"
                1 -> "Volume"
                2 -> "Sets"
                else -> null
            }
        }.attach()

        binding.darkThemeSwitch.setOnCheckedChangeListener { _, isChecked ->
            sharedPreferences.edit().putBoolean(DARK_THEME_ENABLED_KEY, isChecked).apply()
            (binding.root.context.applicationContext as App).applyTheme(isChecked)
        }

        binding.logoutButton.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_signInUpActivity)
        }
    }

    class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int = 3

        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> DurationAnalysisFragment()
                1 -> VolumeAnalysisFragment()
                2 -> SetsAnalysisFragment()
                else -> throw IllegalStateException("Unexpected position $position")
            }
        }
    }
}