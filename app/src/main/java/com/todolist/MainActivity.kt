package com.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.todolist.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), AddTaskDialog.AddNewTaskCallBack,
    TaskAdapter.TaskItemEventListener,
    EditTaskDialog.EditTaskCallBack {
    lateinit var binding: ActivityMainBinding
    lateinit var taskAdapter: TaskAdapter
    lateinit var taskDao: TaskDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        taskDao = AppDatabase.getAppDatabase(this)?.taskDao()!!

        binding.ivMainClearTasks.setOnClickListener {
            taskDao.deleteAll()
            taskAdapter.clearTaskList()
        }

        binding.fabMainAddNewTask.setOnClickListener {
            val taskDialog = AddTaskDialog()
            taskDialog.show(supportFragmentManager, null)
        }

        setupRecyclerView()
        searchTitleTasks()
    }

    private fun init() {
        taskAdapter = TaskAdapter(this)

    }

    private fun setupRecyclerView() {
        binding.rvMainTasks.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = taskAdapter
            val tasks = taskDao.all
            taskAdapter.addListItem(tasks)
        }
    }

    override fun onNewTask(task: Task) {
        val taskId: Long = taskDao.add(task)
        if (taskId > 0) {
            task.id = taskId
            taskAdapter.addItem(task)
        } else {
            Log.e("MainActivity", "onNewTask: item did not insert")
        }
    }

    override fun onDeleteButtonClick(task: Task) {
        val result = taskDao.delete(task)
        if (result > 0) {
            taskAdapter.deleteItem(task)
        }

    }

    override fun onItemLongPress(task: Task) {
        val editTaskDialog = EditTaskDialog()
        val bundle = Bundle()
        bundle.putParcelable("task", task)
        editTaskDialog.arguments = bundle
        editTaskDialog.show(supportFragmentManager, null)

    }

    override fun onItemCheckedChange(task: Task) {
        taskDao.update(task)
    }

    override fun onEditTask(task: Task) {
        val result = taskDao.update(task)
        if (result > 0) {
            taskAdapter.updateItem(task)
        }
    }

    private fun searchTitleTasks() {
        binding.etSearch.doOnTextChanged { text, start, before, count ->
            if (text?.length!! > 0) {
                val tasks = taskDao.search(text.toString())
                taskAdapter.setTaskList(tasks)
            } else {
                val tasks = taskDao.all
                taskAdapter.setTaskList(tasks)
            }
        }
    }
}