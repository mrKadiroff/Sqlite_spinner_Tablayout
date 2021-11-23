package com.example.home_1.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.home_1.R
import com.example.home_1.adapters.Ijtimoiy_adapter
import com.example.home_1.databinding.FragmentDunyoBinding
import com.example.home_1.db.MyDbHelper
import com.example.home_1.models.Contact


class DunyoFragment : Fragment() {
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
        var view = inflater.inflate(R.layout.fragment_dunyo, container, false)
        var binding =  FragmentDunyoBinding.bind(view)

        var List = ArrayList<Contact>()
        var database = MyDbHelper(mContext)
        var ValueList = database.getAllContacts()
        val kateg = "Dunyo"
        ValueList.forEach {
            if (it.kategoriya == kateg){
                List.add(it)
            }
        }
        var adapter = Ijtimoiy_adapter(List)
        binding.dunyoRv.adapter = adapter
        val rv = binding.dunyoRv




        return view
    }


}