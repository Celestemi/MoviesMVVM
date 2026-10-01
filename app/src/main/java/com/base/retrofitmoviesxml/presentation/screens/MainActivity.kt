package com.base.retrofitmoviesxml.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.retrofitmoviesxml.databinding.ActivityMainBinding
import com.base.retrofitmoviesxml.presentation.adapter.MoviesAdapter
import com.base.retrofitmoviesxml.presentation.viewmodel.MoviesViewModel
import com.base.retrofitmoviesxml.presentation.viewmodel.UiState
import com.base.retrofitmoviesxml.utils.BaseActivity

// VIEW: solo pinta lo que le dice el ViewModel
class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val moviesAdapter by lazy { MoviesAdapter() }
    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rlMovies.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = moviesAdapter
        }

        viewModel.movies.observe(this) { state ->
            when (state) {
                is UiState.Loading -> binding.pbMovies.visibility = View.VISIBLE
                is UiState.Success -> {
                    binding.pbMovies.visibility = View.GONE
                    moviesAdapter.differ.submitList(state.data)
                }
                is UiState.Error -> {
                    binding.pbMovies.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}
