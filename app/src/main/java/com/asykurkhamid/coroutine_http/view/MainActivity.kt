package com.asykurkhamid.coroutine_http.view

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.asykurkhamid.coroutine_http.R
import com.asykurkhamid.coroutine_http.adapter.TodoAdapter
import com.asykurkhamid.coroutine_http.model.TodoModel
import com.asykurkhamid.coroutine_http.repository.TodoRepository
import com.asykurkhamid.coroutine_http.viewmodel.TodoViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TodoViewModel
    private var adapter = TodoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider.NewInstanceFactory().create(TodoViewModel::class.java)
        rvTodo.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvTodo.adapter = adapter

        btnTodo.setOnClickListener {
            lifecycleScope.launch {
                val threadName = Thread.currentThread().name
                Log.i("THREAD", "on $threadName thread")
                val id = (1..66).random()

                withContext(Dispatchers.IO) {
                    val threadName2 = Thread.currentThread().name
                    Log.i("THREAD", "on $threadName2 thread")
                    viewModel.callTodo(id)
                }

                viewModel.getTodoData().observe(this@MainActivity, Observer { data ->
                    showTodo(data)
                })

                viewModel.getTodoError().observe(this@MainActivity, Observer { error ->
                    Snackbar.make(rvTodo, error, Snackbar.LENGTH_SHORT).show()
                })

                viewModel.getIsLoading().observe(this@MainActivity, Observer { isLoading ->
                    if (isLoading){
                        pgMain.visibility = View.VISIBLE
                    }else{
                        pgMain.visibility = View.GONE
                    }
                })
            }
        }
    }

    private fun showTodo(todoData: ArrayList<TodoModel>) {
        adapter.setTodoList(todoData)
        adapter.notifyDataSetChanged()
    }
}