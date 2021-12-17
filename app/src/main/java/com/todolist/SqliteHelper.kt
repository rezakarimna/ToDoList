package com.todolist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import kotlin.math.log

class SqliteHelper(context: Context?) : SQLiteOpenHelper(context, "db_app", null, 1) {

    companion object {
        private const val TABLE_TASK = "tbl_tasks"
        private const val TAG = "SqliteHelper"

    }

    private val contentValues = ContentValues()

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db?.execSQL(
                "CREATE TABLE " + TABLE_TASK +
                        " (id INTEGER PRIMARY KEY AUTOINCREMENT , title TEXT , completed BOOLEAN); "
            )
        } catch (e: SQLiteException) {
            Log.e(TAG, "onCreate: $e")
        }

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun addTask(task: Task): Long {
        val sqliteDbWrite = writableDatabase!!
        contentValues.put("title", task.title)
        contentValues.put("completed", task.isCompleted)
        val result: Long = sqliteDbWrite.insert(TABLE_TASK, null, contentValues)
        sqliteDbWrite.close()
        return result
    }

    fun getLitTasks(): List<Task> {
        val sqliteDbRead = readableDatabase!!
        val cursor: Cursor = sqliteDbRead.rawQuery("SELECT * FROM $TABLE_TASK ", null)
        var tasks = arrayListOf<Task>()
        if (cursor.moveToFirst()) {
            do {
                val task = Task()
                task.id = cursor.getLong(0)
                task.title = cursor.getString(1)
                task.isCompleted = cursor.getInt(2) == 1
                tasks.add(task)
            } while (cursor.moveToNext())
        }
        sqliteDbRead.close()
        return tasks
    }

    fun updateTask(task: Task): Int {
        val sqliteDbWrite = writableDatabase!!
        contentValues.put("title", task.title)
        contentValues.put("completed", task.isCompleted)
        val result: Int =
            sqliteDbWrite.update(TABLE_TASK, contentValues, "id =?", arrayOf(task.id.toString()))
        sqliteDbWrite.close()
        return result
    }

    fun searchInTasks(query: String) : List<Task> {
        val sqliteDbRead = readableDatabase!!
        val cursor = sqliteDbRead.rawQuery("SELECT * FROM $TABLE_TASK WHERE title LIKE '& $query &' " , null)
        var tasks = arrayListOf<Task>()
        if (cursor.moveToFirst()) {
            do {
                val task = Task()
                task.id = cursor.getLong(0)
                task.title = cursor.getString(1)
                task.isCompleted = cursor.getInt(2) == 1
                tasks.add(task)
            } while (cursor.moveToNext())
        }
        sqliteDbRead.close()
        return tasks
    }

    fun deleteTask(task: Task): Int {
        val sqliteDbWrite = writableDatabase!!
        val result = sqliteDbWrite.delete(TABLE_TASK, "id = ?", arrayOf(task.id.toString()))
        sqliteDbWrite.close()
        return result
    }

    fun clearAllTasks() {
        try {
            val sqliteDbWrite = writableDatabase!!
            sqliteDbWrite.execSQL("DELETE FROM $TABLE_TASK")
            sqliteDbWrite.close()
        } catch (e : SQLiteException) {
            Log.e(TAG, "onCreate: $e")
        }

    }
}
