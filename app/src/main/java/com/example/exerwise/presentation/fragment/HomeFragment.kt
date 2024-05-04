package com.example.exerwise.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.exerwise.databinding.FragmentHomeBinding
import com.example.exerwise.presentation.ArcProgressBar
import com.example.exerwise.presentation.WeekdayIndicatorView


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arcProgressBar: ArcProgressBar = binding.progressBars.arcProgressBarSteps
        arcProgressBar.setProgress(5000, 10000)

        val arcProgressBarW: ArcProgressBar = binding.progressBars.arcProgressBarWorkout
        arcProgressBarW.setProgress(12, 24)

        val arcProgressBarC: ArcProgressBar = binding.progressBars.arcProgressBarCalories
        arcProgressBarC.setProgress(300, 1800)

        val weekdayIndicator: WeekdayIndicatorView = binding.weekdayIndicator.weekdayIndicatorCircles
        weekdayIndicator.markDayCompleted("M")
    }
}