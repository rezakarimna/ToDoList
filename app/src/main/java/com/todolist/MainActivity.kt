package com.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.todolist.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var taskAdapter: TaskAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        binding.fabMainAddNewTask.setOnClickListener {
            val taskDialog = TaskDialog()
            taskDialog.show(supportFragmentManager, null)
        }
    }


    private fun setupRecyclerView() {
        binding.rvMainTasks.apply {
            taskAdapter = TaskAdapter()
            layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
            adapter = taskAdapter
        }
    }
}