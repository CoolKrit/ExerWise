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
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class VolumeAnalysisFragment : Fragment() {

    private val viewModel: WorkoutViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_volume_analysis, container, false)

        viewModel.workouts.observe(viewLifecycleOwner, Observer { workouts ->
            val entries = ArrayList<BarEntry>()
            val labels = ArrayList<String>()
            val barChart = view.findViewById<BarChart>(R.id.barChart)

            workouts.forEachIndexed { index, workout ->
                entries.add(BarEntry(index.toFloat(), workout.volume.toFloat()))
                labels.add(formatDate(workout.id))
            }

            val dataSet = BarDataSet(entries, "Volume")
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

    private fun formatDate(id: String): String {
        val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
        val date = dateFormat.parse(id) ?: Date()
        val outputFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
        return outputFormat.format(date)
    }
}