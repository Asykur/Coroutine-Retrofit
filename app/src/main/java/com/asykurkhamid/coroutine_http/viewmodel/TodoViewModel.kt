package com.asykurkhamid.coroutine_http.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.asykurkhamid.coroutine_http.model.TodoModel
import com.asykurkhamid.coroutine_http.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class TodoViewModel : ViewModel() {

    private var repo = TodoRepository()
    private var todoDataList = MutableLiveData<ArrayList<TodoModel>>()
    private val list = ArrayList<TodoModel>()
    private val todoError = MutableLiveData<String>()
    private val isLoading = MutableLiveData<Boolean>()

    fun callTodo(id: Int) {

            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        isLoading.postValue(true)
                        val result = repo.getTodo(id)
                        isLoading.postValue(false)
                        list.add(result)
                        todoDataList.postValue(list)
                    } catch (throwable: Throwable) {
                        when (throwable) {
                            is IOException -> {
                                todoError.postValue("Network Error")
                            }
                            is HttpException -> {
                                val errCode = throwable.code()
                                val errMessage = throwable.message()
                                todoError.postValue("Error $errCode : $errMessage")
                            }
                            else -> {
                                todoError.postValue("Unknown Error")
                            }
                        }
                    }
                }
            }


    }

    fun getTodoData(): LiveData<ArrayList<TodoModel>> {
        return todoDataList
    }

    fun getTodoError(): LiveData<String> {
        return todoError
    }

    fun getIsLoading(): LiveData<Boolean>{
        return isLoading
    }

}