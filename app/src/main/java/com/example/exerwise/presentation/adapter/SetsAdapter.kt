package com.example.exerwise.presentation.adapter

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exerwise.data.model.Exercise
import com.example.exerwise.data.model.Set
import com.example.exerwise.databinding.ItemSetBinding
import com.example.exerwise.presentation.viewmodel.CreateWorkoutViewModel

class SetsAdapter(private val exercise: Exercise, private val viewModel: CreateWorkoutViewModel) : ListAdapter<Set, SetsAdapter.SetViewHolder>(SetDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val binding = ItemSetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SetViewHolder(private val binding: ItemSetBinding) : RecyclerView.ViewHolder(binding.root) {
        private var weightTextWatcher: TextWatcher? = null
        private var repsTextWatcher: TextWatcher? = null

        fun bind(set: Set) {
            binding.textViewSetNumber.text = set.setNumber.toString()

            // Remove existing TextWatchers if any
            weightTextWatcher?.let { binding.editTextWeight.removeTextChangedListener(it) }
            repsTextWatcher?.let { binding.editTextReps.removeTextChangedListener(it) }

            binding.editTextWeight.setText(set.setWeight.toString())
            binding.editTextReps.setText(set.setReps.toString())

            weightTextWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val weightText = s.toString()
                    if (weightText.isNotEmpty()) {
                        set.setWeight = weightText.toDouble()
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            }

            repsTextWatcher = object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    val repsText = s.toString()
                    if (repsText.isNotEmpty()) {
                        set.setReps = repsText.toInt()
                    }
                }
                override fun afterTextChanged(s: Editable?) {}
            }

            binding.editTextWeight.addTextChangedListener(weightTextWatcher)
            binding.editTextReps.addTextChangedListener(repsTextWatcher)

            binding.imageButtonDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    viewModel.removeSet(exercise.id, position)
                }
            }
        }
    }

    class SetDiffCallback : DiffUtil.ItemCallback<Set>() {
        override fun areItemsTheSame(oldItem: Set, newItem: Set): Boolean {
            return oldItem.setNumber == newItem.setNumber
        }

        override fun areContentsTheSame(oldItem: Set, newItem: Set): Boolean {
            return oldItem == newItem
        }
    }
}