package com.anacoimbra.crackme.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.anacoimbra.crackme.R
import com.anacoimbra.crackme.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val adapter by lazy { FactsAdapter(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.rvBookmarkedFacts.adapter = adapter
        binding.viewModel = viewModel

        viewModel.bookmarked.observe(this) {
            Log.d("bookmarked", it.joinToString())
        }
    }
}

@BindingAdapter("facts")
fun setAdapter(view: RecyclerView, facts: List<String>?) {
    (view.adapter as? FactsAdapter)?.facts = facts.orEmpty()
}