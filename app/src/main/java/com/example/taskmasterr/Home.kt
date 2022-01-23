package com.example.taskmasterr

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmasterr.room.Constant
import com.example.taskmasterr.room.Task
import com.example.taskmasterr.room.TaskDb
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Home : AppCompatActivity() {

    val db by lazy { TaskDb( this ) }
    lateinit var taskAdapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

    supportActionBar?.hide()

        setupListener()
        setupRecyclerView()


    }

    override fun onStart() {
        super.onStart()

        loadTask()
    }

    private fun loadTask() {
        CoroutineScope(Dispatchers.IO).launch {
            val tasks =db.TaskDao().getTask()
            Log.d("Home", "dbresponse : $tasks")
            withContext(Dispatchers.Main){
                taskAdapter.setData(tasks)
            }
        }
    }

    fun setupListener() {
        val addtask = findViewById<Button>(R.id.add_task)

        addtask.setOnClickListener {
            intentAdd( 0, Constant.TYPE_CREATE)
        }
    }

    fun intentAdd(taskId: Int, IntentType: Int) {
        startActivity(
            Intent(applicationContext, AddActivity::class.java)
                .putExtra("intent_id", taskId)
                .putExtra("intent_type", IntentType)
        )

    }

    private fun setupRecyclerView() {
        taskAdapter = TaskAdapter(arrayListOf(), object : TaskAdapter.OnAdapterListener{
            override fun onClick(task: Task) {
                intentAdd(task.id, Constant.TYPE_READ)
            }

            override fun onUpdate(task: Task) {
                intentAdd(task.id, Constant.TYPE_UPDATE)
            }

            override fun onDelete(task: Task) {
                deleteDialog(task)
            }
        })
        rv_task.apply{
            layoutManager = LinearLayoutManager(applicationContext)
            adapter = taskAdapter
        }
    }

    private fun deleteDialog(task: Task){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.apply {
            setTitle("Konfirmasi")
            setMessage("Yakin Hapus ${task.title}?")
            setNegativeButton("Batal",){dialoginterface, i ->
                dialoginterface.dismiss()
            }

            setPositiveButton("Hapus",){dialoginterface, i ->
                dialoginterface.dismiss()
                CoroutineScope(Dispatchers.IO).launch {
                    db.TaskDao().deleteTask(task)
                    loadTask()
                }
            }
        }

        alertDialog.show()

    }

}