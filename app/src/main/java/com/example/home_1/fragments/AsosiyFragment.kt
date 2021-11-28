package com.example.home_1.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.home_1.ChildActivity
import com.example.home_1.MainActivity
import com.example.home_1.R
import com.example.home_1.`interface`.SendMessage
import com.example.home_1.adapters.Asosiy_adapter
import com.example.home_1.adapters.Ijtimoiy_adapter
import com.example.home_1.adapters.SpinnerAdapter
import com.example.home_1.databinding.FragmentAsosiyBinding
import com.example.home_1.databinding.FragmentDunyoBinding
import com.example.home_1.databinding.MyDialogBinding
import com.example.home_1.db.MyDbHelper
import com.example.home_1.models.Contact
import java.util.*
import kotlin.collections.ArrayList


class AsosiyFragment : Fragment() {

//    private var _binding: FragmentAsosiyBinding? = null
//    private val binding get() = _binding!!
//    lateinit var root: View
lateinit var spinnerAdapter: SpinnerAdapter
    lateinit var rvAdapters: Asosiy_adapter
    lateinit var spinnerBasicList: ArrayList<String>
    lateinit var list: ArrayList<Contact>


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
        var binding =  FragmentAsosiyBinding.bind(view)
        setHasOptionsMenu(true)



        spinnerBasicList = ArrayList()
        spinnerBasicList.add("Asosiy")
        spinnerBasicList.add("Dunyo")
        spinnerBasicList.add("Ijtimoiy")


        var List = ArrayList<Contact>()
        var database = MyDbHelper(mContext)
        var ValueList = database.getAllContacts()
        val kateg = "Asosiy"
        ValueList.forEach {
            if (it.kategoriya == kateg){
                List.add(it)
            }
        }

        list = database.getAllContacts()
         rvAdapters = Asosiy_adapter(List, object:Asosiy_adapter.OnItemClickListener{
            override fun onItemContactClick(contact: Contact) {
               val intent = Intent(mContext,ChildActivity::class.java)
                intent.putExtra("id", contact.id)
                startActivity(intent)
            }

            override fun onItemClick(contact: Contact, position: Int, imageView: ImageView) {
                val popupMenu = PopupMenu(mContext, imageView)
                popupMenu.inflate(R.menu.popup_menu)

                popupMenu.setOnMenuItemClickListener {
                   when (it.itemId) {
                       R.id.edit -> {
                           val alertDialog = AlertDialog.Builder(mContext)
                           val dialog = alertDialog.create()
                           val dialogView = MyDialogBinding.inflate(
                               LayoutInflater.from(mContext),
                               null,
                               false
                           )
                           spinnerAdapter = SpinnerAdapter(spinnerBasicList)
                           dialogView.Spinner.adapter = spinnerAdapter

                           dialogView.sarlavha.setText(contact.name)
                           dialogView.matn.setText(contact.phoneNumber)
                           dialogView.Spinner.setSelection(0)

                           dialogView.saveText.setOnClickListener {
                               val type = spinnerBasicList[dialogView.Spinner.selectedItemPosition]
                               val name = dialogView.sarlavha.text.toString()
                               val descriptions = dialogView.matn.text.toString()

                               if (name.isNotEmpty()) {
                                  contact.kategoriya=spinnerBasicList[dialogView.Spinner.selectedItemPosition]
                                   contact.name=dialogView.sarlavha.text.toString()
                                   contact.phoneNumber=dialogView.matn.text.toString()
                                   database.updateContact(contact)
                                   List[position] = contact
                                   List.clear()

                                   var allBasic = database.getAllContacts()

                                   for (contact in allBasic){
                                       if (contact.kategoriya == spinnerBasicList[0]) {
                                           List.add(contact)
                                       }
                                   }


                                   binding.asosRv.adapter = rvAdapters
                                   rvAdapters.notifyItemInserted(List.size)
                                   rvAdapters.notifyItemChanged(position)
                                   rvAdapters.notifyItemRangeInserted(position,List.size)
                                   rvAdapters.notifyItemRangeChanged(position, List.size)
                                   dialog.dismiss()

//                                   var database = MyDbHelper(mContext)
//                                   var List = ArrayList<Contact>()
                                   List.clear()
                                    allBasic = database.getAllContacts()
                                   for (contact in allBasic) {
                                       if (contact.kategoriya == spinnerBasicList[0]) {
                                           List.add(contact)
                                       }

//            var rvAdapter = Asosiy_adapter()
                                       rvAdapters.notifyDataSetChanged()

                                   }
                               }
                           }
                           dialog.setView(dialogView.root)
                           dialog.show()

                       }
                       R.id.delete -> {
                           database.deleteContact(contact)
                           List.remove(contact)
                           rvAdapters.notifyItemRemoved(position)
                           rvAdapters.notifyItemRangeChanged(position, List.size)
                       }


                   }
                  true
                }
                popupMenu.show()
            }

        }, navController = NavController(mContext))
        binding.asosRv.adapter = rvAdapters
        val rv = binding.asosRv


        return view
    }

    override fun onResume() {
        super.onResume()
        var database = MyDbHelper(mContext)
        var List = ArrayList<Contact>()
        List.clear()
        List.addAll(database.getAllContacts())
        rvAdapters.notifyDataSetChanged()
//        List.clear()
//        var allBasic = database.getAllContacts()
//        for (contact in allBasic) {
//            if (contact.kategoriya == spinnerBasicList[0]) {
//                List.add(contact)
//            }
//
////            var rvAdapter = Asosiy_adapter()
//            rvAdapters.notifyDataSetChanged()
//
//        }







}}