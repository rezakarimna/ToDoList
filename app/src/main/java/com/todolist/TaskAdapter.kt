package com.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {

    }

    override fun getItemCount() = 20


    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }


}