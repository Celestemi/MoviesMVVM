package com.base.retrofitmoviesxml.data.repository

import com.base.movieapplication.domain.response.MovieDetails
import com.base.movieapplication.domain.response.MoviesListResponse
import com.base.retrofitmoviesxml.domain.api.ApiClient
import com.base.retrofitmoviesxml.domain.api.ApiServices

// REPOSITORIO: única puerta de acceso a los datos de la API (TMDB) para los ViewModels
class MovieRepository(
    private val api: ApiServices = ApiClient().getClient().create(ApiServices::class.java)
) {
    suspend fun getPopularMovies(page: Int = 1): List<MoviesListResponse.Result> =
        api.getPopularMovie(page).results

    suspend fun getMovieDetails(id: Int): MovieDetails =
        api.getMovieDetails(id)
}
