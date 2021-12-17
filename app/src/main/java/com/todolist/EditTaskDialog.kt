package com.todolist

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.todolist.databinding.DialogEditTaskBinding

class EditTaskDialog : DialogFragment(), View.OnClickListener {
    lateinit var binding: DialogEditTaskBinding
    lateinit var callBack: EditTaskCallBack
    lateinit var task: Task

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = context as EditTaskCallBack
        task = arguments?.getParcelable("task")!!
        if (task == null) {
            dismiss()
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogEditTaskBinding.inflate(layoutInflater)
        val builder = AlertDialog.Builder(context)
        binding.btnDialogEditSave.setOnClickListener(this)
        builder.setView(binding.root)
        return builder.create()
    }


    interface EditTaskCallBack {
        fun onEditTask(task: Task)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_dialog_save -> {
                saveNewTask()
            }
        }
    }

    private fun saveNewTask() {
        if (binding.etDialogEditTitle.length() > 0) {
            task.title = binding.etDialogEditTitle.text.toString()
            callBack.onEditTask(task)
            dismiss()
        } else {
            binding.etlDialogEditTitle.error = "عنوان نباید خالی باشد"
        }
    }
}

