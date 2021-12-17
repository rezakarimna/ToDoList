package com.todolist

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tbl_tasks")
@Parcelize
data class Task(
    @PrimaryKey(autoGenerate = true) var id: Long,
    var title: String,
    var isCompleted: Boolean
) : Parcelable {

    constructor() : this(0, "", false)


}