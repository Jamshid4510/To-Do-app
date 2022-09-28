package com.example.myapplication.ui

import android.content.Intent
import android.icu.text.IDNA
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.myapplication.R
import com.example.myapplication.adapters.AdapterForExpandableListView
import com.example.myapplication.models.SpinnerData
import com.example.myapplication.models.ToDo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_to_do_list.*
import java.lang.reflect.Type
import kotlin.math.log

class ToDoListActivity : AppCompatActivity() {
    var list = arrayListOf<ToDo>()
    var titeList = arrayListOf<String>("Open", "Development", "Uploading", "Reject", "Closed")
    var map = hashMapOf<String, ArrayList<ToDo>>()
    var toDoCount = 1
    lateinit var adapter : AdapterForExpandableListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)

        Log.d("jamshid", "onCreate")
        loadData()

        adapter = AdapterForExpandableListView(titeList, map)
        my_expandable_list_view.setAdapter(adapter)

        my_expandable_list_view.setOnChildClickListener {parent, v, groupPosition, childPosition, id ->
            val intent = Intent(this, ToDoInfoActivity::class.java)
            var info = map[titeList[groupPosition]]!![childPosition]
            intent.putExtra("info", info)
            Log.d("Jamshid", info.toString())
            startActivity(intent)
            // shu joyini startActivityForResult deb o'zgartiramiz

//            startActivityForResult(intent, 1)

            true
        }
    }

    private fun loadData(){
        var sharedPreferences = getSharedPreferences("todo", MODE_PRIVATE)

        var gSon = Gson()
        var jSon = sharedPreferences.getString("todolist", "")

        if (jSon == ""){
            list = arrayListOf()
        } else{
            val type: Type = object : TypeToken<ArrayList<ToDo?>?>() {}.type
            list = gSon.fromJson(jSon, type)
        }

//        map.put("Open", list) // bo'lish

        var listOpen = arrayListOf<ToDo>()
        var listDevelopment = arrayListOf<ToDo>()
        var listUploading = arrayListOf<ToDo>()
        var listReject = arrayListOf<ToDo>()
        var listClosed = arrayListOf<ToDo>()

        for (i in list){
            println(i.type)
        }

        for (i in list){
            when(i.type){
                "Open" -> {
                    listOpen.add(i)
                }
                "Development" -> {
                    listDevelopment.add(i)
                }
                "Uploading" -> {
                    listUploading.add(i)
                }
                "Reject" -> {
                    listReject.add(i)
                }
                "Closed" -> {
                    listClosed.add(i)
                }
            }
        }

        map.put("Open", listOpen)
        map.put("Development", listDevelopment)
        map.put("Uploading", listUploading)
        map.put("Reject", listReject)
        map.put("Closed", listClosed)
    }

    override fun onStart() {
        super.onStart()
        Log.d("jamshid", "OnStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("jamshid", "OnResume")
        loadData()

        adapter = AdapterForExpandableListView(titeList, map)
        my_expandable_list_view.setAdapter(adapter)
    }

    override fun onPause() {
        super.onPause()
        Log.d("jamshid", "onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("jamshid", "onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("jamshid", "onDestroy")
    }
}