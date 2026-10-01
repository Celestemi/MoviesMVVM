package com.base.retrofitmoviesxml.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.base.movieapplication.domain.response.MovieDetails
import com.base.retrofitmoviesxml.data.repository.MovieRepository
import kotlinx.coroutines.launch

// VIEWMODEL del detalle de una película
class MovieDetailsViewModel(
    private val repository: MovieRepository = MovieRepository()
) : ViewModel() {

    private val _movie = MutableLiveData<UiState<MovieDetails>>()
    val movie: LiveData<UiState<MovieDetails>> = _movie

    private var loadedId: Int? = null

    fun loadMovie(id: Int) {
        if (loadedId == id) return // evita recargar al rotar la pantalla
        loadedId = id
        viewModelScope.launch {
            _movie.value = UiState.Loading
            _movie.value = try {
                UiState.Success(repository.getMovieDetails(id))
            } catch (e: Exception) {
                loadedId = null
                UiState.Error(e.message ?: "Error al cargar el detalle")
            }
        }
    }
}
