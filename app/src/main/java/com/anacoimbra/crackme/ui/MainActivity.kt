package com.anacoimbra.crackme.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.anacoimbra.crackme.R
import com.anacoimbra.crackme.databinding.ActivityMainBinding
import com.anacoimbra.crackme.domain.defaultPref
import com.anacoimbra.crackme.domain.set

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    private val adapter by lazy { FactsAdapter(viewModel) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        showDialog()

        binding.rvBookmarkedFacts.adapter = adapter
        binding.viewModel = viewModel
    }

    private fun showDialog() {
        if (!defaultPref(this).contains("likes")) {
            AlertDialog.Builder(this)
                .setTitle(R.string.i_am)
                .setItems(arrayOf("Dog person", "Cat person")) { _, which ->
                    when (which) {
                        0 -> defaultPref(this)["likes"] = "dog"
                        1 -> defaultPref(this)["likes"] = "cat"
                    }
                }.create()
                .show()
        }
    }
}

@BindingAdapter("facts")
fun setAdapter(view: RecyclerView, facts: List<String>?) {
    (view.adapter as? FactsAdapter)?.facts = facts.orEmpty()
}