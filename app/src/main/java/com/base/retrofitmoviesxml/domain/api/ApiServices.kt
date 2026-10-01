package com.base.retrofitmoviesxml.domain.api

import com.base.movieapplication.domain.response.MovieDetails
import com.base.movieapplication.domain.response.MoviesListResponse
import com.base.retrofitmoviesxml.utils.ApiConstants.Companion.MOVIE_POPULAR
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    // https://api.themoviedb.org/3/movie/550?api_key=***
    // https://api.themoviedb.org/3/movie/popular?api_key=***

    // Ahora son "suspend": se llaman desde corrutinas (viewModelScope) y no usan Call/enqueue
    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Int): MovieDetails

    @GET(MOVIE_POPULAR)
    suspend fun getPopularMovie(@Query("page") page: Int): MoviesListResponse
}
