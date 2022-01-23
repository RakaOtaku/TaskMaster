package com.example.taskmasterr

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.taskmasterr.room.Task
import kotlinx.android.synthetic.main.activity_add.view.*
import kotlinx.android.synthetic.main.list_task.view.*

class TaskAdapter(private val tasks: ArrayList<Task>, private val listener: OnAdapterListener) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_task, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.view.tittle.text  = task.title
        holder.view.tittle.setOnClickListener {
            listener.onClick(task)
        }

        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(task)
        }

        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(task)
        }

    }

    override fun getItemCount() = tasks.size

    class TaskViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Task>) {
        tasks.clear()
        tasks.addAll(list)
        notifyDataSetChanged()
    }

    interface OnAdapterListener{
        fun onClick(task : Task)
        fun onUpdate(task: Task)
        fun onDelete(task: Task)
    }
}