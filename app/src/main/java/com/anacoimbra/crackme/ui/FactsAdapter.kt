package com.anacoimbra.crackme.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.anacoimbra.crackme.databinding.ItemBookmarkedBinding

class FactsAdapter(private val listener: Listener?) :
    RecyclerView.Adapter<FactsAdapter.ViewHolder>() {

    var facts: List<String> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            ItemBookmarkedBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(facts[position], listener)
    }

    override fun getItemCount(): Int = facts.size

    inner class ViewHolder(private val binding: ItemBookmarkedBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(fact: String, listener: Listener?) {
            binding.fact = fact
            binding.listener = listener
        }
    }
}