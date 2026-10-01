package com.base.retrofitmoviesxml.presentation.screens

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import coil.load
import coil.size.Scale
import com.base.movieapplication.domain.response.MovieDetails
import com.base.retrofitmoviesxml.R
import com.base.retrofitmoviesxml.databinding.ActivityMovieDetailsBinding
import com.base.retrofitmoviesxml.presentation.viewmodel.MovieDetailsViewModel
import com.base.retrofitmoviesxml.presentation.viewmodel.UiState
import com.base.retrofitmoviesxml.utils.BaseActivity
import com.base.retrofitmoviesxml.utils.Constants.POSTER_BASE_URL

// VIEW del detalle: observa al ViewModel
class MovieDetailsActivity : BaseActivity() {

    private lateinit var binding: ActivityMovieDetailsBinding
    private val viewModel: MovieDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val movieId: Int = intent.getIntExtra("id", 1)
        viewModel.loadMovie(movieId)

        viewModel.movie.observe(this) { state ->
            when (state) {
                is UiState.Loading -> binding.prgBarMovies.visibility = View.VISIBLE
                is UiState.Success -> {
                    binding.prgBarMovies.visibility = View.GONE
                    showMovie(state.data)
                }
                is UiState.Error -> {
                    binding.prgBarMovies.visibility = View.GONE
                    Toast.makeText(this, state.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun showMovie(movie: MovieDetails) {
        val posterUrl = POSTER_BASE_URL + movie.posterPath
        binding.apply {
            imgMovie.load(posterUrl) {
                crossfade(true)
                placeholder(R.drawable.poster_placeholder)
                scale(Scale.FILL)
            }
            imgMovieBack.load(posterUrl) {
                crossfade(true)
                placeholder(R.drawable.poster_placeholder)
                scale(Scale.FILL)
            }
            tvMovieTitle.text = movie.title
            tvMovieTagLine.text = movie.tagline
            tvMovieDateRelease.text = movie.releaseDate
            tvMovieRating.text = movie.voteAverage.toString()
            tvMovieRuntime.text = movie.runtime.toString()
            tvMovieBudget.text = movie.budget.toString()
            tvMovieRevenue.text = movie.revenue.toString()
            tvMovieOverview.text = movie.overview
        }
    }
}
