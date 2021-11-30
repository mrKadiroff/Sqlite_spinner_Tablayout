package com.example.home_1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.home_1.databinding.IjtimContactBinding
import com.example.home_1.models.Contact

class Ijtimoiy_adapter(var list: List<Contact>, var onItemClickListener: OnItemClickListener) :RecyclerView.Adapter<Ijtimoiy_adapter.Vh>() {

    inner class Vh(var ijtimContactBinding: IjtimContactBinding) : RecyclerView.ViewHolder(ijtimContactBinding.root){

        fun onBind(contact: Contact) {
            ijtimContactBinding.heading.text = contact.name
            ijtimContactBinding.teks.text = contact.phoneNumber
//            ijtimContactBinding.turi.text = contact.kategoriya

            ijtimContactBinding.root.setOnClickListener {
                onItemClickListener.onItemContactClick(contact)
            }
            ijtimContactBinding.more.setOnClickListener {
                onItemClickListener.onItemClick(contact, position, ijtimContactBinding.more)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh (IjtimContactBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener{
        fun onItemContactClick(contact: Contact)
        fun onItemClick(contact: Contact, position: Int, imageView: ImageView)
    }
}