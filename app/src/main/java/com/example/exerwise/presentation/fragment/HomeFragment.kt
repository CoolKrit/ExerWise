package com.example.exerwise.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exerwise.R
import com.example.exerwise.WorkoutItemClickListener
import com.example.exerwise.data.model.Workout
import com.example.exerwise.databinding.FragmentHomeBinding
import com.example.exerwise.presentation.adapter.FinishedWorkoutAdapter
import com.example.exerwise.presentation.viewmodel.WorkoutViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WorkoutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid)
            .get().addOnSuccessListener {
                val name = it.get("name")
                binding.userNameGreetings.text = "Hi, ${name.toString()}!"
            }

        val recyclerView = binding.finishedWorkoutsRV
        val adapter = FinishedWorkoutAdapter()
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter
        adapter.setItemClickListener(object : WorkoutItemClickListener {
            override fun onWorkoutItemClick(workout: Workout) {
                val bundle = Bundle().apply { putParcelable("workout", workout) }
                findNavController().navigate(R.id.action_homeFragment_to_finishedWorkoutFragment, bundle)
            }
        })

        viewModel.finishedWorkoutList.observe(viewLifecycleOwner, Observer { workouts ->
            adapter.submitList(workouts.reversed())
        })
    }
}