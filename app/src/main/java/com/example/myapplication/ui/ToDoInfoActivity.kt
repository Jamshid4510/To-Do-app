package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.models.ToDo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_to_do_info.*
import java.lang.reflect.Type

class ToDoInfoActivity : AppCompatActivity() {
    var list = arrayListOf<ToDo>()
    var index = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_info)

        loadData()

        var info = intent.getSerializableExtra("info") as ToDo

        for (i in list.indices){
            // klaslarni tenglashni boshqa yo'lini top
            if (list[i].name == info.name && list[i].description == info.description && list[i].createDate == info.createDate){
                index = i
            }
        }

        changeRadioSelected()

        tv_todoname.text = info.name

        tv_todo_description.text = info.description
        tv_todo_createdata.text = info.createDate
        tv_dedline.text = info.deadLine

        img_degree.setBackgroundResource(info.spinnerItem.img)
        tv_degree.text = info.spinnerItem.degree

        card_ok.setOnClickListener {
            var id = radio.checkedRadioButtonId

            when(id){
                R.id.rb_open -> {
                    list[index].type = "Open"                            // ma'lumotni orqadagi oynaga qayta o'tkazish kerak
                }
                R.id.rb_development -> {
                    list[index].type = "Development"
                }
                R.id.rb_uploading -> {
                    list[index].type = "Uploading"
                }
                R.id.rb_reject -> {
                    list[index].type = "Reject"
                }
                R.id.rb_closed -> {
                    list[index].type = "Closed"
                }
            }

            saveData()

            finish()
        }
    }

    fun changeRadioSelected(){
        when(list[index].type){
            "Open" -> {
                radio.check(R.id.rb_open)
            }
            "Development" -> {
                radio.check(R.id.rb_development)
            }
            "Uploading" -> {
                radio.check(R.id.rb_uploading)
            }
            "Reject" -> {
                radio.check(R.id.rb_reject)
            }
            "Closed" -> {
                radio.check(R.id.rb_closed)
            }
        }
    }

    fun loadData(){
        var sharedPreferences = getSharedPreferences("todo", MODE_PRIVATE)

        var gSon = Gson()
        var jSon = sharedPreferences.getString("todolist", "")

        if (jSon == ""){
            list = arrayListOf()
        } else{
            val type: Type = object : TypeToken<ArrayList<ToDo?>?>() {}.type
            list = gSon.fromJson(jSon, type)
        }
    }

    private fun saveData(){
        val sharedPreferences = getSharedPreferences("todo", MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        val gSon = Gson()
        var jSon = gSon.toJson(list)

        editor.putString("todolist", jSon)
        editor.apply()

        Log.d("save", "data saved")
        Toast.makeText(this, "Saved!", Toast.LENGTH_SHORT).show()
    }
}