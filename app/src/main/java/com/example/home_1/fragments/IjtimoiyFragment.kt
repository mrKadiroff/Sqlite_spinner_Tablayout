package com.example.home_1.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.home_1.R
import com.example.home_1.adapters.Ijtimoiy_adapter
import com.example.home_1.databinding.FragmentIjtimoiyBinding
import com.example.home_1.db.MyDbHelper
import com.example.home_1.models.Contact


class IjtimoiyFragment : Fragment() {


    lateinit var mContext : Context
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        var view: View = inflater.inflate(R.layout.fragment_ijtimoiy, container, false)
        var binding = FragmentIjtimoiyBinding.bind(view)
       var modelList = ArrayList<Contact>()
        var database = MyDbHelper(mContext)
        var phoneList = database.getAllContacts()

        phoneList.forEach {
//            modelList.add(it)
        }
        var adapter = Ijtimoiy_adapter(modelList)
        binding.ijtRv.adapter = adapter

       modelList.addAll(phoneList)
       adapter = Ijtimoiy_adapter(modelList)
        binding.ijtRv.adapter = adapter

//
//        ijtimoiyAdapter = Ijtimoiy_adapter(phon)
//        ijtimoiyAdapter.notifyItemInserted(phoneList.size)




        return view
    }

//    override fun onResume() {
//        super.onResume()
//        var myDbHelper = MyDbHelper(mContext)
//        var list = ArrayList<Contact>()
//        var alllist = myDbHelper.getAllContacts()
//        list.clear()
//        list.addAll(myDbHelper.getAllContacts())
//        ijtimoiyAdapter = Ijtimoiy_adapter(list)
//        ijtimoiyAdapter.notifyItemInserted(list.size)
//        ijtimoiyAdapter.notifyDataSetChanged()
//    }


}