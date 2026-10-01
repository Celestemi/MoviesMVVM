package com.base.retrofitmoviesxml.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.base.movieapplication.domain.response.MoviesListResponse
import com.base.retrofitmoviesxml.data.repository.MovieRepository
import kotlinx.coroutines.launch

// VIEWMODEL de la lista de películas populares
class MoviesViewModel(
    private val repository: MovieRepository = MovieRepository()
) : ViewModel() {

    private val _movies = MutableLiveData<UiState<List<MoviesListResponse.Result>>>()
    val movies: LiveData<UiState<List<MoviesListResponse.Result>>> = _movies

    init {
        loadPopularMovies()
    }

    fun loadPopularMovies(page: Int = 1) {
        viewModelScope.launch {
            _movies.value = UiState.Loading
            _movies.value = try {
                UiState.Success(repository.getPopularMovies(page))
            } catch (e: Exception) {
                UiState.Error(e.message ?: "Error al cargar las películas")
            }
        }
    }
}
