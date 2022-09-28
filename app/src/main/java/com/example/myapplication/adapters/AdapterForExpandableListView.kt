package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import com.example.myapplication.R
import com.example.myapplication.models.ToDo
import kotlinx.android.synthetic.main.child_item.view.*
import kotlinx.android.synthetic.main.group_item.view.*

class AdapterForExpandableListView(var titleList : ArrayList<String>, var map : HashMap<String, ArrayList<ToDo>>) : BaseExpandableListAdapter(){
    override fun getGroupCount(): Int {
        return titleList.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return map[titleList[groupPosition]]!!.size
    }

    override fun getGroup(groupPosition: Int): Any {
        return titleList[groupPosition]
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return map[titleList[groupPosition]]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun getGroupView(
        groupPosition: Int,
        isExpanded: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var itemView: View

        if (convertView == null){
            itemView = LayoutInflater.from(parent?.context).inflate(R.layout.group_item, parent, false)
        } else{
            itemView = convertView
        }

        itemView.tv_group_item.text = titleList[groupPosition]

        return itemView
    }

    override fun getChildView(
        groupPosition: Int,
        childPosition: Int,
        isLastChild: Boolean,
        convertView: View?,
        parent: ViewGroup?
    ): View {
        var itemView : View

        if (convertView == null){
            itemView = LayoutInflater.from(parent?.context).inflate(R.layout.child_item, parent, false)
        } else{
            itemView = convertView
        }

        itemView.tv_child_item.text = map[titleList[groupPosition]]!![childPosition].name // shu yerda toDo 1 deganday yozib ketish kerakdir balki

        return itemView
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}
