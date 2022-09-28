package com.example.myapplication.models

import java.io.Serializable

data class ToDo(var type: String, var name: String, var description: String,
                val spinnerItem: SpinnerData, val createDate: String, val deadLine: String) : Serializable