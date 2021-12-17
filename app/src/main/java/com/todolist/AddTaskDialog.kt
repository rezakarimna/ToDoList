package com.todolist

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.todolist.databinding.DialogAddTaskBinding

class AddTaskDialog : DialogFragment(), View.OnClickListener {
    lateinit var binding: DialogAddTaskBinding
    lateinit var callBack: AddNewTaskCallBack

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = context as AddNewTaskCallBack
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogAddTaskBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(context)
        binding.btnDialogSave.setOnClickListener(this)
        builder.setView(binding.root)
        return builder.create()
    }


    interface AddNewTaskCallBack {
        fun onNewTask(task: Task)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_dialog_save -> {
                saveNewTask()
            }
        }
    }

    private fun saveNewTask() {
        if (binding.etDialogTitle.length() > 0) {
            val task = Task()
            task.title = binding.etDialogTitle.text.toString()
            task.isCompleted = false
            callBack.onNewTask(task)
            dismiss()
        } else {
            binding.etlDialogTitle.error = "عنوان نباید خالی باشد"
        }
    }
}

