package com.asykurkhamid.coroutine_http.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.asykurkhamid.coroutine_http.R
import com.asykurkhamid.coroutine_http.model.TodoModel
import kotlinx.android.synthetic.main.todo_item.view.*

class TodoAdapter:RecyclerView.Adapter<TodoAdapter.VH>() {

    private var todoList = ArrayList<TodoModel>()

    fun setTodoList(newList: ArrayList<TodoModel>){
        if (todoList.size != 0){
            todoList.clear()
            this.todoList.addAll(newList)
        }else{
            this.todoList.addAll(newList)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.todo_item,parent,false)
        return VH(view)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(todoList[position])
    }

    class VH(itemView: View): RecyclerView.ViewHolder(itemView){

        fun onBind(todoData: TodoModel){
            itemView.tvTitle.text = todoData.title
        }
    }
}