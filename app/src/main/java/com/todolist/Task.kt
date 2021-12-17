package com.todolist

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Task(var id: Long, var title: String, var isCompleted: Boolean) : Parcelable {

    constructor() : this(0, "", false)


}