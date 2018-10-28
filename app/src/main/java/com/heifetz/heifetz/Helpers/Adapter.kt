package com.heifetz.heifetz.Helpers

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.heifetz.heifetz.R
import com.heifetz.heifetz.models.TimeItem

class Adapter(private val items: ArrayList<TimeItem>) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflater = LayoutInflater.from(parent!!.context)
        val view = convertView ?: inflater.inflate(R.layout.list_item, parent, false)
        val vId = view.findViewById<TextView>(R.id.item_id)
        val vTitle = view.findViewById<TextView>(R.id.item_title)

        val item = getItem(position) as TimeItem

        vId.text = item.id.toString()
        vTitle.text = item.value
        view.setBackgroundColor(item.color)

        return view
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

}