package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.myapplication.R
import com.example.myapplication.models.SpinnerData
import com.example.myapplication.models.ToDo
import kotlinx.android.synthetic.main.spinner_item.view.*

class AdapterForSpinner(var list: ArrayList<SpinnerData>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): SpinnerData {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView: View

        if (convertView == null) {
            itemView =
                LayoutInflater.from(parent?.context).inflate(R.layout.spinner_item, parent, false)
        } else {
            itemView = convertView
        }

        if (position == 0){
            itemView.tv_degree.text = list[position].degree
        } else{
            itemView.img_flag.setImageResource(list[position].img)
            itemView.tv_degree.text = list[position].degree
        }

        return itemView
    }

    override fun isEnabled(position: Int): Boolean {        // to'g'ri yozilganmi?
        if (position == 0)
            return false
        else
            return super.isEnabled(position)
    }
}