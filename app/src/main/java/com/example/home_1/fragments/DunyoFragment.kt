package com.example.home_1.fragments

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import com.example.home_1.Child_dunyoActivity
import com.example.home_1.R
import com.example.home_1.adapters.Dunyo_adapter
import com.example.home_1.adapters.SpinnerAdapter
import com.example.home_1.databinding.FragmentDunyoBinding
import com.example.home_1.databinding.MyDialogBinding
import com.example.home_1.db.MyDbHelper
import com.example.home_1.models.Contact


class DunyoFragment : Fragment() {

    lateinit var spinnerAdapter: SpinnerAdapter
    lateinit var rvAdapters: Dunyo_adapter
    lateinit var spinnerBasicList: ArrayList<String>
    lateinit var worldList: ArrayList<Contact>
    lateinit var list : ArrayList<Contact>
    lateinit var myDbHelper: MyDbHelper
//    lateinit var mContext : Context
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        mContext = context
//    }

    lateinit var binding: FragmentDunyoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentDunyoBinding.inflate(layoutInflater)

        myDbHelper= MyDbHelper(binding.root.context)

        setHasOptionsMenu(true)
        spinnerBasicList = ArrayList()
        spinnerBasicList.add("Asosiy")
        spinnerBasicList.add("Dunyo")
        spinnerBasicList.add("Ijtimoiy")

        worldList= ArrayList()
        list= ArrayList()

        worldList=myDbHelper.getAllContacts()

        val kateg = "Dunyo"

        worldList.forEach {
            if (it.kategoriya == kateg){
                list.add(it)
            }
        }
        rvAdapters = Dunyo_adapter(list, object:Dunyo_adapter.OnItemClickListener{
            override fun onItemContactClick(contact: Contact) {
                val intent = Intent(binding.root.context, Child_dunyoActivity::class.java)
                intent.putExtra("aydi", contact.id)
                startActivity(intent)
            }

            override fun onItemClick(contact: Contact, position: Int, imageView: ImageView) {
                val popupMenu = PopupMenu(binding.root.context, imageView)
                popupMenu.inflate(R.menu.popup_menu)
                popupMenu.setOnMenuItemClickListener {
                    when (it.itemId) {
                        R.id.edit -> {
                            val alertDialog = AlertDialog.Builder(binding.root.context)
                            val dialog = alertDialog.create()
                            val dialogView = MyDialogBinding.inflate(
                                LayoutInflater.from(binding.root.context),
                                null,
                                false
                            )
                            spinnerAdapter = SpinnerAdapter(spinnerBasicList)
                            dialogView.Spinner.adapter = spinnerAdapter

                            dialogView.sarlavha.setText(contact.name)
                            dialogView.matn.setText(contact.phoneNumber)
                            dialogView.Spinner.setSelection(1)

                            dialogView.saveText.setOnClickListener {
                                val type = spinnerBasicList[dialogView.Spinner.selectedItemPosition]
                                val name = dialogView.sarlavha.text.toString()
                                val descriptions = dialogView.matn.text.toString()

                                if (name.isNotEmpty()) {
                                    contact.kategoriya=spinnerBasicList[dialogView.Spinner.selectedItemPosition]
                                    contact.name=dialogView.sarlavha.text.toString()
                                    contact.phoneNumber=dialogView.matn.text.toString()
                                    myDbHelper.updateContact(contact)
                                    list[position] = contact
                                    list.clear()
//
//                                    var allBasic = database.getAllContacts()
//
//                                    for (contact in allBasic){
//                                        if (contact.kategoriya == spinnerBasicList[1]) {
//                                            List.add(contact)
//                                        }
//                                    }
//
//
                                    binding.dunyoRv.adapter = rvAdapters
                                    rvAdapters.notifyItemInserted(list.size)
                                    rvAdapters.notifyItemChanged(position)
                                    rvAdapters.notifyItemRangeInserted(position,list.size)
                                    rvAdapters.notifyItemRangeChanged(position, list.size)
                                    dialog.dismiss()


                                }
                            }
                            dialog.setView(dialogView.root)
                            dialog.show()

                        }
                        R.id.delete -> {
                            myDbHelper.deleteContact(contact)
                            list.remove(contact)
                            rvAdapters.notifyItemRemoved(position)
                            rvAdapters.notifyItemRangeChanged(position, list.size)
                        }


                    }
                    true
                }
                popupMenu.show()
            }

        })

        binding.dunyoRv.adapter = rvAdapters

        return binding.root
    }


    override fun onStart() {
        super.onStart()
        Toast.makeText(binding.root.context, "onStart", Toast.LENGTH_SHORT).show()
    }

    override fun onDetach() {
        super.onDetach()
        Toast.makeText(binding.root.context, "onDetach", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        Toast.makeText(binding.root.context, "onResume", Toast.LENGTH_SHORT).show()
        list.clear()
        var allBasic = myDbHelper.getAllContacts()
        for (contact in allBasic) {
            if (contact.kategoriya == spinnerBasicList[1]) {
                list.add(contact)
            }
            rvAdapters.notifyDataSetChanged()
    }

    }
}



