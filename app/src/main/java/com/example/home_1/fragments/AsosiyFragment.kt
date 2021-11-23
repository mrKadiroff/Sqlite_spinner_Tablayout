package com.example.home_1.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.home_1.R
import com.example.home_1.adapters.Ijtimoiy_adapter
import com.example.home_1.databinding.FragmentAsosiyBinding
import com.example.home_1.db.MyDbHelper
import com.example.home_1.models.Contact


class AsosiyFragment : Fragment() {
    lateinit var mContext : Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_asosiy, container, false)
        var binding = FragmentAsosiyBinding.bind(view)

        var List = ArrayList<Contact>()
        var database = MyDbHelper(mContext)
        var ValueList = database.getAllContacts()
        val kateg = "Asosiy"
        ValueList.forEach {
            if (it.kategoriya == kateg){
                List.add(it)
            }
        }
        var adapter = Ijtimoiy_adapter(List)
        binding.asosRv.adapter = adapter
        val rv = binding.asosRv


        return view
    }



}