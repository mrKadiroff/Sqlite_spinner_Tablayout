package com.example.home_1.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.home_1.ChildIjtActivity
import com.example.home_1.Child_dunyoActivity
import com.example.home_1.R
import com.example.home_1.adapters.Asosiy_adapter
import com.example.home_1.adapters.Ijtimoiy_adapter
import com.example.home_1.databinding.FragmentIjtimoiyBinding
import com.example.home_1.db.MyDbHelper
import com.example.home_1.models.Contact
import com.google.android.material.tabs.TabLayout


class IjtimoiyFragment : Fragment() {

    lateinit var binding: FragmentIjtimoiyBinding
    lateinit var myDbHelper: MyDbHelper
    lateinit var ijtimoiyList: ArrayList<Contact>
    lateinit var list: ArrayList<Contact>
    lateinit var rvAdapters: Ijtimoiy_adapter
    lateinit var spinnerBasicList: ArrayList<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentIjtimoiyBinding.inflate(layoutInflater)

        myDbHelper = MyDbHelper(binding.root.context)

        ijtimoiyList = ArrayList()
        list = ArrayList()

        ijtimoiyList = myDbHelper.getAllContacts()


        spinnerBasicList = ArrayList()
        spinnerBasicList.add("Asosiy")
        spinnerBasicList.add("Dunyo")
        spinnerBasicList.add("Ijtimoiy")

        val kateg = "Ijtimoiy"

        ijtimoiyList.forEach {
            if (it.kategoriya == kateg) {
                list.add(it)
            }
        }

        rvAdapters = Ijtimoiy_adapter(list, object : Ijtimoiy_adapter.OnItemClickListener {
            override fun onItemContactClick(contact: Contact) {
                val intent = Intent(binding.root.context, ChildIjtActivity::class.java)
                intent.putExtra("oydi", contact.id)
                startActivity(intent)
            }
        })

        binding.ijtRv.adapter = rvAdapters



        return binding.root
    }


    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        list.clear()
        var allBasic = myDbHelper.getAllContacts()
        for (contact in allBasic) {
            if (contact.kategoriya == spinnerBasicList[0]) {
                list.add(contact)
            }
            rvAdapters.notifyDataSetChanged()
        }
    }
}




