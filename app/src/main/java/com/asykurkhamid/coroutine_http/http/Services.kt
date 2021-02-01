package com.asykurkhamid.coroutine_http.http

import com.asykurkhamid.coroutine_http.model.TodoModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface Services {

    @GET("/todos/{id}")
    suspend fun getTodo(@Path("id")id : Int): TodoModel

}

