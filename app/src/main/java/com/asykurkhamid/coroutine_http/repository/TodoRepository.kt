package com.asykurkhamid.coroutine_http.repository

import com.asykurkhamid.coroutine_http.http.RetrofitBuilder

class TodoRepository {
    private var services  = RetrofitBuilder().api
    suspend fun getTodo(id: Int) = services.getTodo(id)
}