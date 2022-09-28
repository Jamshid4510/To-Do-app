package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myapplication.R
import com.example.myapplication.adapters.AdapterForSpinner
import com.example.myapplication.models.SpinnerData
import com.example.myapplication.models.ToDo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_add_to_do.*
import java.lang.reflect.Type

class AddToDoActivity : AppCompatActivity() {
    var listData = arrayListOf<SpinnerData>()
    var list = arrayListOf<ToDo>()
    var correct = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_do)

        initDataToList()
        spinner_for_degree.adapter = AdapterForSpinner(listData)

        card_save.setOnClickListener {
            correct = true

            val name = et_to_do_name.text.toString().trim()
            val description = et_to_do_description.text.toString().trim()
            val itemSpinner : SpinnerData = spinner_for_degree.selectedItem as SpinnerData // yoki id sini olib ko'ramiz
            val createDate = et_to_do_data.text.toString().trim()
            val deadLine = et_to_do_deadline.text.toString().trim()

            if (name.isEmpty()){
                et_to_do_name.setError("Enter data")
                correct = false
            }
            if (description.isEmpty()){
                et_to_do_description.setError("Enter data")
                correct = false
            }
            if (createDate.isEmpty()){
                et_to_do_data.setError("Enter data")
                correct = false
            }
            if (deadLine.isEmpty()){
                et_to_do_deadline.setError("Enter data")
                correct = false
            }

            if (correct){
                loadData()

                var toDo = ToDo("Open", name, description, itemSpinner, createDate, deadLine)

                list.add(toDo)

                saveData()
                clearEtDate()
            }
        }

    }

    private fun clearEtDate(){
        et_to_do_name.setText("")
        et_to_do_description.setText("")
        et_to_do_data.setText("")
        et_to_do_deadline.setText("")

        spinner_for_degree.post {
            kotlin.run {
                spinner_for_degree.setSelection(0)       // spinnerni 0-index dagi elementini tanlangan qilib qo'yish
            }
        }
    }

    private fun initDataToList(){
        listData.add(SpinnerData(1, "To do degree"))
        listData.add(SpinnerData(R.drawable.ic_urgent, "Urgent"))
        listData.add(SpinnerData(R.drawable.ic_high, "High"))
        listData.add(SpinnerData(R.drawable.ic_normal, "Normal"))
        listData.add(SpinnerData(R.drawable.ic_low, "Low"))
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