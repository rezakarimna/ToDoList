package com.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class TaskAdapter(private val eventListener: TaskItemEventListener) :
    RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    private var taskList: MutableList<Task> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_task, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
//        holder.bindTask(taskList[(taskList.size - 1) - position])
        holder.bindTask(taskList[position])
    }

    fun addItem(task: Task) {
        taskList.add(0, task)
        notifyItemInserted(0)
    }

    fun updateItem(task: Task) {
        for (i in taskList.indices) {
            if (task.id == taskList[i].id) {
                taskList[i] = task
                notifyItemChanged(i)
                break
            }
        }
    }

    fun deleteItem(task: Task) {
        for (i in taskList.indices) {
            if (taskList[i].id == task.id) {
                taskList.removeAt(i)
                notifyItemRemoved(i)
                break
            }
        }
    }

    fun addListItem(tasks: List<Task>) {
        this.taskList.addAll(tasks)
        notifyDataSetChanged()
    }

    override fun getItemCount() = taskList.size


    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var checkBox: CheckBox = itemView.findViewById(R.id.checkBox_task)
        var deleteBtn: View = itemView.findViewById(R.id.btn_task_delete)

        fun bindTask(task: Task) {
            checkBox.isChecked = task.isCompleted
            checkBox.text = task.title
            deleteBtn.setOnClickListener { eventListener.onDeleteButtonClick(task) }
        }
    }

    interface TaskItemEventListener {
        fun onDeleteButtonClick(task: Task)
        fun onItemLongPress(task: Task)
        fun onItemCheckedChange(task: Task)
    }

}