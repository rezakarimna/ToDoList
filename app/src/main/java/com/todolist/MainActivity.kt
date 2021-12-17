package com.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), AddTaskDialog.AddNewTaskCallBack,
    TaskAdapter.TaskItemEventListener,
    EditTaskDialog.EditTaskCallBack {
    lateinit var binding: ActivityMainBinding
    lateinit var taskAdapter: TaskAdapter
    lateinit var sqliteHelper: SqliteHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sqliteHelper = SqliteHelper(this)
        taskAdapter = TaskAdapter(this)


        setupRecyclerView()

        binding.fabMainAddNewTask.setOnClickListener {
            val taskDialog = AddTaskDialog()
            taskDialog.show(supportFragmentManager, null)
        }
    }

    private fun setupRecyclerView() {
        binding.rvMainTasks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = taskAdapter
            val tasks: List<Task> = sqliteHelper.getLitTasks()
            taskAdapter.addListItem(tasks)
        }
    }

    override fun onNewTask(task: Task) {
        val taskId: Long = sqliteHelper.addTask(task)
        if (taskId > 0) {
            task.id = taskId
            taskAdapter.addItem(task)
        } else {
            Log.e("MainActivity", "onNewTask: item did not insert")
        }
    }

    override fun onDeleteButtonClick(task: Task) {
        val result = sqliteHelper.deleteTask(task)
        if (result > 0) {
            taskAdapter.deleteItem(task)
        }

    }

    override fun onItemLongPress(task: Task) {
    }

    override fun onItemCheckedChange(task: Task) {
    }

    override fun onEditTask(task: Task) {
        val result = sqliteHelper.updateTask(task)
        if (result > 0 ) {
            taskAdapter.updateItem(task)
        }
    }
}