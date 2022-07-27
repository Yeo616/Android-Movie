package com.yeo.movieapp.api;

import com.yeo.movieapp.model.MovieList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface MovieApi {

    //영화 목록 가져오는 API
    @GET("movie")
    Call<MovieList> getMovieList(@Header("Authorization") String token,
                                 @Query("offset") int offset,
                                 @Query("limit") int limit);


}
