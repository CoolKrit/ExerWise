package com.example.exerwise.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.exerwise.databinding.FragmentProfileBinding
import com.example.exerwise.presentation.viewmodel.WorkoutViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val viewModel: WorkoutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().currentUser!!.uid)
            .collection("finishedWorkouts").get().addOnSuccessListener {
                val count = it.size()
                binding.workoutsTV.text = count.toString()
            }

        val chart: BarChart = binding.chart

        viewModel.workouts.observe(viewLifecycleOwner, Observer { workouts ->
            val entries = ArrayList<BarEntry>()
            val labels = ArrayList<String>()

            workouts.forEachIndexed { index, workout ->
                val durationInSeconds = parseDuration(workout.duration)
                entries.add(BarEntry(index.toFloat(), durationInSeconds.toFloat()))
                labels.add(formatDate(workout.id))
            }

            val dataSet = BarDataSet(entries, "Workout Duration")
            val barData = BarData(dataSet)

            chart.data = barData

            chart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            chart.axisRight.isEnabled = false
            chart.description.isEnabled = false

            chart.invalidate()
        })

        viewModel.fetchLastSevenWorkouts()
    }

    private fun parseDuration(duration: String): Long {
        val parts = duration.split(":")
        val hours = parts[0].toLong()
        val minutes = parts[1].toLong()
        val seconds = parts[2].toLong()
        return hours * 3600 + minutes * 60 + seconds
    }

    private fun formatDate(id: String): String {
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
        val date = dateFormat.parse(id) ?: Date()
        val outputFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
        return outputFormat.format(date)
    }
}