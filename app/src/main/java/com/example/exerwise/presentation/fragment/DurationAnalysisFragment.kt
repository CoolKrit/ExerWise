package com.example.exerwise.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.exerwise.R
import com.example.exerwise.presentation.viewmodel.WorkoutViewModel
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DurationAnalysisFragment : Fragment() {

    private val viewModel: WorkoutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_duration_analysis, container, false)

        viewModel.workouts.observe(viewLifecycleOwner, Observer { workouts ->
            val entries = ArrayList<BarEntry>()
            val labels = ArrayList<String>()
            val barChart = view.findViewById<BarChart>(R.id.barChart)

            workouts.forEachIndexed { index, workout ->
                val durationInSeconds = parseDuration(workout.duration)
                entries.add(BarEntry(index.toFloat(), durationInSeconds.toFloat()))
                labels.add(formatDate(workout.id))
            }

            val dataSet = BarDataSet(entries, "Duration")
            dataSet.color = resources.getColor(R.color.md_theme_primary)
            dataSet.valueTextColor = resources.getColor(R.color.md_theme_onSurface)
            val barData = BarData(dataSet)

            barChart.data = barData
            barChart.setBorderColor(R.color.md_theme_onSurface)

            barChart.xAxis.valueFormatter = IndexAxisValueFormatter(labels)
            barChart.xAxis.position = XAxis.XAxisPosition.BOTTOM
            barChart.xAxis.textColor = resources.getColor(R.color.md_theme_onSurface)
            barChart.axisLeft.textColor = resources.getColor(R.color.md_theme_onSurface)
            barChart.axisRight.isEnabled = false
            barChart.description.isEnabled = false

            barChart.invalidate()
        })
        viewModel.fetchLastSevenWorkouts()

        return view
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