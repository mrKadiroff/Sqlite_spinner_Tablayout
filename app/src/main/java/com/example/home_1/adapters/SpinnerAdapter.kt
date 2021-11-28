package com.example.home_1.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.home_1.databinding.ItemBinding

class SpinnerAdapter(var list: ArrayList<String>) : BaseAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): String {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(p0: Int, convertView: View?, parent: ViewGroup?): View {
        var basicViewHolder:BasicViewHolder
        if (convertView==null){
            val binding =
                ItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            basicViewHolder=BasicViewHolder(binding)
        }else{
            basicViewHolder=BasicViewHolder(ItemBinding.bind(convertView))
        }
        basicViewHolder.itemSpinnerBinding.textSpinner.text=list[p0]
        return basicViewHolder.itemView
    }

    inner class BasicViewHolder{
        var itemView:View
        var itemSpinnerBinding:ItemBinding

        constructor(itemSpinnerBinding: ItemBinding) {
            itemView=itemSpinnerBinding.root
            this.itemSpinnerBinding = itemSpinnerBinding
        }
    }
}