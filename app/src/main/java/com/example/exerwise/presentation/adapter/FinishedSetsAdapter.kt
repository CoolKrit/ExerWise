package com.example.exerwise.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.exerwise.data.model.Set
import com.example.exerwise.databinding.ItemFinishedSetBinding

class FinishedSetsAdapter : ListAdapter<Set, FinishedSetsAdapter.SetViewHolder>(SetDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetViewHolder {
        val binding = ItemFinishedSetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SetViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SetViewHolder(private val binding: ItemFinishedSetBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(set: Set) {
            binding.textViewSetNumber.text = set.setNumber.toString()
            binding.editTextWeight.setText(set.setWeight.toString())
            binding.editTextReps.setText(set.setReps.toString())
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