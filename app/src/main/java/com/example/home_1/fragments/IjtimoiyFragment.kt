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
import com.google.android.material.tabs.TabLayout


class IjtimoiyFragment : Fragment() {

//lateinit var ijtimoiyAdapter: Ijtimoiy_adapter
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

      




       var List = ArrayList<Contact>()
        var database = MyDbHelper(mContext)
        var ValueList = database.getAllContacts()

        val kateg = "Ijtimoiy"

        ValueList.forEach {
            if (it.kategoriya == kateg){
                List.add(it)
            }
        }
        var adapter = Ijtimoiy_adapter(List)
        binding.ijtRv.adapter = adapter
        val rv = binding.ijtRv

//       List.addAll(ValueList)
//       adapter = Ijtimoiy_adapter(List)
//        binding.ijtRv.adapter = adapter


//            List.add(it)



        return view
    }

    override fun onResume() {
        super.onResume()
        var database = MyDbHelper(mContext)
        var List = ArrayList<Contact>()
        List.clear()
        List.addAll(database.getAllContacts())
        var adapter = Ijtimoiy_adapter(List)
        adapter.notifyDataSetChanged()
//        ijtimoiyAdapter.notifyDataSetChanged()



    }




}