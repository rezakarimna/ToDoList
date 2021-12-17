package com.todolist

import androidx.room.*

@Dao
interface TaskDao {

    @Insert
    fun add(task: Task?): Long

    @Delete
    fun delete(task: Task?): Int

    @Update
    fun update(task: Task?): Int

    @get:Query("SELECT * FROM tbl_tasks")
    val all: List<Task>

    @Query("SELECT * FROM tbl_tasks WHERE title LIKE '%' || :query || '%'")
    fun search(query: String): List<Task>

    @Query("DELETE FROM tbl_tasks")
    fun deleteAll()
}