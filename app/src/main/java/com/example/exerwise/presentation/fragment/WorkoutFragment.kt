package com.example.exerwise.presentation.fragment

import WorkoutAdapter
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.exerwise.R
import com.example.exerwise.data.model.Workout
import com.example.exerwise.databinding.FragmentWorkoutBinding
import com.example.exerwise.presentation.viewmodel.WorkoutViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class WorkoutFragment : Fragment() {
    private var _binding: FragmentWorkoutBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WorkoutViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentWorkoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        binding.createWorkout.setOnClickListener {
            val navOptions = NavOptions.Builder().setPopUpTo(R.id.workoutFragment, true).build()
            findNavController().navigate(R.id.action_workoutFragment_to_createWorkoutFragment, null, navOptions)
        }
    }

    private fun setupRecyclerView() {
        val recyclerView = binding.workoutsRV
        val adapter = WorkoutAdapter(object : WorkoutAdapter.OptionsMenuClickListener {
            override fun onOptionsMenuClicked(position: Int, workout: Workout) {
                performOptionsMenuClick(position, workout)
            }
        }, startButtonClickListener = { workout ->
            val bundle = Bundle().apply {
                putParcelable("workout", workout)
            }
            findNavController().navigate(R.id.action_workoutFragment_to_startWorkoutFragment, bundle)
        })
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        observeWorkouts(adapter)
    }

    private fun observeWorkouts(adapter: WorkoutAdapter) {
        viewModel.createdWorkoutList.observe(viewLifecycleOwner, Observer { workouts ->
            adapter.submitList(workouts)
            binding.noWorkoutsText.visibility = if (workouts.isEmpty()) View.VISIBLE else View.GONE
        })
    }

    private fun performOptionsMenuClick(position: Int, workout: Workout) {
        val popupMenu = PopupMenu(
            requireContext(),
            binding.workoutsRV[position].findViewById(R.id.workoutMoreMenu)
        )
        popupMenu.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.editButton -> {
                        val bundle = Bundle().apply { putParcelable("workout", workout) }
                        findNavController().navigate(R.id.editWorkoutFragment, bundle)
                        return true
                    }

                    R.id.deleteButton -> {
                        viewModel.deleteWorkout(workout.id)
                        return true
                    }
                }
                return false
            }
        })
        popupMenu.inflate(R.menu.menu_more_item_workout)
        try {
            val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMPopup.isAccessible = true
            val mPopup = fieldMPopup.get(popupMenu)
            mPopup.javaClass
                .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(mPopup, true)
        } catch (e: Exception) {
            Log.e("Main", "Error showing menu icons.", e)
        } finally {
            popupMenu.show()
        }
    }
}